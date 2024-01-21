package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.common.enums.OrderSystemTypeEnum;
import cc.newex.dax.perpetual.criteria.OrderShardingExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.OrderAll;
import cc.newex.dax.perpetual.domain.OrderMarginFee;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.MarkIndexReasonablePriceDTO;
import cc.newex.dax.perpetual.dto.OrderDTO;
import cc.newex.dax.perpetual.dto.enums.OrderFromEnum;
import cc.newex.dax.users.client.UsersClient;
import com.alibaba.fastjson.JSONObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * 订单表 服务接口
 *
 * @author newex-team
 * @date 2018-10-30 18:49:48
 */
public interface OrderShardingService extends CrudService<Order, OrderShardingExample, Long> {

    /*
     * For Unittest
     */
    void setUsersClient(UsersClient usersClient);

    /**
     * 检查订单保证金不足的订单,撤销订单
     */
    boolean checkOrderMargin(final ContractCodeUserIdBrokerIdBean contractCodeUserIdBrokerIdBean,
                             final CurrencyPair currencyPair, final Contract contract);

    /**
     * 检查订单保证金不足的订单,撤销订单
     */
    boolean checkOrderMarginWithBlance(final UserBalance userBalance, final UserPosition userPosition,
                                       final ContractCodeUserIdBrokerIdBean contractCodeUserIdBrokerIdBean,
                                       final CurrencyPair currencyPair, final Contract contract, final boolean check);

    /**
     * 创建order表
     *
     * @param shardTable
     * @return
     */
    int createOrderIfNotExists(final ShardTable shardTable);

    /**
     * 根据matchStatus查询订单
     *
     * @param contractCode
     * @param matchStatus
     * @param limit
     * @param shardTable
     * @return
     */
    List<Order> getOrderByMatchStatus(final String contractCode, final int matchStatus,
                                      final Integer limit, final ShardTable shardTable);

    /**
     * 批量查询用户挂单
     *
     * @param contractCode
     * @param brokerId
     * @param userIds
     * @param shardTable
     * @return
     */
    List<Order> selectBatchOrder(final String contractCode, final Integer brokerId,
                                 final Set<Long> userIds, final ShardTable shardTable);

    /**
     * 计算订单的保证金和手续费
     *
     * @param userPosition
     * @param firstSellPrice
     * @param maxFeeRate
     * @param orderList
     */
    OrderMarginFee countOrderMarginAndFee(UserPosition userPosition, BigDecimal firstSellPrice,
                                          BigDecimal maxFeeRate, List<Order> orderList);

    /**
     * 获取分表
     *
     * @param contractCode
     * @return
     */
    ShardTable getOrderShardTable(String contractCode);

    /**
     * 通过订单 id 判断订单是否完成态
     *
     * @param contractCode
     * @param orderId
     * @return
     */
    List<Order> selectInByOrderId(String contractCode, List<Long> orderId);

    Order getByOrderId(String contractCode, Long orderId);

    /**
     * 查询用户下单数据
     *
     * @param userId   用户id
     * @param brokerId 券商id
     * @param pairCode 合约code
     */
    List<Order> queryTradeOrderList(Long userId, Integer brokerId, String pairCode);

    /**
     * 按照id和pairCode分页查询订单列表
     *
     * @param contractCode
     * @return
     */
    List<Order> queryContractCodeOrderList(String contractCode);

    /**
     * 查询订单
     *
     * @param contractCode
     * @param brokerId
     * @param userIds
     * @return
     */
    List<Order> queryOrder(String contractCode, Integer brokerId, List<Long> userIds);

    /**
     * 批量撤单 返回撤单的 订单id
     *
     * @param brokerId     券商 id
     * @param userId       用户 id
     * @param contractCode 币对
     * @return 生成的撤单 id
     */
    void cancelAll(Integer brokerId, Long userId, String... contractCode);

