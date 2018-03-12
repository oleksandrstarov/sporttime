package converter.service;

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

        System.out.println("New Rows");
        System.out.println(newRows);
        prevData = data;
    }

    private void createPunches(String[] punch) {
        int siNumber = Integer.parseInt(punch[siCardIndex]);
        String startTime = punch[startTimeIndex];
        String finishTime = punch[finishTimeIndex];
        String[] punches = Arrays.copyOfRange(punch, punchesIndex, punch.length);

        System.out.println("SI: " + siNumber + ", start: " + startTime + ", finish: " + finishTime + " punches: " + punches);
    }

    private void setPattern(String pattern) {
        this.pattern = new ArrayList<>(Arrays.asList(pattern.split(";")));

        siCardIndex = this.pattern.indexOf(SI_CARD);
        startTimeIndex = this.pattern.indexOf(START_TIME);
        finishTimeIndex = this.pattern.indexOf(FINISH_TIME);
        punchesIndex = this.pattern.indexOf(PUNCHES);
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
