package cc.newex.dax.users.service.kyc;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserKycEventExample;
import cc.newex.dax.users.domain.UserKycEvent;

/**
 * 用户kyc审核操作记录 服务接口
 *
 * @author newex-team
 * @date 2018-05-21
 */
public interface UserKycEventService extends CrudService<UserKycEvent, UserKycEventExample, Long> {

    void deleteByUserId(Long userId);

}
