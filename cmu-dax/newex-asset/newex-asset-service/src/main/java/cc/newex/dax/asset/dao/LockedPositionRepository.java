package cc.newex.dax.asset.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.asset.criteria.LockedPositionExample;
import cc.newex.dax.asset.domain.LockedPosition;
import org.springframework.stereotype.Repository;

/**
 * 锁仓记录表 数据访问类
 *
 * @author newex-team
 * @date 2018-07-04
 */
@Repository
public interface LockedPositionRepository
        extends CrudRepository<LockedPosition, LockedPositionExample, Long> {
}