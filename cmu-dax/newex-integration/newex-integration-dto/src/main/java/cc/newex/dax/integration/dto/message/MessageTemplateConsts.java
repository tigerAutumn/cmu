package cc.newex.dax.integration.dto.message;

/**
 * 信息模板Key常量
 */
public class MessageTemplateConsts {
    /**
     * 尊敬的用户您好！感谢您选择CoinTobe！您的注册验证码为：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_MOBILE_REGISTER_CODE = "SMS_USERS_MOBILE_REGISTER_CODE";
    /**
     * 尊敬的用户您好！恭喜您注册成功！感谢您选择CoinTobe！
     */
    public static final String SMS_USERS_MOBILE_REGISTER_SUCCESS = "SMS_USERS_MOBILE_REGISTER_SUCCESS";
    /**
     * 尊敬的用户您好，您的账号于${time}登录CoinTobe，若非您本人操作，请及时修改密码。
     */
    public static final String SMS_USERS_MOBILE_LOGIN = "SMS_USERS_MOBILE_LOGIN";
    /**
     * 尊敬的用户您好！您正在修改登录密码。验证码： ${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_LOGIN_PWD_MODIFY_CODE = "SMS_USERS_LOGIN_PWD_MODIFY_CODE";
    /**
     * 尊敬的用户您好！您正在重设登录密码。验证码：${code}，验证码有效时间：30分钟。 请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_LOGIN_PWD_RESET_CODE = "SMS_USERS_LOGIN_PWD_RESET_CODE";
    /**
     * 手机号绑定,尊敬的用户您好！您正在绑定手机号。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_MOBILE_BIND_CODE = "SMS_USERS_MOBILE_BIND_CODE";
    /**
     * 尊敬的用户您好！恭喜您绑定手机号成功，手机尾号为 ${phoneSuffix}。
     */
    public static final String SMS_USERS_MOBILE_BIND_SUCCESS = "SMS_USERS_MOBILE_BIND_SUCCESS";
    /**
     * 尊敬的用户您好！您正在修改手机号。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_MOBILE_MODIFY_CODE = "SMS_USERS_MOBILE_MODIFY_CODE";
    /**
     * 尊敬的用户您好！恭喜您修改手机号成功，新手机尾号为 ${phoneSuffix}。
     */
    public static final String SMS_USERS_MOBILE_MODIFY_SUCCESS = "SMS_USERS_MOBILE_MODIFY_SUCCESS";
    /**
     * 尊敬的用户您好！恭喜您绑定邮箱成功！绑定邮箱为 ${shadeEmail}。感谢您选择CoinTobe。
     */
    public static final String SMS_USERS_MAIL_BIND_SUCCESS = "SMS_USERS_MAIL_BIND_SUCCESS";
    /**
     * 尊敬的用户您好！恭喜您修改邮箱成功！新邮箱为 ${shadeEmail}。感谢您选择CoinTobe。
     */
    public static final String SMS_USERS_MAIL_MODIFY_SUCCESS = "SMS_USERS_MAIL_MODIFY_SUCCESS";
    /**
     * 尊敬的用户您好！您正在关闭手机号验证码功能。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_MOBILE_VERIFY_CLOSE_CODE = "SMS_USERS_MOBILE_VERIFY_CLOSE_CODE";
    /**
     * 尊敬的用户您好！您正在开启手机号验证码功能。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_MOBILE_VERIFY_OPEN_CODE = "SMS_USERS_MOBILE_VERIFY_OPEN_CODE";
    /**
     * 尊敬的用户您好！您正在绑定谷歌身份验证。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_GOOGLE_BIND_CODE = "SMS_USERS_GOOGLE_BIND_CODE";
    /**
     * 尊敬的用户您好！您正在重置谷歌验证。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_GOOGLE_RESET_CODE = "SMS_USERS_GOOGLE_RESET_CODE";
    /**
     * 尊敬的用户您好！您正在开启谷歌身份验证。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_GOOGLE_VERIFY_OPEN_CODE = "SMS_USERS_GOOGLE_VERIFY_OPEN_CODE";
    /**
     * 尊敬的用户您好！您正在关闭谷歌身份验证。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_GOOGLE_VERIFY_CLOSE_CODE = "SMS_USERS_GOOGLE_VERIFY_CLOSE_CODE";
    /**
     * 尊敬的用户您好！您正在开启登录谷歌验证码。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_GOOGLE_LOGIN_OPEN_CODE = "SMS_USERS_GOOGLE_LOGIN_OPEN_CODE";
    /**
     * 尊敬的用户您好！您正在关闭登录谷歌验证码。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_GOOGLE_LOGIN_CLOSE_CODE = "SMS_USERS_GOOGLE_LOGIN_CLOSE_CODE";
    /**
     * 尊敬的用户您好！您正在设置资金密码。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_PAY_PWD_SET_CODE = "SMS_USERS_PAY_PWD_SET_CODE";
    /**
     * 尊敬的用户您好！您正在重设资金密码。验证码：${code}，验证码有效时间：30分钟。 请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_PAY_PWD_RESET_CODE = "SMS_USERS_PAY_PWD_RESET_CODE";
    /**
     * 尊敬的用户您好！您正在修改资金密码。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_PAY_PWD_MODIFY_CODE = "SMS_USERS_PAY_PWD_MODIFY_CODE";
    /**
     * 尊敬的用户您好！您正在关闭交易资金密码。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_PAY_PWD_CLOSE_CODE = "SMS_USERS_PAY_PWD_CLOSE_CODE";
    /**
     * 尊敬的用户您好！您正在新增apikey。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_API_CREATE_CODE = "SMS_USERS_API_CREATE_CODE";
    /**
     * 尊敬的用户您好！您正在查看api密匙信息。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_API_VIEW_CODE = "SMS_USERS_API_VIEW_CODE";
    /**
     * 尊敬的用户您好！您正在重置api密匙信息。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_API_RESET_CODE = "SMS_USERS_API_RESET_CODE";
    /**
     * 尊敬的用户您好！您正在删除apiKey。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_API_DELETE_CODE = "SMS_USERS_API_DELETE_CODE";
    /**
     * 尊敬的用户您好！感谢您选择CoinTobe，您的登录验证码为： ${code} ，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_USERS_LOGIN_STEP2AUTH_CODE = "SMS_USERS_LOGIN_STEP2AUTH_CODE";
    /**
     * 尊敬的用户您好，恭喜您成功申请CoinTobe账号，账户名称：${loginName}，默认初始密码：${password}，请尽快登录CoinTobe官网修改账户密码。
     */
    public static final String SMS_USERS_APPLY_REGISTER = "SMS_USERS_APPLY_REGISTER";
    /**
     * 尊敬的用户您好！${timestamp}您的账户登录IP发生了变化。登录的详细信息如下：地点：${address}，IP 地址：${ipAddress}，如果这不是您本人的操作，请立刻修改您的登录密码。${antiphishing}
     */
    public static final String SMS_USERS_LOGIN_IP_CHANGE = "SMS_USERS_LOGIN_IP_CHANGE";
    /**
     * 尊敬的用户您好！由于操作次数过多，您的账户暂时被锁定，请注意资金安全。${antiphishing}
     */
    public static final String SMS_USERS_LOGIN_RETRY_LIMIT_LOCK = "SMS_USERS_LOGIN_RETRY_LIMIT_LOCK";
    /**
     * 余额不足提醒：尊敬的用户，您账户的币数量过低，类型:${taskType},币种:${symbol},附加类型:${extType},站点:${website},账户:${account},初始值:${initBalance},当前值:${currentBalance}。
     */
    public static final String SMS_TAKE_TRADE_WARN2 = "SMS_TAKE_TRADE_WARN2";
    /**
     * 补差额提醒：尊敬的用户，你账户的币已达到阀值，类型:${taskType},币种:${symbol},附加类型:${extType},差值:${difference}。
     */
    public static final String SMS_TAKE_TRADE_WARN1 = "SMS_TAKE_TRADE_WARN1";
    /**
     * 现货${currency}账单在${time}时有异常, 数据库用户总余额${dbUser}，账单用户总余额${baseUser}，请及时确认和处理。
     */
    public static final String SMS_SYSTEM_USER_BILL_WARN0 = "SMS_SYSTEM_USER_BILL_WARN0";
    /**
     * 尊敬的用户，您的${productName}的${marginLever}杠杆账户风险率已经达到${riskRatio}，请注意补仓以免发生爆仓
     */
    public static final String SMS_TYPE_MARGIN_FORCE_CLOSE_PRE = "SMS_TYPE_MARGIN_FORCE_CLOSE_PRE";
    /**
     * 尊敬的用户，因行情剧烈波动,您的${productName}的${marginLever}杠杆账户已爆仓
     */
    public static final String SMS_TYPE_MARGIN_FORCE_CLOSE = "SMS_TYPE_MARGIN_FORCE_CLOSE";
    /**
     * 你的ECS云主机${hostName}于${time}${alertStatus}，${service}${status}，${alertStatus}信息：${message}，影响应用${application}，请登录系统查看详情。
     */
    public static final String SMS_AGENT_MONITOR_HEALTH = "SMS_AGENT_MONITOR_HEALTH";
    /**
     * 任务信息:${detail}
     */
    public static final String SMS_DTS_MONITOR_ERROR = "SMS_DTS_MONITOR_ERROR";
    /**
     * 指数未生成: market信息:${marketDetail},最近更新时间:${createdDate}
     */
    public static final String SMS_MSG_MARKET_LATEST_TICKER_ERROR = "SMS_MSG_MARKET_LATEST_TICKER_ERROR";
    /**
     * k线数据未生成：错误信息:${kLineDetail}
     */
    public static final String SMS_MSG_MARKET_LATEST_KLINE_ERROR = "SMS_MSG_MARKET_LATEST_KLINE_ERROR";
    /**
     * 尊敬的用户，${amount}个${coin}已存入您的账户，登录CoinTobe官网www.cointobe.com查看。
     */
    public static final String SMS_DELIVER_COIN = "SMS_DELIVER_COIN";
    /**
     * 您正在CoinToBe上绑定${coin}接收地址，验证码：${code}。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_ASSET_WITHDRAW_ADDRESS_BIND_CODE = "SMS_ASSET_WITHDRAW_ADDRESS_BIND_CODE";
    /**
     * 您正在CoinToBe提现${coin}，验证码：${code}。
     */
    public static final String SMS_ASSET_WITHDRAW_CODE = "SMS_ASSET_WITHDRAW_CODE";
    /**
     * 尊敬的用户，您充值的${amount}个${coin}已存入您的账户。请登录官网查看。
     */
    public static final String SMS_ASSET_DEPOSIT = "SMS_ASSET_DEPOSIT";
    /**
     * 尊敬的用户，您提现的${amount}个${coin}已完成汇出。详情请登录官网查看。
     */
    public static final String SMS_ASSET_WITHDRAW_SUCCESS = "SMS_ASSET_WITHDRAW_SUCCESS";
    /**
     * 尊敬的用户您好！恭喜您登录密码修改成功！
     */
    public static final String SMS_USERS_LOGIN_PWD_MODIFY_SUCCESS = "SMS_USERS_LOGIN_PWD_MODIFY_SUCCESS";
    /**
     * 感谢您选择CoinTobe
     */
    public static final String SMS_USERS_LOGIN_STEP2AUTH_SUCCESS = "SMS_USERS_LOGIN_STEP2AUTH_SUCCESS";
    /**
     * ${currency} 的区块最后更新时间为 ${updateTime} , 请及时查看
     */
    public static final String SMS_WALLET_CLIENT_WARNING_SUCCESS = "SMS_WALLET_CLIENT_WARNING_SUCCESS";
    /**
     * 小主您好！恭喜您因为参加 {activeName} 活动获得{amount}个 {currencyCode} 币， 现已汇入您的币币账户。请您前往官网账户查收。
     */
    public static final String SMS_USERS_ACTIVITY_SUCCESS = "SMS_USERS_ACTIVITY_SUCCESS";

