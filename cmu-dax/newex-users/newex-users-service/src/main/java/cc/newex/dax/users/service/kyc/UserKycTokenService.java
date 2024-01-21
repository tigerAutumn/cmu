package cc.newex.dax.users.service.kyc;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserKycTokenExample;
import cc.newex.dax.users.domain.UserKycToken;

/**
 * 用户kyc等级 服务接口
 *
 * @author newex-team
 * @date 2018-05-11
 */
public interface UserKycTokenService extends CrudService<UserKycToken, UserKycTokenExample, Long> {
    /**
     * @param token
     * @description通过token获取token对象
     * @date 2018/5/11 下午2:12
     */
    UserKycToken getByToken(String token);

    UserKycToken getByBizId(String bizId);

    void deleteByUserId(Long userId);

}
