package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.PreLiquidateAlertExample;
import cc.newex.dax.perpetual.domain.PreLiquidateAlert;

import java.util.List;

/**
 * 风险告警表 服务接口
 *
 * @author newex-team
 * @date 2018-11-14 12:09:15
 */
public interface PreLiquidateAlertService extends CrudService<PreLiquidateAlert, PreLiquidateAlertExample, Long> {

    /**
     * 批量插入，过滤重复数据
     *
     * @param list
     * @return
     */
    int batchInsertOnDuplicateKeyDoNothing(List<PreLiquidateAlert> list);

    /**
     * 获取告警列表
     *
     * @param index
     * @param size
     * @param contractCode
     * @return
     */
    List<PreLiquidateAlert> alertList(long index, int size, String contractCode);

    /**
     * 删除已发送并过期的告警数据
     *
     * @param contractCode
     */
    void removeExpireList(String contractCode);
}