    /**
     * 尊敬的用户您好！您正在设置银行卡收款信息。验证码：${code}，验证时间3分钟内有效，请勿向任何人包括客服提供验证码，以免造成账户或资金损失。
     */
    public static final String SMS_USERS_FIAT_BANK_SETTING_CODE = "SMS_USERS_FIAT_BANK_SETTING_CODE";
    /**
     * 尊敬的用户您好！您正在设置支付宝收款信息。验证码：${code}，验证时间3分钟内有效，请勿向任何人包括客服提供验证码，以免造成账户或资金损失。
     */
    public static final String SMS_USERS_FIAT_ALIPAY_SETTING_CODE = "SMS_USERS_FIAT_ALIPAY_SETTING_CODE";
    /**
     * 尊敬的用户您好！您正在设置微信收款信息。验证码：${code}，验证时间3分钟内有效，请勿向任何人包括客服提供验证码，以免造成账户或资金损失。
     */
    public static final String SMS_USERS_FIAT_WEPAY_SETTING_CODE = "SMS_USERS_FIAT_WEPAY_SETTING_CODE";
    /**
     * 尊敬的用户您好！您申请的KYC2认证审核失败，失败原因：身份证与照片信息不匹配。
     */
    public static final String SMS_USERS_KYC2_AUDIT_FAIL_SUCCESS = "SMS_USERS_KYC2_AUDIT_FAIL_SUCCESS";
    /**
     * 您有一个新订单，请及时处理：订单号为${orderNo}，${trade}单价为${unitPrice}元的${currency}${number}个，总价为${totalPrice}元
     */
    public static final String SMS_C2C_NEW_ORDER_MESSAGE_TO_ACCEPTOR = "SMS_C2C_NEW_ORDER_MESSAGE_TO_ACCEPTOR";
    /**
     * 您有一个新的待支付订单，请于10分钟内支付，否则将会超时自动取消 订单号为：${orderNo}，买入单价为${totalPrice}的${currency}${number}个，总价为${totalPrice}元。
     */
    public static final String SMS_C2C_PAYMENT_REMIND_MESSAGE_TO_BUYER = "SMS_C2C_PAYMENT_REMIND_MESSAGE_TO_BUYER";
    /**
     * 您订单号为${orderNo}，买入单价为${unitPrice}元，币种为${currency}，数量为${number}个，总价为${totalPrice}元的订单由于超时未支付，系统已自动取消
     */
    public static final String SMS_C2C_ORDER_CANCELLED_AUTO_MESSAGE_TO_CREATOR = "SMS_C2C_ORDER_CANCELLED_AUTO_MESSAGE_TO_CREATOR";
    /**
     * 您订单号为${orderNo}，售出单价为${unitPrice}元，币种为${currency}，数量为${number}个，总价为${totalPrice}元的订单由于对方超时未支付，系统已自动取消
     */
    public static final String SMS_C2C_ORDER_CANCELLED_AUTO_MESSAGE_TO_ACCEPTOR = "SMS_C2C_ORDER_CANCELLED_AUTO_MESSAGE_TO_ACCEPTOR";
    /**
     * 您订单号为${orderNo}，售出单价为${unitPrice}元，币种为${currency}，数量为${number}个，总价为${totalPrice}元的订单对方已完成支出，请及时查账，确认到账后请及时放币，若未收到款项，可申请客诉处理 1小时内未处理订单，将会自动放币给对方。
     */
    public static final String SMS_C2C_ORDER_PAID_MESSAGE_TO_SELLER = "SMS_C2C_ORDER_PAID_MESSAGE_TO_SELLER";
    /**
     * 您订单号为${orderNo}，售出单价为${unitPrice}元，币种为${currency}，数量为${number}个，总价为${totalPrice}元的订单对方已完成支出，请及时查账，确认到账后请及时放币，若未收到款项，可申请客诉处理 12小时内未处理订单，将会自动放币给对方。
     */
    public static final String SMS_C2C_SECOND_ORDER_PAID_MESSAGE_TO_SELLER = "SMS_C2C_SECOND_ORDER_PAID_MESSAGE_TO_SELLER";
    /**
     * 您订单号为${orderNo}，售出单价为${unitPrice}元，币种为${currency}，数量为${number}个，总价为${totalPrice}元的订单对方已完成支出，请及时查账，确认到账后请及时放币，若未收到款项，可申请客诉处理,50分钟内未处理订单，将会自动放币给对方。
     */
    public static final String SMS_C2C_THIRD_ORDER_PAID_MESSAGE_TO_SELLER = "SMS_C2C_THIRD_ORDER_PAID_MESSAGE_TO_SELLER";
    /**
     * 您订单号为${orderNo}，售出单价为${unitPrice}元，币种为${currency}，数量为${number}个，总价为${totalPrice}元的订单对方已完成支出，由于您1小时未处理，系统已自动放币给对方账户，如有疑问，请联系平台客服
     */
    public static final String SMS_C2C_ORDER_COMPLETED_AUTO_MESSAGE_TO_SELLER = "SMS_C2C_ORDER_COMPLETED_AUTO_MESSAGE_TO_SELLER";

