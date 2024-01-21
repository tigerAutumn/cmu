package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.criteria.LockedPositionExample;
import cc.newex.dax.asset.domain.LockedPosition;
import cc.newex.dax.asset.dto.UserLockedPositionDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 锁仓记录表 服务接口
 *
 * @author newex-team
 * @date 2018-07-04
 */
public interface LockedPositionService
        extends CrudService<LockedPosition, LockedPositionExample, Long> {

    /**
     * 锁仓
     * @param isGive 是否赠送（从10号账户）
     */
    ResponseResult lockUserPosition(LockedPosition lockedPosition, String tradeNo, boolean isGive, String operator);

    ResponseResult lockUserPositionBatch(UserLockedPositionDto userLockedPositionDto);

    ResponseResult notifyBizUnlockPosition(BigDecimal amount, LockedPosition lockedPosition, String tradeNo);

    /**
     * 解锁
     *
     * @param amount         锁仓金额
     * @param lockedPosition 锁仓实体
     * @return
     */
    ResponseResult unlockUserPosition(BigDecimal amount, LockedPosition lockedPosition, String operator, String tradeNo);

    /**
     * 批量锁仓
     *
     * @param lockedIds 批量解锁id 逗号隔开
     * @param amount    解锁数量
     * @return
     */
    ResponseResult batchUnlockUser(List<Long> lockedIds, BigDecimal amount, String operator);
}
