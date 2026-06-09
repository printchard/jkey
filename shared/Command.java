package shared;

public enum Command {
  PING(0x1),
  GET(0x2),
  SET(0x3),
  DEL(0x4),
  EXISTS(0x5);

  public final byte code;

  Command(int code) {
    this.code = (byte) code;
  }

  public static Command fromByte(byte b) {
    for (Command c : values()) {
      if (c.code == b) {
        return c;
      }
    }
    throw new IllegalArgumentException(String.format("Invalid command: %d", b));
  }
}