    /**
     * 您订单号为${orderNo}，买入单价为${unitPrice}元，币种为${currency}，数量为${number}个，总价为${totalPrice}元的订单对方已确认收款并放币，请您及时查收
     */
    public static final String SMS_C2C_ORDER_COMPLETED_MESSAGE_TO_BUYER = "SMS_C2C_ORDER_COMPLETED_MESSAGE_TO_BUYER";

    /**
     * 您订单号为${orderNo}，买入/卖出单价为${unitPrice}元，币种为${currency}数量为${number}个，总价为${totalPrice}元的订单已被客服取消冻结
     */
    public static final String SMS_C2C_SERVICE_CANCEL_FROZEN_ORDER = "SMS_C2C_SERVICE_CANCEL_FROZEN_ORDER";
    /**
     * 您订单号为${orderNo}，买入/卖出单价为${unitPrice}元，币种为${currency}数量为${number}个，总价为${totalPrice}元的订单已被客服取消
     */
    public static final String SMS_C2C_SERVICE_CANCEL_ORDER = "SMS_C2C_SERVICE_CANCEL_ORDER";
    /**
     * 您订单号为${orderNo}，买入/卖出单价为${unitPrice}元，币种为${currency}数量为${number}个，总价为${totalPrice}元的订单已进入客诉仲裁流程，平台客服将会联系您，请保持联系电话畅通
     */
    public static final String SMS_C2C_ARBITRATION = "SMS_C2C_ARBITRATION";
    /**
     * 您订单号为${orderNo}，买入单价为${unitPrice}元，币种为${currency}数量为${number}个，总价为${totalPrice}元的订单买家已上传支付凭证，如若您再次确认没有收到款项，可发起客诉仲裁，平台客服将会处理订单
     */
    public static final String SMS_C2C_ORDER_PAID_WITH_PROOFS_MESSAGE_TO_SELLER = "SMS_C2C_ORDER_PAID_WITH_PROOFS_MESSAGE_TO_SELLER";

