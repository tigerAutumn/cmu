package cc.newex.dax.users.service.kyc;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserKycLevelExample;
import cc.newex.dax.users.domain.UserKycLevel;

import java.util.Optional;

/**
 * 用户kyc等级 服务接口
 *
 * @author newex-team
 * @date 2018-05-03
 */
public interface UserKycLevelService extends CrudService<UserKycLevel, UserKycLevelExample, Long> {
    /**
     * @param userId
     * @description 通过用户id获取用户kyc等级
     * @date 2018/5/8 上午11:56
     */
    Optional<UserKycLevel> getByUserid(Long userId);

    /**
     * 根据条件获取查询的总记录数
     *
     * @param example 查询条件参数
     * @return 总记录数
     */
    int countByExample(UserKycLevelExample example);

    void deleteByUserId(Long userId);

}
