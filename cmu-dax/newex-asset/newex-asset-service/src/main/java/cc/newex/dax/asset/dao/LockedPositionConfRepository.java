package cc.newex.dax.asset.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.asset.criteria.LockedPositionConfExample;
import cc.newex.dax.asset.domain.LockedPositionConf;
import org.springframework.stereotype.Repository;

/**
 * 锁仓配置表 数据访问类
 *
 * @author newex-team
 * @date 2018-07-04
 */
@Repository
public interface LockedPositionConfRepository
        extends CrudRepository<LockedPositionConf, LockedPositionConfExample, Long> {
}