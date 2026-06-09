package shared;

public enum Status {
  OK(0x0),
  NOT_FOUND(0x1),
  ERROR(0x2);

  public final byte code;

  Status(int code) {
    this.code = (byte) code;
  }

  public static Status fromByte(byte b) {
    for (Status s : values()) {
      if (s.code == b) {
        return s;
      }
    }
    throw new IllegalArgumentException(String.format("Invalid status: %d", b));
  }
}
