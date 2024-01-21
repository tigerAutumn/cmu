package cc.newex.dax.users.service.verification.impl;

import cc.newex.dax.users.common.enums.security.VerifyCodeTypeEnum;
import cc.newex.dax.users.service.verification.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
    private final String prefix = "verify_code";
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public VerifyCodeServiceImpl(final StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(final VerifyCodeTypeEnum type, final String id, final String code) {
        this.redisTemplate.opsForValue()
                .set(this.key(type, id), code, type.getTimeout(), type.getTimeUnit());
    }

    @Override
    public boolean check(final VerifyCodeTypeEnum type, final String id, final String code) {
        final String key = this.key(type, id);
        final String codeInRedis = this.redisTemplate.opsForValue().get(key);
        final boolean result = Objects.equals(code, codeInRedis);
        if (result) {
            this.remove(type, id);
        }
        return result;
    }

    @Override
    public Optional<String> get(final VerifyCodeTypeEnum type, final String id) {
        return Optional.ofNullable(this.redisTemplate.opsForValue().get(this.key(type, id)));
    }

    @Override
    public void remove(final VerifyCodeTypeEnum type, final String id) {
        this.redisTemplate.delete(this.key(type, id));
    }

    private String key(final VerifyCodeTypeEnum type, final String id) {
        return this.prefix + ":" + type.getPrefix() + ":" + id;
    }
}
