package converter.service;

import converter.model.SICard;
import converter.model.SpecialPunch;

import java.util.ArrayList;
import java.util.Arrays;

public class DataParser {
    private final String SI_CARD = "SI-Card";
    private final String START_TIME = "start time";
    private final String FINISH_TIME = "Finish time";
    private final String PUNCHES = "No. of punches";

    private int siCardIndex = -1;
    private int startTimeIndex = -1;
    private int finishTimeIndex = -1;
    private int punchesIndex = -1;

    private ArrayList<String> pattern = new ArrayList<>();

    private String prevData = "";

    DataParser() {

    }

    public void parseData(String data) {
        if (pattern.isEmpty()) {
            setPattern(data.split(System.lineSeparator())[0]);
        }
        ArrayList<String> newRows = getNewRows(data);

        for(String row : newRows) {
            createPunches(row.split(";"));
        }
        prevData = data;
    }

    private void createPunches(String[] punchData) {
        int siNumber = Integer.parseInt(punchData[siCardIndex]);
        String startTimeString = punchData[startTimeIndex];
        String finishTimeString = punchData[finishTimeIndex];
        String[] punches = Arrays.copyOfRange(punchData, punchesIndex, punchData.length);

        SICard card = new SICard();
        card.SICardNo = siNumber;

        int starTime = startTimeString.length() > 0 ? Utils.convertTime(startTimeString): 0;

        if (starTime > 0) {
            card.addPunch(SpecialPunch.PunchStart.code, starTime);
        }

        card.addPunches(punches);

        int finishTime = finishTimeString.length() > 0 ? Utils.convertTime(finishTimeString) : 0;
        if (finishTime > 0) {
            card.addPunch(SpecialPunch.PunchFinish.code, finishTime);
        }
        System.out.println(card);
        MeosConnector.sendData(card);
    }

    private void setPattern(String pattern) {
        this.pattern = new ArrayList<>(Arrays.asList(pattern.split(";")));

        siCardIndex = this.pattern.indexOf(SI_CARD);
        startTimeIndex = this.pattern.indexOf(START_TIME);
        finishTimeIndex = this.pattern.indexOf(FINISH_TIME);
        punchesIndex = this.pattern.indexOf(PUNCHES) + 1;
    }

    private ArrayList<String> getNewRows(String data) {
        boolean isInitialRead = "".equals(prevData);
        data = data.replace(prevData, "");
        ArrayList<String> newData = new ArrayList<>(Arrays.asList(data.split(System.lineSeparator())));
        if (isInitialRead) {
            newData = new ArrayList<>(newData.subList(2, newData.size()));
        }
        return newData;
    }
}