    /**
     * 您订单号为${orderNo}，买入单价为${unitPrice}元，币种为${currency}数量为${number}个，总价为${totalPrice}元的订单卖家已申请冻结，您可以上传支付凭证证明付款行为，如果您十分钟内未处理，平台客服将会仲裁处理订单
     */
    public static final String SMS_C2C_ORDER_FROZEN = "SMS_C2C_ORDER_FROZEN";
    /**
     * 您委托买入/卖出${currency}的交易单由于您的完成率低于${limitTradingOrderCompleteRate}%，已被系统取消，您可以通过主动与其它交易单成交提高完成率
     */
    public static final String SMS_C2C_LOW_STRUTS_AUTO_CANCEL_TRADING_ORDER = "SMS_C2C_LOW_STRUTS_AUTO_CANCEL_TRADING_ORDER";
    /**
     * 您委托买入${currency}的交易单，由于该交易单下的订单已连续超时取消${cancelOrderTradingNum}次，已被系统取消。
     */
    public static final String SMS_C2C_AUTO_CANCEL_ORDER_TRADING = "SMS_C2C_AUTO_CANCEL_ORDER_TRADING";

    /**
     * 法币${currency}账单在${time}时有异常, 数据库用户总余额${userBalance}，账单用户总余额${userSize}，请及时确认和处理。
     */
    public static final String SMS_SYSTEM_C2C_USER_BILL_WARN0 = "SMS_SYSTEM_C2C_USER_BILL_WARN0";

    /**
     * 钱包余额对账预警，时间:{threshold}，内容(币种[阈值,超出值]):{currency}，请及时处理。
     */
    public static final String SMS_WALLET_BALANCE_SUMMARY_ALERT = "SMS_WALLET_BALANCE_SUMMARY_ALERT";

    /**
     * 警告：用户持仓、持仓冻结数量出现异常UserId:${freezedUserId}
     */
    public static final String SMS_FUTURE_CLOSE_FREEZED_MONITOR = "SMS_FUTURE_CLOSE_FREEZED_MONITOR";

    /**
     * 警告：合约持仓中多仓数量 != 空仓数量 contractid：${contractId}
     */
    public static final String SMS_FUTURE_POSITION_ERROR_MONITOR = "SMS_FUTURE_POSITION_ERROR_MONITOR";

    /**
     * "警告：合约挂单中出现错误数量挂单 marketFrom：${errorMarket}"
     */
    public static final String SMS_FUTURE_ORDER_AMOUNT_ERROR_MONITOR = "SMS_FUTURE_ORDER_AMOUNT_ERROR_MONITOR";

    /**
     * 对账数据异常 future check 币种: ${symbolStr},合约内部对账出现不平,合约id: ${errorContract},差值: ${diffVal}
     */
    public static final String SMS_FUTURE_ACCOUNT_ERROR_MONITOR = "SMS_FUTURE_ACCOUNT_ERROR_MONITOR";

    /**
     * 对账异常 future check 币种
     */
    public static final String SMS_FUTURE_ACCOUNT_MONITOR = "SMS_FUTURE_ACCOUNT_MONITOR";

    /**
     * 转入转出减用户总余额
     */
    public static final String SMS_FUTURE_USER_TANSFER_IN_AND_OUT_SUB_BALANCE = "SMS_FUTURE_USER_TANSFER_IN_AND_OUT_SUB_BALANCE";

    /**
     * 合约用户账户权益小于零
     */
    public static final String SMS_FUTURE_ACCOUNT_RIGHTS_LESS_THAN_ZERO = "SMS_FUTURE_ACCOUNT_RIGHTS_LESS_THAN_ZERO";

    /**
     * 一分钟k线${marketFrom}超过5分钟未生成
     */
    public static final String SMS_FUTURE_KLINE_FAIL_WARN = "SMS_FUTURE_KLINE_FAIL_WARN";

