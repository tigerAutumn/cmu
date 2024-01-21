package cc.newex.commons.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.SafeEncoder;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * @author newex-team
 * @data 30/03/2018
 */
@Configuration
@Slf4j
public class REDIS {
    private static final int TIMES = 3;
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    private static JedisConnectionFactory factory;

    @PostConstruct
    public void init() {
        factory = this.jedisConnectionFactory;
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            log.info("destroy redis factory begin");
            factory.destroy();
            log.info("destroy redis factory success");
        }));
    }


    public static String get(final String key) {
        log.info("get begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        byte[] resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.get(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("get error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        String result = null;
        if (resultBytes != null) {
            result = SafeEncoder.encode(resultBytes);
        }
        log.info("get end: {}", result);
        return result;
    }

    public static Integer getInt(final String key) {
        log.info("getInt begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        byte[] resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.get(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("getInt error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        Integer result = null;
        if (resultBytes != null) {
            final String resultStr = SafeEncoder.encode(resultBytes);
            result = Integer.valueOf(resultStr);
        }
        log.info("getInt end: {}", result);
        return result;
    }

    public static Boolean exists(final String key) {
        log.info("exists begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        Boolean result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.exists(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("exists error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("exists end: {}", result);
        return result;
    }

    public static Long incr(final String key) {
        log.info("incr begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.incr(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("incr error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("incr end: {}", result);
        return result;
    }

    public static Long incrBy(final String key, final long increment) {
        log.info("incrby begin: {} {}", key, increment);

        final byte[] keyBytes = SafeEncoder.encode(key);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.incrBy(keyBytes, increment);
                    break;
                } catch (final Throwable e) {
                    log.error("incr error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("incrby end: {}", result);
        return result;
    }

    public static void set(final String key, final String value) {
        log.info("set begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    connection.set(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("set error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("set end");
    }

    public static void set(final String key, final int value) {
        log.info("set begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(String.valueOf(value));
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    connection.set(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("set error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("set end");
    }

    public static void setEx(final String key, final long time, final int value) {
        log.info("setEx begin: {}, {}, {}", key, time, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(String.valueOf(value));
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    connection.setEx(keyBytes, time, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("setEx error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("setEx end");
    }

    public static void setEx(final String key, final long time, final String value) {
        log.info("setEx begin: {}, {}, {}", key, time, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    connection.setEx(keyBytes, time, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("setEx error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("setEx end");
    }

    public static boolean setNX(final String key, final String value) {
        log.info("setNX begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        Boolean result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.setNX(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("setNX error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("setNX end: {}", result);
        return result == null ? false : result;
    }

    public static Integer getSet(final String key, final int value) {
        log.info("getSet begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(String.valueOf(value));
        byte[] resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.getSet(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("getSet error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        Integer result = null;
        if (resultBytes != null) {
            final String resultStr = SafeEncoder.encode(resultBytes);
            result = Integer.valueOf(resultStr);
        }
        log.info("getSet end: {}", result);
        return result;
    }

    public static String getSet(final String key, final String value) {
        log.info("getSet begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        byte[] resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.getSet(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("getSet error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        String result = null;
        if (resultBytes != null) {
            result = SafeEncoder.encode(resultBytes);
        }
        log.info("getSet end: {}", result);
        return result;
    }

    public static boolean expire(final String key, final long seconds) {
        log.info("expire begin: {}, {}", key, seconds);

        final byte[] keyBytes = SafeEncoder.encode(key);
        Boolean result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.expire(keyBytes, seconds);
                    break;
                } catch (final Throwable e) {
                    log.error("expire error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("expire end: {}", result);
        return result == null ? false : result;
    }

    /**
     * 当 key 不存在时，返回 -2
     * <p/>
     * 当 key 存在但没有设置剩余生存时间时，返回 -1
     */
    public static Long ttl(final String key) {
        log.info("ttl begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.ttl(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("ttl error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("ttl end: {}", result);
        return result;
    }

    public static Long del(final String key) {
        log.info("del begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.del(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("del error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("del end: {}", result);
        return result;
    }

    public static Long lPush(final String key, final String value) {
        log.info("lPush begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.lPush(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("lPush error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("lPush end: {}", result);
        return result;
    }

    public static String lPop(final String key) {
        log.info("lPop begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        byte[] resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.lPop(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("lPop error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        String result = null;
        if (resultBytes != null) {
            result = SafeEncoder.encode(resultBytes);
        }
        log.info("lPop end: {}", result);
        return result;
    }

    public static String rPop(final String key) {
        log.info("rPop begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        byte[] resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.rPop(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("rPop error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        String result = null;
        if (resultBytes != null) {
            result = SafeEncoder.encode(resultBytes);
        }
        log.info("rPop end: {}", result);
        return result;
    }


    public static String rPoplPush(final String src, final String dst) {
        log.info("rPoplPush begin, src: {},dst:{}", src, dst);

        final byte[] srcBytes = SafeEncoder.encode(src);
        final byte[] dstBytes = SafeEncoder.encode(dst);
        byte[] resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.rPopLPush(srcBytes, dstBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("rPoplPush error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        String result = null;
        if (resultBytes != null) {
            result = SafeEncoder.encode(resultBytes);
        }
        log.info("rPoplPush end: {}", result);
        return result;
    }

    public static List<String> lRange(final String key, final long start, final long end) {
        log.info("lRange begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        List<byte[]> resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.lRange(keyBytes, start, end);
                    break;
                } catch (final Throwable e) {
                    log.error("lRange error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        final List<String> result = new LinkedList<>();
        for (final byte[] bytes : resultBytes) {
            final String s = SafeEncoder.encode(bytes);
            result.add(s);
        }
        log.info("lRange end: {}", result.size());
        return result;
    }

    public static Long rPush(final String key, final String value) {
        log.info("rPush begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.rPush(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("rPush error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("rPush end: {}", result);
        return result;
    }

    public static Long lRem(final String key, final long countParams, final String value) {
        log.info("lRem begin: {}, {}, {}", key, countParams, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.lRem(keyBytes, countParams, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("lRem error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("lRem end: {}", result);
        return result;
    }

    public static Long lLen(final String key) {
        log.info("lLen begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.lLen(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("lLen error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("lLen end: {}", result);
        return result;
    }

    public static void lTrim(final String key, final long start, final long end) {
        log.info("lTrim begin: {} {} {}", key, start, end);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    connection.lTrim(keyBytes, start, end);
                    break;
                } catch (final Throwable e) {
                    log.error("lTrim error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("lTrim end");
    }

    public static Long sAdd(final String key, final String value) {
        log.info("sAdd begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.sAdd(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("sAdd error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("sAdd end: {}", result);
        return result;
    }

    public static Long sRem(final String key, final String value) {
        log.info("sRem begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.sRem(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("sRem error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("sRem end: {}", result);
        return result;
    }

    public static Set<String> sMembers(final String key) {
        log.info("sMembers begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        Set<byte[]> resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.sMembers(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("sMembers error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        Set<String> result = null;
        if (resultBytes != null) {
            result = new HashSet<>();
            for (final byte[] r : resultBytes) {
                if (r != null) {
                    final String rStr = SafeEncoder.encode(r);
                    result.add(rStr);
                }
            }
        }
        log.info("sMembers end: {}", result == null ? null : result.size());
        return result;
    }

    public static boolean sIsMember(final String key, final String value) {
        log.info("sIsMember begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        Boolean result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.sIsMember(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("sIsMember error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("sIsMember end: {}", result);
        return result == null ? false : (boolean) result;
    }

    public static boolean zAdd(final String key, final double score, final String value) {
        log.info("zAdd begin: {}, {}, {}", key, score, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        Boolean result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.zAdd(keyBytes, score, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("zAdd error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("zAdd end: {}", result);
        return result == null ? false : (boolean) result;
    }

    public static Long zRem(final String key, final String value) {
        log.info("zRem begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.zRem(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("zRem error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("zRem end: {}", result);
        return result;
    }

    public static Long zRemRangeByScore(final String key, final double min, final double max) {
        log.info("zRemRangeByScore begin: {}, {}, {}", key, min, max);
        final Long result = zRemRangeByScore(key, new RedisZSetCommands.Range().gte(min).lte(max));
        log.info("zRemRangeByScore end");
        return result;
    }

    public static Long zRemRangeByScore(final String key, final RedisZSetCommands.Range range) {
        log.info("zRemRangeByScore begin: {}, {}", key, JSON.toJSON(range));

        final byte[] keyBytes = SafeEncoder.encode(key);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.zRemRangeByScore(keyBytes, range);
                    break;
                } catch (final Throwable e) {
                    log.error("zRemRangeByScore error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("zRemRangeByScore end: {}", result);
        return result == null ? 0 : (long) result;
    }

    public static Set<String> zRange(final String key, final long start, final long end) {
        log.info("zRange begin: {}, {}, {}", key, start, end);

        final byte[] keyBytes = SafeEncoder.encode(key);
        Set<byte[]> resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.zRange(keyBytes, start, end);
                    break;
                } catch (final Throwable e) {
                    log.error("zRange error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        Set<String> result = null;
        if (resultBytes != null) {
            result = new HashSet<>();
            for (final byte[] r : resultBytes) {
                if (r != null) {
                    final String rStr = SafeEncoder.encode(r);
                    result.add(rStr);
                }
            }
        }
        log.info("zRange end: {}", result == null ? null : result.size());
        return result;
    }

    public static Set<String> zRangeByScore(final String key, final double min, final double max) {
        log.info("zRangeByScore begin: {}, {}, {}", key, min, max);
        final Set<String> result = zRangeByScore(key, new RedisZSetCommands.Range().gte(min).lte(max));
        log.info("zRangeByScore end");
        return result;
    }

    public static Set<String> zRangeByScore(final String key, final RedisZSetCommands.Range range) {
        log.info("zRangeByScore begin: {}, {}", key, JSON.toJSON(range));

        final byte[] keyBytes = SafeEncoder.encode(key);
        Set<byte[]> resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.zRangeByScore(keyBytes, range);
                    break;
                } catch (final Throwable e) {
                    log.error("zRangeByScore error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        Set<String> result = null;
        if (resultBytes != null) {
            result = new HashSet<>();
            for (final byte[] r : resultBytes) {
                if (r != null) {
                    final String rStr = SafeEncoder.encode(r);
                    result.add(rStr);
                }
            }
        }
        log.info("zRangeByScore end: {}", result == null ? null : result.size());
        return result;
    }

    public static String hGet(final String key, final String value) {
        log.info("hGet begin: {}, {}", key, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] valueBytes = SafeEncoder.encode(value);
        byte[] resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.hGet(keyBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("hGet error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        String result = null;
        if (resultBytes != null) {
            result = SafeEncoder.encode(resultBytes);
        }
        log.info("hGet end: {}", result);
        return result;
    }

    public static long hLen(final String key) {
        log.info("hLen begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.hLen(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("hLen error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("hLen end: {}", result);
        return result == null ? 0 : (long) result;
    }

    public static Map<String, String> hGetAll(final String key) {
        log.info("hGetAll begin: {}", key);

        final byte[] keyBytes = SafeEncoder.encode(key);
        Map<String, String> result = null;
        Map<byte[], byte[]> resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.hGetAll(keyBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("hGetAll error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        if (resultBytes != null) {
            result = new HashMap<>();
            for (final Map.Entry<byte[], byte[]> entry : resultBytes.entrySet()) {
                final byte[] k = entry.getKey();
                String kStr = null;
                if (k != null) {
                    kStr = SafeEncoder.encode(k);
                }
                final byte[] v = entry.getValue();
                String vStr = null;
                if (v != null) {
                    vStr = SafeEncoder.encode(v);
                }
                result.put(kStr, vStr);
            }
        }
        log.info("hGetAll end: {}", result == null ? null : result.size());
        return result;
    }

    public static boolean hSet(final String key, final String field, final String value) {
        log.info("hSet begin: {}, {}, {}", key, field, value);

        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] fieldBytes = SafeEncoder.encode(field);
        final byte[] valueBytes = SafeEncoder.encode(value);
        Boolean result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.hSet(keyBytes, fieldBytes, valueBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("hSet error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("hSet end: {}", result);
        return result == null ? false : (boolean) result;
    }

    public static long hDel(final String key, final String field) {
        log.info("hDel begin: {}, {}", key, field);
        final byte[] keyBytes = SafeEncoder.encode(key);
        final byte[] fieldBytes = SafeEncoder.encode(field);
        Long result = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    result = connection.hDel(keyBytes, fieldBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("hDel error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        log.info("hDel end: {}", result);
        return result == null ? 0 : (long) result;
    }

    public static Set<String> keys(final String pattern) {
        log.info("keys begin: {}", pattern);

        final byte[] patternBytes = SafeEncoder.encode(pattern);
        Set<byte[]> resultBytes = null;
        final RedisConnection connection = factory.getConnection();
        try {
            int count = 0;
            while (true) {
                count++;
                try {
                    resultBytes = connection.keys(patternBytes);
                    break;
                } catch (final Throwable e) {
                    log.error("keys error: ", e);
                    if (count > TIMES) {
                        throw new JedisException(e);
                    }
                }
            }
        } finally {
            connection.close();
        }
        Set<String> result = null;
        if (resultBytes != null) {
            result = new HashSet<>();
            for (final byte[] r : resultBytes) {
                if (r != null) {
                    final String rStr = SafeEncoder.encode(r);
                    result.add(rStr);
                }
            }
        }
        log.info("keys end: {}", result == null ? "null" : result.size());
        return result;
    }

    public static long publish(final String channel,final String message) {
        log.info("publish channel:{}, message :{} begin", channel,message);
        long res = 0;
        final RedisConnection connection = factory.getConnection();

        try {
            final byte[] channelBytes = SafeEncoder.encode(channel);
            final byte[] messageBytes = SafeEncoder.encode(message);
            res = connection.publish(channelBytes,messageBytes);
        } finally {
            connection.close();
        }
        log.info("publish channel:{}, message :{} end", channel,message);
        return res;
    }

    public static RedisSubHandler asyncSubscribe(final String channel, final AbstractMessageListener listener) {
        log.info("asyncSubscribe channel:{} begin", channel);

        final byte[] channelBytes = SafeEncoder.encode(channel);
        final RedisConnection connection = factory.getConnection();
        Thread subscribe = new Thread(()->{
            while (!Thread.currentThread().isInterrupted()){
                try {
                    if(connection.isClosed()){
                        break;
                    }
                    connection.subscribe(listener,channelBytes);
                    log.error("asyncSubscribe error,sleep 1 second before nex retry");
                    Thread.sleep(60*1000);
                } catch (final InterruptedException e) {
                    log.error("asyncSubscribe is Interrupted");
                    break;
                }
            }

        });
        subscribe.setName("asyncSubscribe-" + channel);
        subscribe.start();
        log.info("asyncSubscribe channel:{} end", channel);
        return new RedisSubHandler(subscribe,channel,connection);

    }

    public static void subscribe(final String channel, final AbstractMessageListener listener) {
        log.info("subscribe channel:{} begin", channel);

        final byte[] channelBytes = SafeEncoder.encode(channel);
        final RedisConnection connection = factory.getConnection();
        try {
            connection.subscribe(listener,channelBytes);
        } catch (Exception e) {
            log.info("subscribe channel:{} error", channel,e);
            connection.close();
        }

        log.info("subscribe channel:{} end", channel);
    }

    public abstract static class AbstractMessageListener implements MessageListener{
        @Override
        final public void onMessage(Message message, byte[] pattern){
            if(message == null){
                log.error("subscribe get a null message");
                return;
            }
            String channel = SafeEncoder.encode(message.getChannel());
            String data = SafeEncoder.encode(message.getBody());
            onMesssage(data,channel);
        }

        /**
         *
         * @param data
         * @param channel
         */
        public abstract void onMesssage(String data,String channel);


    }

    public static class RedisSubHandler{

        private Thread subscribe;
        private String subscribeKey;
        private RedisConnection connection;

        public String getSubscribeKey(){
            return subscribeKey;
        }


        RedisSubHandler(Thread t,String key,RedisConnection connection){
            this.subscribe = t;
            this.subscribeKey = key;
            this.connection = connection;
        }

        public void destroy(){
            synchronized (connection) {
                if (subscribe != null && !subscribe.isInterrupted()) {
                    //connection.close();
                    connection.getSubscription().close();
                    connection.close();
                    subscribe.interrupt();
                }
            }
        }

    }

}
