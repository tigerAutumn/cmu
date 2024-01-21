package cc.newex.dax.perpetual.matching.service;

import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.Pending;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.matching.bean.ShortOrders;

import java.util.List;

/**
 * 交易服务
 *
 * @author xionghui
 * @date 2018/10/20
 */
public interface TradeService {

  /**
   * 获取保险金
   *
   * @return
   */
  UserBalance getInsuranceBalance(Contract contract);

  /**
   * 查询最新的成交数据
   *
   * @param size
   * @param contract
   * @return
   */
  List<Pending> getRecentDeal(int size, Contract contract);

  /**
   * 查询order
   *
   * @param contract
   * @param matchStatus
   * @param limit
   * @return
   */
  List<Order> getOrderByMatchStatus(Contract contract, int matchStatus, Integer limit);

  /**
   * 更新订单的matchStatus
   *
   * @param contract
   * @param idList
   * @param matchStatus
   * @return
   */
  int editOrderMatchStatus(final Contract contract, final List<Long> idList, final int matchStatus);

  /**
   * order入库并缓存pengding、orderAll等信息
   *
   * @param contract
   * @param contractList
   * @param shortOrdersList
   * @param checkInsurance
   * @param pendingList
   * @return
   */
  void dealOrderPending(Contract contract, List<Contract> contractList,
                        List<ShortOrders> shortOrdersList, boolean checkInsurance, List<Pending> pendingList);

  /**
   * pending,orderAll等入库并发送order给push
   *
   * @param contractCode
   * @return
   */
  void dealDB(String contractCode);
}
