package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.OrderAllExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.OrderAll;

import java.util.List;

/**
 * 全部订单表 服务接口
 *
 * @author newex-team
 * @date 2018-11-01 09:29:33
 */
public interface OrderAllService extends CrudService<OrderAll, OrderAllExample, Long> {
    /**
     * 批量查询orderAll成交数据
     *
     * @param orderList
     * @return
     */
    int batchInsertOrderAllDealOnDuplicate(List<Order> orderList);

    /**
     * 按order_id批量删除
     *
     * @param orderList
     * @return
     */
    int deleteByOrderIds(List<Order> orderList);

    /**
     * 查询用户所有合约的订单列表
     *
     * @param userId
     * @param brokerId
     * @param type
     * @return
     */
    List<OrderAll> queryOrderTypeList(Long userId, Integer brokerId, String type);

    /**
     * 查询用户所有合约的订单列表
     *
     * @param userId
     * @param brokerId
     * @param contract
     * @return
     */
    List<OrderAll> queryOrderTypeList(List<Long> userId, Integer brokerId, List<Contract> contract);

    /**
     * 通过 orderId 批量获取订单
     *
     * @param orderIds
     * @return
     */
    List<OrderAll> getInByOrderId(List<Long> orderIds);

}
