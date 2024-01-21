package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.common.enums.BillTypeEnum;
import cc.newex.dax.perpetual.criteria.UserBalanceExample;
import cc.newex.dax.perpetual.domain.UserBalance;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 账户资产 服务接口
 *
 * @author newex -team
 * @date 2018 -11-01 09:31:54
 */
public interface UserBalanceService extends CrudService<UserBalance, UserBalanceExample, Long> {

  /**
   * 数据加锁
   *
   * @param userId 用户 id
   * @param brokerId 券商 id
   * @param currencyCode 币种 code
   * @return for update
   */
  UserBalance getForUpdate(Long userId, Integer brokerId, String currencyCode);

  /**
   * 批量锁定用户资产: 按currency asc,brokerId asc和user_id asc排序；不排序可能和其它锁的锁定顺序不一致造成死锁
   *
   * @param currencyCode the currency code
   * @param brokerId the broker id
   * @param userIds the userIds
   * @return list list
   */
  List<UserBalance> selectBatchForUpdate(String currencyCode, Integer brokerId, Set<Long> userIds);

  /**
   * 获取用户资产信息
   *
   * @param currencyCode
   * @param brokerId
   * @param userIds
   * @param pageInfo
   * @return
   */
  List<UserBalance> selectBatch(String currencyCode, Integer brokerId, List<Long> userIds, PageInfo pageInfo);

  /**
   * 获取用户 balance 的基本信息 userId brokerId currencyCode available
   *
   * @param currencyCode the currency code
   * @param brokerId the broker id
   * @return list list
   */
  List<UserBalance> selectAllBaseInfoForUpdate(String currencyCode, Integer brokerId);

  /**
   * 获取用户 balance
   *
   * @param currencyCode the currency code
   * @param brokerId     the broker id
   * @return list list
   */
  List<UserBalance> selectAll(String currencyCode, Integer brokerId);

  /**
   * 获取所有的 userId
   *
   * @param currencyCode
   * @param brokerId
   * @return
   */
  List<Long> selectAllUserId(String currencyCode, Integer brokerId);

  /**
   * 取用户资产
   *
   * @param currencyCode the currency code
   * @param userId the user id
   * @param brokerId the broker id
   * @return the user balance
   */
  UserBalance get(String currencyCode, Long userId, Integer brokerId);


  /**
   * 取用户资产列表
   *
   * @param userId 用户ID
   * @param brokerId the broker id
   * @return list list
   */
  List<UserBalance> get(Long userId, Integer brokerId);

  /**
   * 转入资产
   *
   * @param userId the user id
   * @param currencyCode the currency code
   * @param brokerId the broker id
   * @param amount the amount
   * @param billTypeEnum the bill type enum
   */
  void transferInUserBalance(long userId, String currencyCode, Integer brokerId, BigDecimal amount,
      BillTypeEnum billTypeEnum);

  /**
   * 转出资产
   *
   * @param userId the user id
   * @param currencyCode the currency code
   * @param brokerId the broker id
   * @param amount the amount
   * @param billTypeEnum the bill type enum
   */
  void transferOutUserBalance(long userId, String currencyCode, Integer brokerId, BigDecimal amount,
      BillTypeEnum billTypeEnum);

  /**
   * 查询用户资产大于0的账户
   *
   * @param userId the user id
   * @param brokerId the broker id
   * @return list list
   */
  List<UserBalance> selectByBalanceGreaterThanZero(Long userId, Integer brokerId);

  /**
   * 检查userID是否存在。不存在则创建。
   *
   * @param base the base
   * @param env the env
   * @param userId the user id
   */
  void checkUserIdExistsAndCreate(String base, Integer env, Long userId);

  /**
   * 获取资产列表时,检查合约的资金账户,没有则创建
   *
   * @param userId
   * @param brokerId
   */
  void checkUserAccountByContract(Long userId, Integer brokerId);

  /**
   * 检查用户是否存在
   *
   * @param base the base
   * @param userId the user id
   * @return boolean boolean
   */
  Boolean checkUserIdExists(String base, Long userId);

  /**
   * Check user id exists boolean.
   *
   * @param userId the user id
   * @return the boolean
   */
  Boolean checkUserIdExists(Long userId);

  /**
   * 给boss后台提供发活动币
   *
   * @param currencyCode
   * @param userId
   * @param brokerId
   * @param value
   * @return
   */
  int bossReward(String currencyCode, Long userId, Integer brokerId, BigDecimal value);

  /**
   * 随机分配资产
   */
  BigDecimal reward(String currencyCode, Long userId, Integer brokerId);

  /**
   * 开通合约分配fbtc资产
   */
  BigDecimal rewardFbtc(Long userId, Integer brokerId);

  /**
   * 获取用户的未实现盈余
   *
   * @param brokerId
   * @param userId
   * @param currencyCode
   * @param priceMap
   * @return
   */
  Map<Long, BigDecimal> unrealizedSurplus(Integer brokerId, List<Long> userId, String currencyCode, Map<String, MarkIndexPriceDTO> priceMap);

  /**
   * 更新balance的env
   *
   * @param userBalance
   * @param userBalanceExample
   * @return
   */
  int updateEnv(UserBalance userBalance, UserBalanceExample userBalanceExample);
}