    void cancelAll(List<Order> list, String contractCode, OrderSystemTypeEnum cancelType);

    /**
     * 批量撤单
     *
     * @param brokerId
     * @param userId
     * @param contractCode
     * @param orderSystemTypeEnum
     */
    void cancelAllByContractCode(Integer brokerId, Long userId, String contractCode, OrderSystemTypeEnum orderSystemTypeEnum);

    /**
     * 取消 订单价超出范围的订单 price <= min || price >= max
     *
     * @param userBalance  用户 资产
     * @param contractCode 合约code
     * @param min          最小值（可以为空）
     * @param max          最大值（可以为空）
     * @param sideEnum     订单方向
     */
    void cancelOverPriceOrder(UserBalance userBalance, String contractCode, BigDecimal min,
                              BigDecimal max, OrderSideEnum sideEnum);

    /**
     * 计算开仓保证金
     */
    OrderMarginFee getOpenMargin(List<Order> orderList, UserPosition userPosition, Order order,
                                 Contract contract, BigDecimal firstSellPrice,
                                 MarkIndexReasonablePriceDTO markIndexReasonablePriceDTO);

    /**
     * 获取订单
     *
     * @param userId       用户 id
     * @param brokerId     券商 id
     * @param contractCode 币对如 p_btc_usde
     * @param status       状态
     * @param side         方向
     * @return
     */
    List<OrderAll> getUserOrders(Long userId, Integer brokerId, List<String> contractCode,
                                 List<Integer> status, String side);

    /**
     * 抓单 撮合重启 抓单 正常抓单
     *
     * @param operateStatus
     * @param status
     * @return
     */
    List<Order> fetchOrders(Integer operateStatus, List<Integer> status, String pairCode);

    /**
     * 根据仓位类型获取订单列表
     *
     * @param userId
     * @param brokerId
     * @param contractCode
     * @param type
     */
    List<Order> queryOrderTypeList(Long userId, Integer brokerId, String contractCode, String type);

    /**
     * 计算保证金
     *
     * @param orderDTO 下单dto
     * @return
     */
    JSONObject calculateMargin(OrderDTO orderDTO);

    /**
     * 下单流程
     *
     * @param orderDTO 下单dto
     * @return
     */
    ResponseResult dealOrder(OrderDTO orderDTO, OrderFromEnum orderFromEnum);

    /**
     * 下单 （爆仓单、平仓单）
     *
     * @param userBalance
     * @param orderDTO
     * @param orderFromEnum
     * @return
     */
    ResponseResult dealOrder(UserBalance userBalance, UserPosition position, OrderDTO orderDTO,
                             OrderFromEnum orderFromEnum, Contract contract, CurrencyPair currencyPair);

    ResponseResult cancelOrder(Long userId, Integer brokerId, Long orderId, final String contractCode);

    /**
     * 计划委托下单流程
     *
     * @param orderDTO 下单dto
     * @return
     */
    ResponseResult dealConditionOrder(OrderDTO orderDTO, OrderFromEnum orderFromEnum);

    /**
     * 批量下单
     *
     * @param userPositions
     * @param orderFromEnum
     * @param orderDTOS
     * @param contract
     */
    List<Order> batchPlaceOrder(List<UserPosition> userPositions, OrderFromEnum orderFromEnum,
                                List<OrderDTO> orderDTOS, Contract contract);

    /**
     * 批量删除
     *
     * @param orderList  pojo记录集
     * @param shardTable 分表对象
     * @return 影响的记录数
     */
    void batchDelete(List<Order> orderList, ShardTable shardTable);

    /**
     * 记录合约code，用户id已经券商id
     *
     * @author xionghui
     * @date 2018/11/23
     */
    @Data
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ContractCodeUserIdBrokerIdBean {
        /**
         * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
         */
        private String contractCode;
        /**
         * 用户id
         */
        private Long userId;
        /**
         * 券商id
         */
        private Integer brokerId;
    }
}
