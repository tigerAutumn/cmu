package cc.newex.dax.boss.admin.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.admin.criteria.IpWhiteExample;
import cc.newex.dax.boss.admin.domain.IpWhite;
import org.springframework.stereotype.Repository;

/**
 * 后台系统ip白名单表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface IpWhiteRepository
        extends CrudRepository<IpWhite, IpWhiteExample, Integer> {
}