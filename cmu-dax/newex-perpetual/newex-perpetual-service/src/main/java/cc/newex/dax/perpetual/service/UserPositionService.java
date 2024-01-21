package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.common.enums.BillTypeEnum;
import cc.newex.dax.perpetual.common.enums.OrderSideEnum;
import cc.newex.dax.perpetual.criteria.UserPositionExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.SystemBill;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.domain.UserBill;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.domain.bean.BrokerUserBean;
import cc.newex.dax.perpetual.domain.bean.CurrentPosition;
import cc.newex.dax.perpetual.domain.bean.PositionPubConfig;
import cc.newex.dax.perpetual.domain.bean.ShortOrder;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.dto.enums.MakerEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户合约持仓数据表 服务接口
 *
 * @author newex-team
 * @date 2018-11-01 09:32:15
 */
public interface UserPositionService extends CrudService<UserPosition, UserPositionExample, Long> {

  /**
   * 构建userBill
   */
  public static UserBill buildUserBill(final UserBalance userBalance, final String contractCode, final String uId,
                                       final BillTypeEnum billTypeEnum, final String detailSide, final BigDecimal amount,
                                       final BigDecimal size, final BigDecimal price, final String feeCurrencyCode,
                                       final BigDecimal fee, final BigDecimal profit, final BigDecimal beforePosition,
                                       final BigDecimal afterPosition, final MakerEnum makerEnum, final Long orderId) {
    final BigDecimal beforeBalance =
        userBalance.getAvailableBalance().add(userBalance.getFrozenBalance())
            .add(userBalance.getPositionMargin()).add(userBalance.getPositionFee())
            .add(userBalance.getOrderMargin()).add(userBalance.getOrderFee());
    BigDecimal afterBalance = beforeBalance.add(profit);
    if (userBalance.getCurrencyCode().equals(feeCurrencyCode)) {
      afterBalance = afterBalance.subtract(fee);
    }
    final Date date = new Date();
    return UserBill.builder().uId(uId).userId(userBalance.getUserId())
        .contractCode(contractCode).currencyCode(userBalance.getCurrencyCode())
        .type(billTypeEnum.getBillType()).detailSide(detailSide).amount(amount).price(price)
        .size(size).feeCurrencyCode(feeCurrencyCode).fee(fee).profit(profit)
        .beforePosition(beforePosition).afterPosition(afterPosition).beforeBalance(beforeBalance)
        .afterBalance(afterBalance).makerTaker(makerEnum.getCode()).referId(orderId)
        .createdDate(date).modifyDate(date).brokerId(userBalance.getBrokerId()).build();
  }

  /**
   * 构建systemBill
   */
  public static SystemBill buildSystemBill(final UserBalance balance, final String feeCurrencyCode,
      final BigDecimal fee, final BigDecimal profit, final String uId) {
    final Date date = new Date();
    return SystemBill.builder().userId(balance.getUserId()).currencyCode(balance.getCurrencyCode())
        .feeCurrencyCode(StringUtils.isBlank(feeCurrencyCode) ? "" : feeCurrencyCode).fee(fee)
        .createdDate(date).modifyDate(date).profit(profit).uId(uId).brokerId(balance.getBrokerId())
        .build();
  }

  /**
   * 更新用户仓位杠杆
   *
   * @param userId
   * @param brokerId
   * @param contractCode
   * @param type
   * @param lever
   * @param gear
   * @return
   */
  UserPosition updateLeverGear(boolean isLever, Long userId, Integer brokerId, String contractCode,
      Integer type, BigDecimal lever, BigDecimal gear);

  /**
   * 批量查询用户持仓
   *
   * @param contractCode
   * @param brokerId
   * @param userIds
   * @return
   */
  List<UserPosition> selectBatchPosition(String contractCode, Integer brokerId, Set<Long> userIds);

  /**
   * 批量查询用户持仓
   *
   * @param currencyCode
   * @param brokerId
   * @param userIds
   * @return
   */
  List<UserPosition> selectBatchPositionByBase(String currencyCode, Integer brokerId,
      Set<Long> userIds);

  /**
   * 头部用户计算
   *
   * @param contractCode
   */
  void sortUserPosition(String contractCode);

  /**
   * 获取头部用户
   *
   * @param contractCode
   * @param sideEnum
   */
  List<UserRank> getUserRank(String contractCode, OrderSideEnum sideEnum);

  /**
   * 获取用户持仓数据
   *
   * @param userId 用户 id
   * @param brokerId 券商 id
   * @param contractCode 币对
   * @return 用户持仓数据
   */
  UserPosition getUserPosition(Long userId, Integer brokerId, String contractCode);