    /**
     * 合约爆仓逻辑: 持仓冻结数量大于零, 明细: ${detailMsg}
     */
    public static final String SMS_FUTURE_EXPLODE_NOW_CONTRACT_FREEZE_ERROR = "SMS_FUTURE_EXPLODE_NOW_CONTRACT_FREEZE_ERROR";

    /**
     * 尊敬的${userName}用户,您的${symbolDesc}虚拟仓位保证金率已经达到30%，请注意仓位控制以免发生爆仓。
     */
    public static final String SMS_FUTURE_LIQUIDATION_WARN_DEPOSIT = "SMS_FUTURE_LIQUIDATION_WARN_DEPOSIT";

    /**
     * 尊敬的${userName}用户,您的${contractName}${direct}${leverRate}倍杠杆仓位合约盈亏比已经达到-70%，请注意仓位控制以免发生爆仓。
     */
    public static final String SMS_FUTURE_LIQUIDATION_WARN_LOSS = "SMS_FUTURE_LIQUIDATION_WARN_LOSS";

    /**
     * 用户ID${userId}邮件发送失败
     */
    public static final String SMS_FUTURE_EMAIL_SEND_FAIL_WARN = "SMS_FUTURE_EMAIL_SEND_FAIL_WARN";

    /**
     * 尊敬的${userName}用户:您的${contractName}计划委托单已于${date}在价格为${price}时被触发
     */
    public static final String SMS_FUTURE_PLAN_ENTRUST_NOTIFY = "SMS_FUTURE_PLAN_ENTRUST_NOTIFY";

    /**
     * 尊敬的用户,因行情剧烈波动,您的${symbolDesc}虚拟仓位已爆仓。
     */
    public static final String SMS_FUTURE_LIQUIDATION_DONE_DEPOSIT = "SMS_FUTURE_LIQUIDATION_DONE_DEPOSIT";

    /**
     * 尊敬的用户,因行情剧烈波动,您的${contractName}${direct}${leverRate}倍杠杆仓位已爆仓。
     */
    public static final String SMS_FUTURE_LIQUIDATION_DONE_LOSS = "SMS_FUTURE_LIQUIDATION_DONE_LOSS";

    /**
     * 【服务健康告警】机器: ${agentId}, 详情：${message}
     */
    public static final String SMS_PUBLISH_ALERT = "SMS_PUBLISH_ALERT";

    /**
     * 您委托买入${currency}的交易单，由于该交易单下剩余的订单金额低于您自定义的最小金额或小于系统最小下单金额，已被自动取消该委托单
     */
    public static final String SMS_C2C_AUTO_CANCEL_BUY_ORDER_TRADING_PRICE = "SMS_C2C_AUTO_CANCEL_BUY_ORDER_TRADING_PRICE";

    /**
     * 您委托卖出${currency}的交易单，由于该交易单下剩余的订单金额低于您自定义的最小金额或小于系统最小下单金额，已被自动取消该委托单
     */
    public static final String SMS_C2C_AUTO_CANCEL_SELL_ORDER_TRADING_PRICE = "SMS_C2C_AUTO_CANCEL_SELL_ORDER_TRADING_PRICE";

    /**
     * 系统账号:${userId},可用资产为:${available},需要发放数量为:${amount},时间为:${time},请及时检查。
     */
    public static final String SMS_SPOT_ACTIVITY_ACCOUNT_NOT_ENOUGH = "SMS_SPOT_ACTIVITY_ACCOUNT_NOT_ENOUGH";

    /**
     * 亲爱的CoinMex用户，您的锁仓资产${amount}枚${currency}已经解锁，您可以前往官网资产管理-币币账户-锁仓管理中进行提取。
     */
    public static final String SMS_ASSET_UNLOCK_POSITION_NOTIFY = "SMS_ASSET_UNLOCK_POSITION_NOTIFY";
    /**
     * 尊敬的用户您好！<br/><br/>您正在点卡转让。验证码：${code}，验证码有效时间：3分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String SMS_CARDPOINT_TRANSFER_CODE = "SMS_CARDPOINT_TRANSFER_CODE";
    /**
     * 尊敬的用户， 您好！您转出到 ${toAccount} 地址的 ${amount} 个 点卡 已完成汇出， 交易ID ${txurl} 。更多详细信息请登录 CoinMex 官网 https://www.coinmex.com/ 查看。感谢您选择 CoinMex 平台。CoinMex 团队详情请登录官网查看。
     */
    public static final String SMS_CARDPOINT_TRANSFER_SUCCESS = "SMS_CARDPOINT_TRANSFER_SUCCESS";

    public static final String SMS_SYSTEM_ACTIVITY_MINING_LIMIT_WARN0 = "SMS_SYSTEM_ACTIVITY_MINING_LIMIT_WARN0";

    public static final String SMS_SYSTEM_ACTIVITY_MINING_FEE_RETURN = "SMS_SYSTEM_ACTIVITY_MINING_FEE_RETURN";

    public static final String SMS_SYSTEM_XXL_WARN = "SMS_SYSTEM_XXL_WARN";

    public static final String SMS_SYSTEM_PORTFOLIO_HOLD_BILL_WARN0 = "SMS_SYSTEM_PORTFOLIO_HOLD_BILL_WARN0";

    public static final String SMS_SYSTEM_PORTFOLIO_BILL_WARN0 = "SMS_SYSTEM_PORTFOLIO_BILL_WARN0";

    public static final String SMS_CS_REGISTER_VERIFICATION = "SMS_CS_REGISTER_VERIFICATION";

    public static final String SMS_CS_RESET_PASSWORD = "SMS_CS_RESET_PASSWORD";

    public static final String SMS_CS_PASSWORD_ERROR = "SMS_CS_PASSWORD_ERROR";

    public static final String SMS_CS_LOGIN_SUCCESS = "SMS_CS_LOGIN_SUCCESS";

