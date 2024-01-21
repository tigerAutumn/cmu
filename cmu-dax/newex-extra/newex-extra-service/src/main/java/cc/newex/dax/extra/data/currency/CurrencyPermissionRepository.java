package cc.newex.dax.extra.data.currency;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.currency.CurrencyPermissionExample;
import cc.newex.dax.extra.domain.currency.CurrencyPermission;
import org.springframework.stereotype.Repository;

/**
 * 币种wiki用户权限管理表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-08-20 17:32:13
 */
@Repository
public interface CurrencyPermissionRepository extends CrudRepository<CurrencyPermission, CurrencyPermissionExample, Long> {
}