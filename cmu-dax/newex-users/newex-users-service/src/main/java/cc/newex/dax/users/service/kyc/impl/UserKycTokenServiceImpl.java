package cc.newex.dax.users.service.kyc.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.UserKycTokenExample;
import cc.newex.dax.users.data.UserKycTokenRepository;
import cc.newex.dax.users.domain.UserKycToken;
import cc.newex.dax.users.service.kyc.UserKycTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户kyc等级 服务实现
 *
 * @author newex-team
 * @date 2018-05-11
 */
@Slf4j
@Service
public class UserKycTokenServiceImpl
        extends AbstractCrudService<UserKycTokenRepository, UserKycToken, UserKycTokenExample, Long>
        implements UserKycTokenService {

    @Autowired
    private UserKycTokenRepository userKycTokenRepos;

    @Override
    protected UserKycTokenExample getPageExample(final String fieldName, final String keyword) {
        final UserKycTokenExample example = new UserKycTokenExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public UserKycToken getByToken(final String token) {
        final UserKycTokenExample example = this.getPageExample("token", token);
        return this.userKycTokenRepos.selectOneByExample(example);
    }

    @Override
    public UserKycToken getByBizId(final String bizId) {
        final UserKycTokenExample example = this.getPageExample("biz_id", bizId);
        return this.userKycTokenRepos.selectOneByExample(example);
    }

    @Override
    public void deleteByUserId(final Long userId) {
        if (userId == null) {
            return;
        }

        final UserKycTokenExample example = new UserKycTokenExample();
        example.createCriteria().andUserIdEqualTo(userId);

        this.userKycTokenRepos.deleteByExample(example);
    }

}