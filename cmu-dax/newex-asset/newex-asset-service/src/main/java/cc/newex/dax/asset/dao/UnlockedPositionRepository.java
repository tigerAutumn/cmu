package cc.newex.dax.asset.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.asset.criteria.UnlockedPositionExample;
import cc.newex.dax.asset.domain.UnlockedPosition;
import org.springframework.stereotype.Repository;

/**
 * 解锁计划表 数据访问类
 *
 * @author newex-team
 * @date 2018-07-05
 */
@Repository
public interface UnlockedPositionRepository
        extends CrudRepository<UnlockedPosition, UnlockedPositionExample, Long> {
}