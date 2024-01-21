package cc.newex.dax.users.data.subaccount;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.subaccount.OrganizationInfoExample;
import cc.newex.dax.users.domain.subaccount.OrganizationInfo;
import org.springframework.stereotype.Repository;

/**
 * 组织详情表 数据访问类
 *
 * @author newex-team
 * @date 2018-10-31 16:16:36
 */
@Repository
public interface OrganizationInfoRepository extends CrudRepository<OrganizationInfo, OrganizationInfoExample, Long> {
}