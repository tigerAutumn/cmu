package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.UserBalanceExample;
import cc.newex.dax.perpetual.domain.UserBalance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 账户资产 数据访问类
 *
 * @author newex-team
 * @date 2018-11-01 09:31:54
 */
@Repository
public interface UserBalanceRepository
    extends CrudRepository<UserBalance, UserBalanceExample, Long> {
  /**
   * 加锁查询用户资金表
   *
   * @param userId
   * @param currencyCode 币种
   */
  UserBalance selectForUpdate(@Param("userId") long userId, @Param("brokerId") Integer brokerId,
      @Param("currencyCode") String currencyCode);

  /**
   * 批量锁定用户资产
   */
  List<UserBalance> selectBatchForUpdate(@Param("currencyCode") String currencyCode,
      @Param("brokerId") Integer brokerId, @Param("set") Set<Long> userIds);

  /**
   * 锁住所有用户，并只返回 userId
   *
   * @param currencyCode
   * @param brokerId
   * @return
   */
  List<UserBalance> selectAllUserIdForUpdate(@Param("currencyCode") String currencyCode,
      @Param("brokerId") Integer brokerId);

  /**
   * 统计sum(balance+frozen_balance)
   *
   * @return
   */
  List<Long> selectAllUserId(@Param("currencyCode") String currencyCode,
                             @Param("brokerId") Integer brokerId);

  /**
   * 查询用户资产大于0的账户
   *
   * @param userId
   * @param brokerId
   * @return
   */
  List<UserBalance> selectByBalanceGreaterThanZero(@Param("userId") long userId,
      @Param("brokerId") long brokerId);

  /**
   * 查询币种下所有的用户资产
   *
   * @param currencyCode
   * @return
   */
  UserBalance selectSumBalanceByCurrencyCode(@Param("currencyCode") String currencyCode);

  /**
   * 更新env
   *
   * @param record
   * @param example
   * @return
   */
  int updateEnvByExample(@Param("record") UserBalance record, @Param("example") UserBalanceExample example);
}
