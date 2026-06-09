package server;

/**
 * A simple byte-array based key/value store used by the server.
 * Implementations are responsible for persisting and retrieving raw
 * byte-array keys and values.
 */
public interface Store {
  /**
   * Store the given value under the provided key. Implementations should
   * not modify the supplied arrays and may store a copy if necessary.
   *
   * @param key   the key under which to store the value
   * @param value the value to store
   */
  public void set(byte[] key, byte[] value);

  /**
   * Retrieve the value associated with the provided key.
   *
   * @param key the key to look up
   * @return the stored value, or {@code null} if the key is not present
   */
  public byte[] get(byte[] key);

  /**
   * Remove any value associated with the provided key.
   *
   * @param key the key to delete
   */
  public void delete(byte[] key);

  /**
   * Check whether the provided key exists in the store.
   *
   * @param key the key to check
   * @return {@code true} if the key exists, {@code false} otherwise
   */
  public boolean exists(byte[] key);
}
