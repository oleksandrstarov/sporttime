package converter.model;

import java.io.DataOutputStream;
import java.io.IOException;

public class CardPunch {
    public int codeNumber; // SI code number
    public int codeTime; // Time tenth of seconds after 00:00:00
    public void serialize(DataOutputStream out) throws IOException {
        System.out.println(codeTime);
        out.writeInt(Integer.reverseBytes(codeNumber));
        out.writeInt(Integer.reverseBytes(codeTime));
    }
}