  /**
   * 获取用户持仓
   *
   * @param userId
   * @param brokerId
   * @param contractCode
   * @return
   */
  List<UserPosition> getUserPosition(Long userId, Integer brokerId, List<String> contractCode);

  /**
   * 获取用户仓位,持有仓位和已平仓位
   *
   * @param userId 用户id
   * @param brokerId 券商id
   * @param contractCode 合约
   * @return
   */
  List<CurrentPosition> getUserPositionByType(Long userId, Integer brokerId, String contractCode);

  /**
   * 初始化账户和持仓数据
   *
   * @param contractCode
   * @param userId
   * @param brokerId
   */
  void initAccount(String contractCode, Long userId, Integer brokerId);

  /**
   * 仓位和限额设置
   *
   * @param userId
   * @param brokerId
   * @param contractCode
   * @return
   */
  PositionPubConfig getPositionConfig(Long userId, Integer brokerId, String contractCode);

  /**
   * 获取 positionid, 按 base 分组，并且有效持仓的条数 >= count
   *
   * @param base
   * @param brokerId
   * @param base
   * @return
   */
  List<UserPosition> getUserPositionByBase(Long userId, Integer brokerId, String base);

  /**
   * 获取 positionid, 按 base 分组，并且有效持仓的条数 >= count
   *
   * @param id
   * @param base
   * @param brokerId
   * @param base
   * @return
   */
  List<UserPosition> getUserPositionWithoutIdByBase(Long id, Long userId, Integer brokerId,
      String base);

  /**
   * 获取高风险的用户持仓数据 long : prePrice >= price; short : prePrice >= price
   *
   * @param index
   * @param size
   * @param contractCode
   * @return
   */
  List<UserPosition> getHighRiskUserPosition(long index, int size, BigDecimal price,
      String contractCode);

  /**
   * 处理交易和手续费
   */
  void transfer(Contract contract, boolean isContraTrade,
      Map<String, MarkIndexPriceDTO> markIndexPriceMap, List<Contract> contractList,
      ShortOrder takerShortOrder, Order takerOrder, ShortOrder makerShortOrder, Order makerOrder,
      Map<BrokerUserBean, UserBalance> userBalanceMap,
      Map<BrokerUserBean, Map<String, UserPosition>> userPositionMap, BigDecimal pointsCardPrice,
      Map<BrokerUserBean, UserBalance> pointsCardBalanceMap, List<UserBill> userBillList,
      List<SystemBill> systemBillList);

  /**
   * 获取 positionid, 按 base 分组，并且有效持仓的条数 >= count
   *
   * @param base
   * @param brokerId
   * @param count
   * @return
   */
  List<Long> getUserIdGroupByBase(String base, Integer brokerId, int count);

  /**
   * 刷新强平价
   *
   * @param userId 用户 id
   * @param brokerId 券商 id
   * @param base contract.base
   * @param contractList contract.base 相同的所有 contract
   */
  void refreshLiquidatePrice(Long userId, Integer brokerId, String base,
      List<Contract> contractList);

  /**
   * 批量获取
   *
   * @param id
   * @return
   */
  List<UserPosition> getInById(List<Long> id);

  /**
   * 修改保证金
   *
   * @param userId
   * @param brokerId
   * @param contractCode
   * @param margin
   * @return
   */
  int updateOpenMargin(Long userId, Integer brokerId, String contractCode, String margin);

  BigDecimal lowestOpenMargin(Long userId, Integer brokerId, String contractCode);

  List<UserPosition> allPosition(Integer brokerId, Long userId, List<Contract> allContract,
      Contract excludeContract);

  Map<Long, List<UserPosition>> positionMap(Integer brokerId, List<Contract> allContract,
      Contract excludeContract);

  Map<Long, List<UserPosition>> positionMap(Integer brokerId, List<Long> userIds, List<Contract> allContract,
                                            Contract excludeContract);

  BigDecimal sumTotalUserPosition(String contractCode);

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public class UserRank {
    /**
     * user positoin id
     */
    private Long prositionId;
    /**
     * 用户 id
     */
    private Long userId;
    /**
     * 券商 id
     */
    private Integer brokerId;
    /**
     * 币对
     */
    private String contractCode;
    /**
     * 持仓张数，不一定是最新值，只能用作参考
     */
    private BigDecimal amount;
    /**
     * 盈利百分比
     */
    private BigDecimal score;
  }
}
