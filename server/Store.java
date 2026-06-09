package server;

public interface Store {
  public void set(byte[] key, byte[] value);

  public byte[] get(byte[] key);

  public void delete(byte[] key);

  public boolean exists(byte[] key);
}
