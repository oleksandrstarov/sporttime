package converter.model;

import converter.service.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SIPunch {
    static final byte Punch = 0;
    static final byte Card = 64;

    byte type = Punch;
    /** 2 byte 0-65K or the code of a SpecialPunch*/
    public short codeNumber;
    /** 4 byte integer  -2GB until +2GB*/
    public int SICardNo;
    /** Obsolete, not used anymore. */
    final int codeDay = 0;
    /** Time tenths of seconds after 00:00:00 */
    public int codeTime;

    public final byte[] serialize() {
        ByteArrayOutputStream bs = new ByteArrayOutputStream(15);
        DataOutputStream oos = null;
        try {
            oos = new DataOutputStream(bs);
            serialize(oos);
        } catch (Exception e) {
            Log.getInstance().error(e);
        } catch (Error e) {
            Log.getInstance().error(e);
        }
        return bs.toByteArray();
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeByte(type);
        out.writeShort(Short.reverseBytes(codeNumber));
        out.writeInt(Integer.reverseBytes(SICardNo));
        out.writeInt(Integer.reverseBytes(codeDay));
        out.writeInt(Integer.reverseBytes(codeTime));
    }
}
