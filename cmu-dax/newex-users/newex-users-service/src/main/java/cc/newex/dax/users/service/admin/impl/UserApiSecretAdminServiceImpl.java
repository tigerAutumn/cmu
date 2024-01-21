package cc.newex.dax.users.service.admin.impl;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserApiSecretExample;
import cc.newex.dax.users.data.UserApiSecretRepository;
import cc.newex.dax.users.domain.UserApiSecret;
import cc.newex.dax.users.service.admin.UserApiSecretAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * apikey表 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class UserApiSecretAdminServiceImpl
        extends AbstractCrudService<UserApiSecretRepository, UserApiSecret, UserApiSecretExample, Long>
        implements UserApiSecretAdminService {

    @Override
    protected UserApiSecretExample getPageExample(final String fieldName, final String keyword) {
        final UserApiSecretExample example = new UserApiSecretExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<UserApiSecret> listByPage(final PageInfo pageInfo, final Long userId, final String apiKey, final Integer brokerId) {
        if (NumberUtil.lte(userId, 0) && StringUtils.isBlank(apiKey)) {
            return this.getByPage(pageInfo);
        }

        final UserApiSecretExample userApiSecretExample = new UserApiSecretExample();
        final UserApiSecretExample.Criteria criteria = userApiSecretExample.createCriteria();
        if (Objects.nonNull(userId) && NumberUtil.gt(userId, 0)) {
            criteria.andUserIdEqualTo(userId);
        }
        if (Objects.nonNull(brokerId) && NumberUtil.gt(brokerId, 0)) {
            criteria.andBrokerIdEqualTo(brokerId);
        }
        if (StringUtils.isNotEmpty(apiKey)) {
            criteria.andApiKeyLike("%" + apiKey + "%");
        }
        return this.getByPage(pageInfo, userApiSecretExample);
    }
}
