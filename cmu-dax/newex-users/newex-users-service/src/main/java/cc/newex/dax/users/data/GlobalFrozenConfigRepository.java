package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.GlobalFrozenConfigExample;
import cc.newex.dax.users.domain.GlobalFrozenConfig;
import org.springframework.stereotype.Repository;

/**
 * 系统业务全局冻结配置表 数据访问类
 *
 * @author newex-team
 * @date 2018-07-08
 */
@Repository
public interface GlobalFrozenConfigRepository
        extends CrudRepository<GlobalFrozenConfig, GlobalFrozenConfigExample, Integer> {
}