    public static final String SMS_CS_NEW_DEVICE_LOGIN_VERIFICATION = "SMS_CS_NEW_DEVICE_LOGIN_VERIFICATION";

    public static final String SMS_CS_REGISTER_SUCCESS = "SMS_CS_REGISTER_SUCCESS";

    public static final String SMS_USERS_OPEN_API_REGISTER_SUCCESS = "SMS_USERS_OPEN_API_REGISTER_SUCCESS";

    public static final String SMS_SPOT_CIRCUIT_BREAKER = "SMS_SPOT_CIRCUIT_BREAKER";

    public static final String SMS_CS_CHANGE_MOBILE = "SMS_CS_CHANGE_MOBILE";

    public static final String SMS_CS_RESET_GRAPH_MOBILE = "SMS_CS_RESET_GRAPH_MOBILE";

    //================================MAIL======================================================================//

    /**
     * 邮箱注册成功,尊敬的用户您好！<br><br>恭喜您注册成功！${antiphishing}
     */
    public static final String MAIL_USERS_REGISTER_SUCCESS = "MAIL_USERS_REGISTER_SUCCESS";
    /**
     * 尊敬的用户您好！<br><br>您正在进行邮箱注册账号。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。邮箱注册激活后不可修改。
     */
    public static final String MAIL_USERS_REGISTER_CODE = "MAIL_USERS_REGISTER_CODE";
    /**
     * 尊敬的用户您好！<br><br>您正在激活邮箱。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。邮箱激活后不可修改。
     */
    public static final String MAIL_USERS_ACTIVE_CODE = "MAIL_USERS_ACTIVE_CODE";
    /**
     * 尊敬的用户您好！<br><br>恭喜您激活邮箱成功！激活邮箱为 ${shadeEmail}。${antiphishing}
     */
    public static final String MAIL_USERS_ACTIVE_SUCCESS = "MAIL_USERS_ACTIVE_SUCCESS";
    /**
     * 尊敬的用户您好！您正在修改邮箱。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_USERS_MODIFY_CODE = "MAIL_USERS_MODIFY_CODE";
    /**
     * 尊敬的用户您好！<br><br>恭喜您登录密码修改成功！${antiphishing}
     */
    public static final String MAIL_USERS_LOGIN_PWD_MODIFY_SUCCESS = "MAIL_USERS_LOGIN_PWD_MODIFY_SUCCESS";
    /**
     * 尊敬的用户您好！<br><br>恭喜您登录密码重置成功！${antiphishing}
     */
    public static final String MAIL_USERS_LOGIN_PWD_RESET = "MAIL_USERS_LOGIN_PWD_RESET";
    /**
     * 尊敬的用户您好！<br><br>恭喜您绑定手机号成功，手机尾号为 ${phoneSuffix}。${antiphishing}
     */
    public static final String MAIL_USERS_MOBILE_BIND_SUCCESS = "MAIL_USERS_MOBILE_BIND_SUCCESS";
    /**
     * 尊敬的用户您好！<br><br>恭喜您修改手机号成功，新手机尾号为 ${phoneSuffix}。${antiphishing}
     */
    public static final String MAIL_USERS_MOBILE_MODIFY_SUCCESS = "MAIL_USERS_MOBILE_MODIFY_SUCCESS";
    /**
     * 尊敬的用户您好！<br><br>您已经${operation}手机号安全验证设置。${antiphishing}
     */
    public static final String MAIL_USERS_MOBILE_VERIFY_SWITCH = "MAIL_USERS_MOBILE_VERIFY_SWITCH";
    /**
     * 尊敬的用户您好！<br><br>恭喜您绑定邮箱成功！绑定邮箱为 ${shadeEmail}。${antiphishing}
     */
    public static final String MAIL_USERS_BIND_SUCCESS = "MAIL_USERS_BIND_SUCCESS";
    /**
     * 尊敬的用户您好！<br><br>恭喜您修改邮箱成功！新邮箱为 ${shadeEmail}。${antiphishing}
     */
    public static final String MAIL_USERS_MODIFY_SUCCESS = "MAIL_USERS_MODIFY_SUCCESS";
    /**
     * 尊敬的用户您好！<br><br>恭喜您设置防钓鱼码成功！你的防钓鱼码为：${firstAntiphishing}
     */
    public static final String MAIL_USERS_ANTIPHISHING_SET = "MAIL_USERS_ANTIPHISHING_SET";
    /**
     * 尊敬的用户您好！<br><br>恭喜您绑定谷歌验证码成功！${antiphishing}
     */
    public static final String MAIL_USERS_GOOGLE_BIND_SUCCESS = "MAIL_USERS_GOOGLE_BIND_SUCCESS";
    /**
     * 尊敬的用户您好！<br><br>恭喜您重置谷歌验证码成功！${antiphishing}
     */
    public static final String MAIL_USERS_GOOGLE_RESET_SUCCESS = "MAIL_USERS_GOOGLE_RESET_SUCCESS";
    /**
     * 尊敬的用户您好！<br><br>您已经${operation}谷歌验证码安全设置。${antiphishing}
     */
    public static final String MAIL_USERS_GOOGLE_VERIFY_SWITCH = "MAIL_USERS_GOOGLE_VERIFY_SWITCH";
    /**
     * 尊敬的用户您好！<br><br>恭喜您登录${operation}谷歌验证码重置成功！${antiphishing}
     */
    public static final String MAIL_USERS_GOOGLE_LOGIN_SWITCH = "MAIL_USERS_GOOGLE_LOGIN_SWITCH";
    /**
     * 尊敬的用户您好！<br><br>恭喜您设置资金密码成功！${antiphishing}
     */
    public static final String MAIL_USERS_PAY_PWD_SUCCESS = "MAIL_USERS_PAY_PWD_SUCCESS";
    /**
     * 尊敬的用户您好！<br><br>恭喜您重置资金密码成功！${antiphishing}
     */
    public static final String MAIL_USERS_PAY_PWD_RESET_SUCCESS = "MAIL_USERS_PAY_PWD_RESET_SUCCESS";
    /**
     * 尊敬的用户您好！<br><br>恭喜您修改资金密码成功！${antiphishing}
     */
    public static final String MAIL_USERS_MODIFY_PAY_PWD_SUCCESS = "MAIL_USERS_MODIFY_PAY_PWD_SUCCESS";
    /**
     * 尊敬的用户您好！<br><br>${operation} 请注意资金安全。${antiphishing}
     */
    public static final String MAIL_USERS_PAY_PWD_SECURITY = "MAIL_USERS_PAY_PWD_SECURITY";
    /**
     * 尊敬的用户您好！<br><br>${timestamp}您的账户登录IP发生了变化。登录的详细信息如下： <br><br>地点：${address}<br>IP 地址：${ipAddress}<br><br>如果这不是您本人的操作，请立刻修改您的登录密码。${antiphishing}
     */
    public static final String MAIL_USERS_LOGIN_IP_CHANGE = "MAIL_USERS_LOGIN_IP_CHANGE";
    /**
     * 尊敬的用户您好！<br><br>由于操作次数过多，您的账户暂时被锁定，请注意资金安全。${antiphishing}
     */
    public static final String MAIL_USERS_LOGIN_RETRY_LIMIT_LOCK = "MAIL_USERS_LOGIN_RETRY_LIMIT_LOCK";
    /**
     * 尊敬的用户您好，恭喜您成功申请CoinToBe账号，账户名称：${loginName}，默认初始密码：${password}，请尽快登录官网修改账户密码。
     */
    public static final String MAIL_USERS_APPLY_REGISTER = "MAIL_USERS_APPLY_REGISTER";
    /**
     * 尊敬的用户：<br/>您好！<br/>您正在提现${coin}，验证码：${code}<br/>该验证码非常重要，请勿将此邮件泄露给任何人。<br/>防钓鱼码：${emailVerify}
     */
    public static final String MAIL_ASSET_WITHDRAW_CODE = "MAIL_ASSET_WITHDRAW_CODE";
    /**
     * 认证地址邮件验证码,尊敬的用户：<br/> 您好！<br/> 认证地址验证码：${code}<br/> 您正在CoinTobe网站对${act}地址进行认证<br/>该验证码非常重要，请勿将此邮件泄露给任何人。<br/>防钓鱼码：${emailVerify}
     */
    public static final String MAIL_ASSET_AUTH_ADDRESS_CODE = "MAIL_ASSET_AUTH_ADDRESS_CODE";
    /**
     * 尊敬的用户，<br/> 您好！<br/> 您充值的${amount}个${coin}已存入您的账户。请登录官网查看。
     */
    public static final String MAIL_ASSET_DEPOSIT = "MAIL_ASSET_DEPOSIT";
    /**
     * 尊敬的用户，<br/> 您好！<br/> 您提现的${amount}个${coin}已完成汇出。详情请登录官网查看。
     */
    public static final String MAIL_ASSET_WITHDRAW_SUCCESS = "MAIL_ASSET_WITHDRAW_SUCCESS";
    /**
     * 尊敬的用户您好！您正在重设登录密码。验证码：${code}，验证码有效时间：30分钟。 请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_USERS_LOGIN_PWD_RESET_CODE = "MAIL_USERS_LOGIN_PWD_RESET_CODE";
    /**
     * 尊敬的用户您好！您正在绑定谷歌身份验证。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_USERS_GOOGLE_BIND_CODE = "MAIL_USERS_GOOGLE_BIND_CODE";
    /**
     * 尊敬的用户您好！您正在重置谷歌验证。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_USERS_GOOGLE_RESET_CODE = "MAIL_USERS_GOOGLE_RESET_CODE";
    /**
     * 尊敬的用户您好！<br/><br/>您正在开启谷歌身份验证。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_USERS_GOOGLE_VERIFY_OPEN_CODE = "MAIL_USERS_GOOGLE_VERIFY_OPEN_CODE";
    /**
     * 尊敬的用户您好！<br/><br/>您正在关闭谷歌身份验证。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_USERS_GOOGLE_VERIFY_CLOSE_CODE = "MAIL_USERS_GOOGLE_VERIFY_CLOSE_CODE";
    /**
     * 尊敬的用户您好！您正在新增apikey。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_USERS_API_CREATE_CODE = "MAIL_USERS_API_CREATE_CODE";
    /**
     * 尊敬的用户您好！您正在查看api密匙信息。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_USERS_API_VIEW_CODE = "MAIL_USERS_API_VIEW_CODE";
    /**
     * 尊敬的用户您好！您正在重置api密匙信息。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_USERS_API_RESET_CODE = "MAIL_USERS_API_RESET_CODE";
    /**
     * 尊敬的用户您好！您正在删除apiKey。验证码：${code}，验证码有效时间：30分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_USERS_API_DELETE_CODE = "MAIL_USERS_API_DELETE_CODE";
    /**
     * 尊敬的用户您好！<br/><br/>感谢您选择CoinTobe，您的登录验证码为： ${code} ，验证码有效时间：3分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_USERS_LOGIN_STEP2AUTH_CODE = "MAIL_USERS_LOGIN_STEP2AUTH_CODE";
    /**
     * 您已通过登录短信或谷歌验证码二次校验
     */
    public static final String MAIL_USERS_LOGIN_STEP2AUTH_SUCCESS = "MAIL_USERS_LOGIN_STEP2AUTH_SUCCESS";
    /**
     * 尊敬的用户您好！您正在设置银行卡收款信息。验证码：${code}，验证时间3分钟内有效，请勿向任何人包括客服提供验证码，以免造成账户或资金损失。
     */
    public static final String MAIL_USERS_FIAT_BANK_SETTING_CODE = "MAIL_USERS_FIAT_BANK_SETTING_CODE";
    /**
     * 尊敬的用户您好！您正在设置支付宝收款信息。验证码：${code}，验证时间3分钟内有效，请勿向任何人包括客服提供验证码，以免造成账户或资金损失。
     */
    public static final String MAIL_USERS_FIAT_ALIPAY_SETTING_CODE = "MAIL_USERS_FIAT_ALIPAY_SETTING_CODE";
    /**
     * 尊敬的用户您好！您正在设置微信收款信息。验证码：${code}，验证时间3分钟内有效，请勿向任何人包括客服提供验证码，以免造成账户或资金损失。
     */
    public static final String MAIL_USERS_FIAT_WEPAY_SETTING_CODE = "MAIL_USERS_FIAT_WEPAY_SETTING_CODE";
    /**
     * 您好，（${realName}：${account}）有一笔法币交易申请仲裁，订单号：${orderId}，请及时处理。
     */
    public static final String MAIL_USERS_REJECT_PAYMENT_CODE = "MAIL_USERS_REJECT_PAYMENT_CODE";
    /**
     * 尊敬的用户您好！您申请的KYC2认证审核失败，失败原因：身份证与照片信息不匹配。
     */
    public static final String MAIL_USERS_KYC2_AUDIT_FAIL_SUCCESS = "MAIL_USERS_KYC2_AUDIT_FAIL_SUCCESS";
    /**
     * 停止提现前各币种提现状态，时间:{threshold}，状态:{currency}。
     */
    public static final String MAIL_WALLET_BALANCE_WITHDRABLE_STATUS = "MAIL_WALLET_BALANCE_WITHDRABLE_STATUS";

