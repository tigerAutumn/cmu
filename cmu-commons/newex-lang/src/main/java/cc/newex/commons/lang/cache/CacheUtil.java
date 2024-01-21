package cc.newex.commons.lang.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
public class CacheUtil {
    private static final ConcurrentHashMap<String, Cache> CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 10秒过期
     */
    private static final long OVER_TIME = 10000L;

    public static void putCache(final String key, final String value) {
        final Cache cache = CACHE_MAP.put(key, Cache.builder()
                .key(key)
                .value(value)
                .expire(OVER_TIME)
                .build());
    }

    public static String getCache(final String key, final Supplier<String> supplier) {
        return CACHE_MAP.compute(key, (k, ov) -> {
            // 无值或缓存超时
            if (ov == null || ov.isExpired()) {
                return Cache.builder()
                        .key(key)
                        .value(supplier.get())
                        .expire(OVER_TIME)
                        .build();
            }
            return ov;
        }).getValue();
    }

}
