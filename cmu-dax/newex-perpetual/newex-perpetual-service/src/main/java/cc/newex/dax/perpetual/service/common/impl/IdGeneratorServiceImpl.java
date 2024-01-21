package cc.newex.dax.perpetual.service.common.impl;

import cc.newex.commons.dictionary.consts.CacheKeys;
import cc.newex.dax.perpetual.service.common.IdGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.net.InetAddress;

/**
 * @author newex
 *
 * 雪花生成全局唯一主键
 *
 * 由于前端页面不能展示 64 位主键，所以缩减位数到 53 位，每毫秒可以支持 16 个主键
 * 最多支持 64 个主机 ip
 * 最大过期时间为 2087-9-7
 * @Date: 2018/6/6 11:16
 * @Description: 默认 id 生成器
 */
@Slf4j
@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {
    /**
     * 将key 的值设为value ，当且仅当key 不存在，等效于 SETNX。
     */
    public static final String NX = "NX";

    /**
     * 将key 的值设为value ，当且仅当key 不存在，等效于 SETNX。
     */
    public static final String XX = "XX";

    /**
     * seconds — 以秒为单位设置 key 的过期时间，等效于EXPIRE key seconds
     */
    public static final String EX = "EX";

    /**
     * 调用set后的返回值
     */
    public static final String OK = "OK";
    /**
     * 默认锁的有效时间(s)
     */
    public static final int EXPIRE = 5 * 60;
    /**
     * 0（1）+ timestamp（41）+ workId（10）+ sequence（12）
     * 每毫秒最大生成的 id 为 4096
     */
    private static final long SEQUENCE_BITS = 4L; //12L;
    private static final long SEQUENCE_MAX = -1L ^ (-1L << IdGeneratorServiceImpl.SEQUENCE_BITS);

    /**
     * 一个业务线最大部署的机器数为 64
     */
    private static final long WORKER_ID_BITS = 6L; //10L;
    private static final long WORKER_ID_MAX = -1L ^ (-1L << IdGeneratorServiceImpl.WORKER_ID_BITS);
    private static final long WORKER_ID_SHIFT = IdGeneratorServiceImpl.SEQUENCE_BITS;

    /**
     * 最大过期时间为 2087-9-7
     */
    private static final long TIMESTAMP_BITS = 41L;
    private static final long TIMESTAMP_MAX = -1L ^ (-1L << IdGeneratorServiceImpl.TIMESTAMP_BITS);
    private static final long TIMESTAMP_LEFT_SHIFT = IdGeneratorServiceImpl.WORKER_ID_BITS + IdGeneratorServiceImpl.SEQUENCE_BITS;

    /**
     * 初始时间（北京时间） 2018-01-01 00:00:00
     */
    private static final long START_TIME = 1514736000000L;
    private static volatile long workerId = -1L;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private volatile Long sequence = 0L;
    private volatile long lastTimestamp = -1L;

    @PostConstruct
    public void initWorkerId() {
        this.workerId();
    }

    @Scheduled(fixedDelay = 100, initialDelay = 100)
    public void resetExpireTime() {
        this.workerId();
    }

    @Override
    public long generateOrderId() {
        return this.getId();
    }

    private long getId() {
        synchronized (sequence) {
            long timestamp = this.currentTime();
            if (timestamp < this.lastTimestamp) {
                throw new IllegalStateException("Clock moved backwards.");
            }
            final long worker = IdGeneratorServiceImpl.workerId;

            if (worker >= IdGeneratorServiceImpl.WORKER_ID_MAX || worker <= 0) {
                // 一个业务线的 worker = 1024
                throw new IllegalStateException("WorkerId reaches a maximum.");
            }

            if (timestamp == this.lastTimestamp) {
                this.sequence = (this.sequence + 1) & IdGeneratorServiceImpl.SEQUENCE_MAX;
                if (this.sequence == 0) {
                    timestamp = this.nextTimeMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            if ((timestamp - IdGeneratorServiceImpl.START_TIME) >= IdGeneratorServiceImpl.TIMESTAMP_MAX) {
                // 2087-09-07 会超出限制，需要重新设置
                throw new IllegalStateException("Timestamp reaches a maximum.");
            }

            this.lastTimestamp = timestamp;
            final long id = ((timestamp - IdGeneratorServiceImpl.START_TIME) << IdGeneratorServiceImpl.TIMESTAMP_LEFT_SHIFT) | (worker << IdGeneratorServiceImpl.WORKER_ID_SHIFT) | this.sequence;
            return id;
        }
    }

    private synchronized long workerId() {
        if (IdGeneratorServiceImpl.workerId <= 0) {
            IdGeneratorServiceImpl.workerId = this.registerWorkerId();
        } else {
            final String key = this.takeWorkerIdKey(IdGeneratorServiceImpl.workerId);
            final String address = this.address();
            final String result = this.stringRedisTemplate.execute((RedisCallback<String>) connection -> {
                final Object nativeConnection = connection.getNativeConnection();
                String result1 = null;
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    result1 = ((JedisCluster) nativeConnection).set(key, address, IdGeneratorServiceImpl.XX, IdGeneratorServiceImpl.EX, IdGeneratorServiceImpl.EXPIRE);
                }
                // 单机模式
                if (nativeConnection instanceof Jedis) {
                    result1 = ((Jedis) nativeConnection).set(key, address, IdGeneratorServiceImpl.XX, IdGeneratorServiceImpl.EX, IdGeneratorServiceImpl.EXPIRE);
                }
                return result1;
            });
            if (IdGeneratorServiceImpl.OK.equalsIgnoreCase(result)) {
                return IdGeneratorServiceImpl.workerId;
            } else {
                IdGeneratorServiceImpl.workerId = -1L;
            }
        }
        return IdGeneratorServiceImpl.workerId;
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }

    private long nextTimeMillis(final long lastTimestamp) {
        long timestamp = this.currentTime();
        while (timestamp <= lastTimestamp) {
            try {
                Thread.sleep(lastTimestamp - timestamp + 1);
            } catch (final InterruptedException e) {
                IdGeneratorServiceImpl.log.error("unexpected error ", e);
            }
            timestamp = this.currentTime();
        }
        return timestamp;
    }

    private long registerWorkerId() {
        final String address = this.address();

        if (StringUtils.isEmpty(address)) {
            throw new IllegalStateException("Address is empty.");
        }

        long id = 0L;
        final long maxWorkerId = IdGeneratorServiceImpl.WORKER_ID_MAX;
        try {

            while ((id++) <= maxWorkerId) {
                final String key = this.takeWorkerIdKey(id);
                /** 检查是否有 */
                final String result = this.stringRedisTemplate.execute((RedisCallback<String>) connection -> {
                    final Object nativeConnection = connection.getNativeConnection();
                    String result1 = null;
                    // 集群模式
                    if (nativeConnection instanceof JedisCluster) {
                        result1 = ((JedisCluster) nativeConnection).set(key, address, IdGeneratorServiceImpl.NX, IdGeneratorServiceImpl.EX, IdGeneratorServiceImpl.EXPIRE);
                    }
                    // 单机模式
                    if (nativeConnection instanceof Jedis) {
                        result1 = ((Jedis) nativeConnection).set(key, address, IdGeneratorServiceImpl.NX, IdGeneratorServiceImpl.EX, IdGeneratorServiceImpl.EXPIRE);
                    }
                    return result1;
                });
                if (IdGeneratorServiceImpl.OK.equalsIgnoreCase(result)) {
                    return id;
                }
            }

        } catch (final Exception e) {
            IdGeneratorServiceImpl.log.error("register worker id failed ", e);
        }
        return -1L;
    }

    private String takeWorkerIdKey(final Long id) {
        final String workerIdKey = CacheKeys.PERPETUAL_ID_GENERATOR_WORKER_ID;
        return workerIdKey + "_" + id;
    }

    private String address() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (final Exception e) {
            IdGeneratorServiceImpl.log.error("unexpected error", e);
        }
        return null;
    }
}
