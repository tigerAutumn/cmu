package cc.newex.dax.users.service.membership.impl;

import cc.newex.commons.dictionary.consts.ApiKeyConsts;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserApiSecretExample;
import cc.newex.dax.users.data.UserApiSecretRepository;
import cc.newex.dax.users.domain.UserApiSecret;
import cc.newex.dax.users.dto.apisecret.UserApiSecretResDTO;
import cc.newex.dax.users.service.membership.UserApiSecretService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * apikey表 服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class UserApiSecretServiceImpl
        extends AbstractCrudService<UserApiSecretRepository, UserApiSecret, UserApiSecretExample, Long>
        implements UserApiSecretService {
    @Autowired
    private UserApiSecretRepository apiSecretRepos;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected UserApiSecretExample getPageExample(final String fieldName, final String keyword) {
        final UserApiSecretExample example = new UserApiSecretExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<UserApiSecret> getApiSecrets(final long userId, final Integer brokerId) {
        final UserApiSecretExample example = new UserApiSecretExample();
        final UserApiSecretExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if (null != brokerId) {
            criteria.andBrokerIdEqualTo(brokerId);
        }
        return this.apiSecretRepos.selectByExample(example);
    }

    @Override
    public long count(final long userId) {
        final UserApiSecretExample example = new UserApiSecretExample();
        example.createCriteria()
                .andUserIdEqualTo(userId);
        return this.apiSecretRepos.countByExample(example);
    }

    @Override
    public boolean existLabel(final String label, final long userId, final long id) {
        final UserApiSecretExample example = new UserApiSecretExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andLabelEqualTo(label)
                .andIdNotEqualTo(id);
        return this.apiSecretRepos.countByExample(example) > 0;
    }

    @Override
    public boolean existLabel(final String label, final long userId) {
        final UserApiSecretExample example = new UserApiSecretExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andLabelEqualTo(label);
        return this.apiSecretRepos.countByExample(example) > 0;
    }

    @Override
    public int save(final UserApiSecret record) {
        return this.apiSecretRepos.insert(record);
    }

    @Override
    public UserApiSecret getApiSecret(final long userId, final long id) {
        final UserApiSecretExample example = new UserApiSecretExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andIdEqualTo(id);
        return this.apiSecretRepos.selectOneByExample(example);
    }

    @Override
    public UserApiSecret getApiSecretById(final Long id) {
        return this.apiSecretRepos.selectById(id);
    }

    @Override
    public UserApiSecret getApiSecret(final long userId, final String apiKey) {
        final UserApiSecretExample example = new UserApiSecretExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andApiKeyEqualTo(apiKey);
        return this.apiSecretRepos.selectOneByExample(example);
    }

    @Override
    public UserApiSecret getApiSecret(final String apiKey) {
        if (StringUtils.isBlank(apiKey)) {
            return null;
        }

        final UserApiSecretExample example = new UserApiSecretExample();
        example.createCriteria().andApiKeyEqualTo(apiKey);

        return this.apiSecretRepos.selectOneByExample(example);
    }

    @Override
    public UserApiSecretResDTO getApiSecretByCache(final String apiKey) {
        final String jsonStr = this.stringRedisTemplate.opsForValue().get(ApiKeyConsts.OPEN_API_KEY_PREFIX + apiKey);
        if (StringUtils.isNotBlank(jsonStr)) {
            final UserApiSecretResDTO userApiSecretResDTO = JSON.parseObject(jsonStr, UserApiSecretResDTO.class);

            return userApiSecretResDTO;
        }

        final UserApiSecret userApiSecret = this.getApiSecret(apiKey);
        if (userApiSecret == null) {
            return null;
        }

        final ModelMapper mapper = new ModelMapper();

        // OpenApiKeyInfo 和 UserApiSecretResDTO 的字段一样
        final UserApiSecretResDTO userApiSecretResDTO = mapper.map(userApiSecret, UserApiSecretResDTO.class);

        userApiSecretResDTO.setAuthorities(JSON.parseArray(userApiSecret.getAuthorities(), String.class));

        this.stringRedisTemplate.opsForValue().set(ApiKeyConsts.OPEN_API_KEY_PREFIX + apiKey, JSON.toJSONString(userApiSecretResDTO), 10, TimeUnit.MINUTES);

        return userApiSecretResDTO;
    }

    @Override
    public int update(final UserApiSecret apiSecret) {
        return this.apiSecretRepos.updateById(apiSecret);
    }

    @Override
    public int delete(final long id, final long userId, final String apiKey) {
        final UserApiSecretExample example = new UserApiSecretExample();
        example.createCriteria()
                .andIdEqualTo(id)
                .andUserIdEqualTo(userId)
                .andApiKeyEqualTo(apiKey);
        return this.apiSecretRepos.deleteByExample(example);
    }
}