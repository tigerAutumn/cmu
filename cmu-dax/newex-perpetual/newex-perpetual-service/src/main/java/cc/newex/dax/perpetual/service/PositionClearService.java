package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.common.enums.PositionClearEnum;
import cc.newex.dax.perpetual.criteria.PositionClearExample;
import cc.newex.dax.perpetual.domain.PositionClear;

import java.util.List;

/**
 * 用户持仓清算流水表 服务接口
 *
 * @author newex-team
 * @date 2018-12-07 18:40:15
 */
public interface PositionClearService extends CrudService<PositionClear, PositionClearExample, Long> {

    List<PositionClear> getPositionClear(Integer brokerId, Long userId, String contractCode, PageInfo pageInfo);

    List<PositionClear> getPositionClear(Integer brokerId, Long contractId);

    List<PositionClear> getPositionClear(String contractCode, PositionClearEnum status, Long startId, int size);

    void batchUpdateStatus(List<Long> ids, PositionClearEnum status);
}