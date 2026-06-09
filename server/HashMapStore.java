package server;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapStore implements Store {
  private ConcurrentHashMap<ByteBuffer, byte[]> map = new ConcurrentHashMap<>();

  @Override
  public void set(byte[] key, byte[] value) {
    map.put(ByteBuffer.wrap(key), value);
  }

  @Override
  public byte[] get(byte[] key) {
    return map.get(ByteBuffer.wrap(key));
  }

  @Override
  public void delete(byte[] key) {
    map.remove(ByteBuffer.wrap(key));
  }

  @Override
  public boolean exists(byte[] key) {
    return map.containsKey(ByteBuffer.wrap(key));
  }
}
