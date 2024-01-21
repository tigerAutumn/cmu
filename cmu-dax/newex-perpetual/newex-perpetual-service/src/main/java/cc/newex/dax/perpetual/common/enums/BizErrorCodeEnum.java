package cc.newex.dax.perpetual.common.enums;

import cc.newex.commons.support.enums.ErrorCode;
import cc.newex.commons.support.i18n.LocaleUtils;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BizErrorCodeEnum implements ErrorCode {

  /**
   * 成功
   */
  SUCCESS(0, 400),

  /**
   * 查询的数据为空
   */
  NORMAL_ERROR_DATA_NULL(1000, 400),
  /**
   * 没有登录
   */
  NOT_LOGIN(20000, 400),
  /**
   * 用户不存在
   */
  USER_NOT_EXIST(20001, 400),
  /**
   * 用户被冻结
   */
  USER_FREEZED(20002, 400),
  /**
   * 用户被爆仓冻结
   */
  USER_LIQUIDATION_FREEZED(20003, 400),
  /**
   * 合约账户被冻结
   */
  USER_ACCOUNT_FREEZED(20004, 400),
  /**
   * 用户合约账户不存在
   */
  CONTRACT_ACCOUNT_NOT_EXIST(20005, 400),
  /**
   * 必填参数为空
   */
  PARAM_IS_NULL(20006, 400),
  /**
   * 参数错误
   */
  ILLEGAL_PARAM(20007, 400),
  /**
   * 合约账户余额为空
   */
  CONTRACT_ACCOUNT_BALANCE_NULL(20008, 400),
  /**
   * 虚拟合约状态错误
   */
  CONTRACT_STATUS_ERROR(20009, 400),
  /**
   * 开通合约协议失败
   */
  OPEN_PERPETUAL_PROTOCOL_ERROR(20010, 400),

  /**
   * 系统错误
   */
  SYSTEM_ERROR(20014, 400),
  /**
   * 订单不存在
   */
  ORDER_NOT_EXIST(20015, 400),
  /**
   * 您的平仓张数大于该仓位的可平张数
   */
  CLOSE_AMOUNT_BIGGER_YOUR_POSITION(20016, 400),
  /**
   * 非本人操作
   */
  NOT_AUTHORIZED(20017, 400),
  /**
   * 下单价格非法
   */
  PRICE_NOT_ALLOWED(20018, 400),

  /**
   * 密钥不存在
   */
  SECRET_KEY_NOT_EXIST(20019, 400),
  /**
   * 指数信息不存在
   */
  INDEX_NOT_EXIST(20020, 400),

  /**
   * 合约不存在
   */
  NO_CONTRACT(20021, 400),

  /**
   * 接口调用错误
   */
  INTERFACE_ERROR(20022, 400),
  /**
   * 超过当前持仓数量,请重新提交
   */
  ORDER_STRATEGY_AMOUNT(20023, 400),
  /**
   * 下单类型错误
   */
  ORDER_STRATEGY_ILLEGAL(20024, 400),
  /**
   * 可用余额不足
   */
  AVAILABLE_BALANCE_NOT_ENOUGH(20025, 400),
  /**
   * 不是逐仓合约
   */
  NOT_A_SOLO_CONTRACT(20026, 400),
  /**
   * 需要开通合约交易
   */
  NEEDED_CONFIRM_FUTURES_EXCHANGE(20027, 400),
  /**
   * 撤单中，请耐心等待
   */
  CONTRACT_IS_CANCELING(20028, 400),

  /**
   * 撤单失败
   */
  CANCEL_ORDER_ERROR(20029, 400),
  /**
   * 下单数量不能超过单笔最大张数
   */
  ORDER_EXCEED_AMOUNT(20030, 400),
  /**
   * 全仓不能开启自动追加保证金功能
   */
  CROSS_CANT_AUTO_ADD_BOND(20031, 400),

  /**
   * 下单数量不足1张，请重新选择
   */
  ORDER_LESS_THAN_ONE_AMOUNT(20032, 400),
  /**
   * 您未开通合约，无法进行资金划转操作
   */
  USER_CANT_DEVOLVE_FUTURE(20033, 400),
  /**
   * 币种类型错误
   */
  SYMBOL_ERROR(20034, 400),
  /**
   * 币种类型错误
   */
  CONTRACT_ARE_BEING_SETTLED(20035, 400),
  /**
   * 委托价为0，暂无法进行市价全平操作
   */
  DEAL_ALL_PRICE_ERROR(20036, 400),
  /**
   * 仓位错误
   */
  MARGIN_MODE_ERROR(20037, 400),
  /**
   * 合约快照进行中
   */
  FUTURES_SNAPSHOT_ING(20038, 400),
  /**
   * 下单数量不是整数
   */
  IS_NOT_INTEGER(20039, 400),
  /**
   * 沒有最新行情信息
   */
  NO_LATEST_TICKER(20040, 400),
  /**
   * 没有交易市场信息
   */
  NO_CURRENCY_PAIR(20041, 400),
  /**
   * 不是本用户的订单
   */
  CANCEL_ORDER_USER_NOT_AGREE(20042, 400),
  /**
   * 没有K线类型
   */
  NO_KLINE_TYPE(20043, 400),
  /**
   * 市价订单不能撤单
   */
  NO_LIMIT_PRICE_TYPE(20044, 400),
  /**
   * 初始化资金账户失败
   */
  INIT_ACCOUNT_ERROR(20045, 400),
  /**
   * 下单失败，下单数量不能少于N张
   */
  ORDER_STRATEGY_MIN_AMOUNT(20046, 400),
  /**
   * 修改保证金失败
   */
  UPDATE_POSITION_MARGIN_ERROR(20047, 400),
  /**
   * 已经存在平仓单
   */
  ORDER_STATUS_ERROR(20048, 400),
  /**
   * 全仓不能修改保证金
   */
  ALLIN_UPDATE_MARGIN_ERROR(20049, 400),
  /**
   * 风险限额不在设置范围之内
   */
  RISK_OUT_LIMIT_ERROR(20050, 400),
  /**
   * 委托价值超过当前风险限额
   */
  ORDER_SIZE_ILLEGAL(20051, 400),
  /**
   * 订单价格不能低于仓位破产价格
   */
  ORDER_PRICE_SMALL_ILLEGAL(20052, 400),
  /**
   * 订单价格不能高于仓位破产价格
   */
  ORDER_PRICE_GREAT_ILLEGAL(20053, 400),
  /**
   * 调整杠杆,风险限额失败
   */
  LEVER_UPDATE_ILLEGAL(20054, 400),
  /**
   * 只能领取测试币
   */
  ILLEGAL_REWARD_CURRENCY(20055, 400),
  /**
   * 测试币每24小时只能领取一次, 还剩{0}小时
   */
  ILLEGAL_REWARD_INTERVAL(20056, 400),
  /**
   * 强平或者爆仓中不能下单
   */
  ORDER_IN_LIQUIDATE_EXPLOSION(20057, 400),
  /**
   * 爆仓和强平状态不能增加保证金
   */
  SUBMARGIN_IN_LIQUIDATE_EXPLOSION(20058, 400),
  /**
   * 爆仓状态不能减少保证金
   */
  ADDMARGIN_IN_LIQUIDATE_EXPLOSION(20059, 400),
  /**
   * 当前仓位最低保证金要求为{0},调整保证金不能低于此值
   */
  ADDMARGIN_ERROR(20060, 400),
  /**
   * 下单价格不合法
   */
  ORDER_PRICE_TYPE(20061, 400),
  /**
   * 下条件单无法获取参考价格
   */
  ORDER_CONDITION_PRICE_ERROR(20062, 400),
  /**
   * 下条件单触发价格不能和参考价格一样
   */
  ORDER_CONDITION_PRICE_TYPE(20063, 400),

  /**
   * 无仓位不能修改保证金
   */
  NO_AMOUNT_UPDATE_MARGIN_ERROR(20065, 400),
  /**
   * 下单数量不能超过个人最大订单数
   */
  ORDER_AMOUNT_MORE_MAX_ERROR(20066, 400),
    /**
     * 完成高级信息认证才可以交易
     */
    ORDER_KYC_LEVEL2_ERROR(20067, 400),
  /**
   * 订单已经被取消了
   */
  ORDER_ALREAY_CANCELLED(20068, 400),



  /**
   * ######################API错误码######################
   */
  /**
   * 该IP限制不能请求该资源
   */
  IP_RESTRICTED(20300, 400),
  /**
   * Sign签名不匹配
   */
  SIGN_NOT_MATCH(20301, 400),
  /**
   * API鉴权错误
   */
  API_PERMISSION_ERROR(20302, 400),
  /**
   * 用户请求接口过于频繁
   */
  REQUEST_TOO_HIGH(20303, 400),
  /**
   * 请求接口失败
   */
  OPERATE_ERROR(20304, 400),


  ;

  private final int code;
  private final int httpStatus;

  BizErrorCodeEnum(final int code, final int httpStatus) {
    this.code = code;
    this.httpStatus = httpStatus;
  }

  public static BizErrorCodeEnum valueOf(final int code) {
    return Arrays.stream(BizErrorCodeEnum.values()).filter(e -> e.getCode() == code).findFirst()
        .orElse(null);
  }

  @Override
  public String getMessage() {
    return LocaleUtils.getMessage("error.code.biz." + this.code);
  }

  public String getMessage(final Object... args) {
    return LocaleUtils.getMessage("error.code.biz." + this.code, args);
  }

  @Override
  public String toString() {
    return "[" + this.getCode() + "]" + this.getMessage();
  }
}
