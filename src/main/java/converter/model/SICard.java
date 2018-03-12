package converter.model;

import converter.service.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class SICard extends SIPunch {
    ArrayList<CardPunch> punches = new ArrayList<>();
    public SICard() {
        type = Card;
        codeTime = 0;
    }

    public void addPunches(String[] punches) {
        System.out.println(punches.length);
        for (int i=0; i <= punches.length-3; i=i+3) {
            String codeS = punches[i];
            System.out.println(codeS + punches[i+2]);
            if (!codeS.isEmpty()) {
                int code = Integer.parseInt(codeS);
                int time = Utils.convertTime(punches[i+2]);
                addPunch(code, time);
            }
        }
    }

    public void serialize(DataOutputStream out) throws IOException {
        super.serialize(out);
        for(CardPunch p : punches) {
            p.serialize(out);
        }
    }

    public void addPunch(int code, int time) {
        CardPunch cp = new CardPunch();
        cp.codeNumber = code;
        cp.codeTime = time;
        punches.add(cp);
        codeNumber = (short) punches.size();
    }

    @Override
    public String toString() {
        System.out.println(punches.size());
        String data = "SICardNo=" + SICardNo;
        for (CardPunch punch: punches) {
            String punchData = " ["+ punch.codeNumber +"] - " + new Date(punch.codeTime * 1000);
            data = data.concat(punchData);
        }
        return data;
    }
}