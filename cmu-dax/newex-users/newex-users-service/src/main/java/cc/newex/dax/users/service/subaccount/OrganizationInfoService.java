package cc.newex.dax.users.service.subaccount;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.subaccount.OrganizationInfoExample;
import cc.newex.dax.users.domain.subaccount.OrganizationInfo;

/**
 * 组织详情表 服务接口
 *
 * @author newex-team
 * @date 2018-10-31 16:16:36
 */
public interface OrganizationInfoService extends CrudService<OrganizationInfo, OrganizationInfoExample, Long> {

    OrganizationInfo getByUserId(Long userId);

}