package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.criteria.LockedPositionConfExample;
import cc.newex.dax.asset.domain.LockedPositionConf;

/**
 * 锁仓配置表 服务接口
 *
 * @author newex-team
 * @date 2018-07-04
 */
public interface LockedPositionConfService
        extends CrudService<LockedPositionConf, LockedPositionConfExample, Long> {

    /**
     * 配置列表分页查询
     *
     * @param pager 前端传递的page对象
     * @return
     */
    ResponseResult getConfigListPage(DataGridPager pager);
}