    /**
     * 【服务健康告警】机器: ${agentId}, 详情：${message}
     */
    public static final String MAIL_PUBLISH_ALERT = "MAIL_PUBLISH_ALERT";
    /**
     * 尊敬的用户您好！<br/><br/>您正在点卡转让。验证码：${code}，验证码有效时间：3分钟。请勿向任何人包括客服提供验证码。
     */
    public static final String MAIL_CARDPOINT_TRANSFER_CODE = "MAIL_CARDPOINT_TRANSFER_CODE";
    /**
     * 尊敬的用户， 您好！您转出到 ${toAccount} 地址的 ${amount} 个 点卡 已完成汇出， 交易ID ${txurl} 。更多详细信息请登录 CoinMex 官网 https://www.coinmex.com/ 查看。感谢您选择 CoinMex 平台。CoinMex 团队详情请登录官网查看。
     */
    public static final String MAIL_CARDPOINT_TRANSFER_SUCCESS = "MAIL_CARDPOINT_TRANSFER_SUCCESS";

    public static final String MAIL_SYSTEM_XXL_WARN = "MAIL_SYSTEM_XXL_WARN";

    /**
     * 小主您好！恭喜您因为参加 ${activeName} 活动获得${amount}个 ${currencyCode} 币， 现已汇入您的币币账户。请您前往官网账户查收
     */
    public static final String MAIL_USERS_ACTIVITY_SUCCESS = "MAIL_USERS_ACTIVITY_SUCCESS";

