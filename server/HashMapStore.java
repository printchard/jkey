package server;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A thread-safe {@link Store} implementation backed by a
 * {@link ConcurrentHashMap} that uses {@link ByteBuffer} wrappers of the
 * provided byte-array keys.
 *
 * <p>
 * Keys are stored as {@code ByteBuffer.wrap(key)}. Because the map keys
 * reference the supplied byte arrays (via the backed ByteBuffer), callers
 * must treat key arrays as effectively immutable after insertion. Mutating a
 * key's byte array after it has been inserted may change lookup behavior.
 *
 * <p>
 * Values are stored as raw byte arrays and are not defensively copied by
 * this implementation. If callers require isolation, they should pass copies
 * when storing or retrieving values.
 */
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
