package converter.model;

public enum SpecialPunch {
    PunchStart(1),
    PunchFinish(2),
    PunchCheck(3);

    public final short code;
    SpecialPunch(int code) {
        this.code = (short)code;
    }
}