    public static final String MAIL_CS_PASSWORD_ERROR = "MAIL_CS_PASSWORD_ERROR";

    public static final String MAIL_CS_RESET_PASSWORD = "MAIL_CS_RESET_PASSWORD";

    public static final String MAIL_CS_LOGIN_SUCCESS = "MAIL_CS_LOGIN_SUCCESS";

    public static final String MAIL_CS_NEW_DEVICE_LOGIN_VERIFICATION = "MAIL_CS_NEW_DEVICE_LOGIN_VERIFICATION";

    public static final String MAIL_CS_REGISTER_SUCCESS = "MAIL_CS_REGISTER_SUCCESS";

    public static final String MAIL_CS_REGISTER_VERIFICATION = "MAIL_CS_REGISTER_VERIFICATION";

    public static final String MAIL_USERS_OPEN_API_REGISTER_SUCCESS = "MAIL_USERS_OPEN_API_REGISTER_SUCCESS";

    /**
     * 兑换验证码
     */
    public static final String SMS_ASSET_EXCHANGE_CODE = "SMS_ASSET_EXCHANGE_CODE";

    /**
     * 兑换验证码
     */
    public static final String MAIL_ASSET_EXCHANGE_CODE = "MAIL_ASSET_EXCHANGE_CODE";

    /**
     * 兑换通知
     */
    public static final String SMS_ASSET_EXCHANGE_SUCCESS = "SMS_ASSET_EXCHANGE_SUCCESS";

    /**
     * 兑换通知
     */
    public static final String MAIL_ASSET_EXCHANGE_SUCCESS = "MAIL_ASSET_EXCHANGE_SUCCESS";

    public static final String MAIL_CS_CHANGE_EMAIL = "MAIL_CS_CHANGE_EMAIL";

    public static final String MAIL_CS_RESET_GRAPH_EMAIL = "MAIL_CS_RESET_GRAPH_EMAIL";

    public static final String SMS_CS_CHANGE_MOBILE_SEND_NEW = "SMS_CS_CHANGE_MOBILE_SEND_NEW";
    public static final String MAIL_CS_CHANGE_EMAIL_SEND_NEW = "MAIL_CS_CHANGE_EMAIL_SEND_NEW";

    /**
     * 发红包验证码
     */
    public static final String SMS_ASSET_REDPACKET_CODE = "SMS_ASSET_REDPACKET_CODE";

    /**
     * 发红包验证码
     */
    public static final String MAIL_ASSET_REDPACKET_CODE = "MAIL_ASSET_REDPACKET_CODE";

}
