package uncmn.eve;

import java.util.List;

/**
 * Set of operations for known types of Java objects.
 */
public interface Operations {

  /**
   * @param key Unique key string.
   * @param value int value.
   */
  void set(String key, int value);

  /**
   * @param key Unique key string.
   * @param value array of ints.
   */
  void set(String key, int[] value);

  /**
   * @param key Unique key string.
   * @param value float value.
   */
  void set(String key, float value);

  /**
   * @param key Unique key string.
   * @param value array of floats.
   */
  void set(String key, float[] value);

  /**
   * @param key Unique key string.
   * @param value double value.
   */
  void set(String key, double value);

  /**
   * @param key Unique key string.
   * @param value array of doubles.
   */
  void set(String key, double[] value);

  /**
   * @param key Unique key string.
   * @param value long value.
   */
  void set(String key, long value);

  /**
   * @param key Unique key string.
   * @param value array of longs.
   */
  void set(String key, long[] value);

  /**
   * @param key Unique key string.
   * @param value boolean value.
   */
  void set(String key, boolean value);

  /**
   * @param key Unique key string.
   * @param value char value.
   */
  void set(String key, char value);

  /**
   * @param key Unique key string.
   * @param value array of chars.
   */
  void set(String key, char[] value);

  /**
   * @param key Unique key string.
   * @param value byte value.
   */
  void set(String key, byte value);

  /**
   * @param key Unique key string.
   * @param value String value.
   */
  void set(String key, String value);

  /**
   * @param key Unique key string.
   * @param value array of strings.
   */
  void set(String key, String[] value);

  /**
   * @param key Unique key string.
   * @param value List of strings.
   */
  void set(String key, List<String> value);

  /**
   * @param key Unique key string.
   * @param value An object that can be converted with {@linkplain Converter}.
   */
  void set(String key, Object value);
}
