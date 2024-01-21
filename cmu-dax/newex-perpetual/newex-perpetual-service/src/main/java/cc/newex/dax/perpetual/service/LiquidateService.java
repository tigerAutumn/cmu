package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.LiquidateExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Liquidate;

import java.util.List;

/**
 * 强平表 服务接口
 *
 * @author newex-team
 * @date 2018-11-02 11:16:40
 */
public interface LiquidateService extends CrudService<Liquidate, LiquidateExample, Long> {

    /**
     * 获取强平列表
     * @param id
     * @param size
     * @param contractCode
     * @return
     */
    List<Liquidate> listLiquidate(long id, int size, String contractCode);

    /**
     * 平仓
     * @param contract
     * @param allContract
     * @param liquidate
     */
    void liquidate(Contract contract, List<Contract> allContract, List<Liquidate> liquidate);

    /**
     * 批量插入，过滤重复数据
     *
     * @param list
     * @return
     */
    int batchInsertOnDuplicateKeyDoNothing(List<Liquidate> list);

    /**
     * 批量删除
     *
     * @param ids
     */
    void removeInById(List<Long> ids);

}