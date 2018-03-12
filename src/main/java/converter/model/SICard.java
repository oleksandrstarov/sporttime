package converter.model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SICard extends SIPunch {
    ArrayList<CardPunch> punches = new ArrayList<>();
    public SICard() {
        type = Card;
        codeTime = 0;
    }

    public SICard(int number) {
        type = Card;
        codeTime = 0;
        SICardNo = number;
    }

    public void addPunches() {

    }

    public void addPunch(int code, int time) {
        CardPunch cp = new CardPunch();
        cp.codeNumber = code;
        cp.codeTime = time;
        punches.add(cp);
        codeNumber = (short) punches.size();
    }

    public void serialize(DataOutputStream out) throws IOException {
        super.serialize(out);
        for(CardPunch p : punches) {
            p.serialize(out);
        }
    }
}