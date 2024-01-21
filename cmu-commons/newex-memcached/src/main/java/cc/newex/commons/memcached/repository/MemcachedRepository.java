package cc.newex.commons.memcached.repository;

/**
 * @author newex-team
 * @date 2017/12/09
 */
public interface MemcachedRepository {
    /**
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * @param key
     * @return
     */
    long getWithDefaultValue(String key);

    /**
     * @param key
     * @param obj
     */
    void put(String key, Object obj);

    /**
     * @param key
     * @param obj
     * @param exp 毫秒
     */
    void put(String key, Object obj, long exp);

    /**
     * @param key
     * @param obj
     */
    void putAll(String key, Object obj);

    /**
     * @param key
     */
    void remove(String key);

    /**
     *
     */
    void clear();

    /**
     * @param key
     * @param by
     * @param def
     * @return
     */
    long incr(String key, long by, long def);

    /**
     * @param key
     * @param by
     * @param def
     * @param exp 秒
     * @return
     */
    long incr(String key, long by, long def, int exp);

    /**
     * @param key
     * @return
     */
    long incr(String key);

}
