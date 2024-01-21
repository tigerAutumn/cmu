package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.asset.criteria.UnlockedPositionExample;
import cc.newex.dax.asset.domain.UnlockedPosition;

import java.util.Date;

/**
 * 解锁计划表 服务接口
 *
 * @author newex-team
 * @date 2018-07-05
 */
public interface UnlockedPositionService
        extends CrudService<UnlockedPosition, UnlockedPositionExample, Long> {

    boolean unlockedPositionJob(Date time);
}
