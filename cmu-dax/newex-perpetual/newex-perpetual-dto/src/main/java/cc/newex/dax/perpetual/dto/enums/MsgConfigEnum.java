package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/5/30 17:03
 * @Description:
 */
public enum MsgConfigEnum {

    MSG_FUTURE_CLOSE_FREEZED_MONITOR("持仓数量、可平仓数量异常", 47, 0, "警告：用户持仓、持仓冻结数量出现异常 userid：${freezedUserId}"),
    MSG_FUTURE_POSITION_ERROR_MONITOR("多仓、空仓总数不相同", 48, 0, "警告：合约持仓中多仓数量 != 空仓数量  contractid：${pid}"),
    MSG_FUTURE_ORDER_AMOUNT_ERROR_MONITOR("合约挂单中存在负数", 49, 0, "警告：合约挂单中出现错误数量挂单  marketFrom：${errorMarket}"),
    MSG_FUTURE_ACCOUNT_ERROR_MONITOR("对账数据异常", 50, 0, "future check 币种: ${symbolStr},合约内部对账出现不平,合约id: ${errorContract},差值: ${diffVal}"),
    MSG_FUTURE_ACCOUNT_MONITOR("对账异常", 51, 0, "future check 币种"),
    //预爆仓短信
    TYPE_FUTURE_LIQUIDATION_WARN_DEPOSIT_ZH("合约爆仓提醒", 54, 0, "尊敬的${userName}用户，您的${symbolDesc}虚拟仓位保证金率已经达到30%，请注意仓位控制以免发生爆仓。"),
    TYPE_FUTURE_LIQUIDATION_WARN_DEPOSIT_EN("合约爆仓提醒", 54, 1, "Dear ${userName}, Your margin balance ${symbolDesc} has reached 30%."),
    TYPE_FUTURE_LIQUIDATION_WARN_LOSS_ZH("合约爆仓提醒", 85, 0, "尊敬的${userName}用户，您的${contractName}${direct}${leverRate}倍杠杆仓位合约盈亏比已经达到-70%，请注意仓位控制以免发生爆仓。"),
    TYPE_FUTURE_LIQUIDATION_WARN_LOSS_EN("合约爆仓提醒", 85, 1, "${userName}, the UPL for your ${contractName} ${direct} position at ${leverRate} leverage has reached -70%. Take due care to avoid liquidation."),
    //与用户中心商定: 300-399号段合约专用  20171213
    TYPE_FUTURE_EMAIL_SEND_FAIL_WARN_ZH("邮件发送失败", 301, 0, "用户ID${userId}邮件发送失败"),
    PLAN_ENTRUST_NOTIFY_WITH_SMS_ZH("计划委托短信通知", 302, 0, "尊敬的${userName}用户:您的${contractName}计划委托单已于${date}在价格为${price}时被触发。"),
    PLAN_ENTRUST_NOTIFY_WITH_SMS_EN("计划委托短信通知", 302, 1, "Hi ${userName},Your ${contractName} trigger order has been triggered at ${price} at ${date}."),

    //对账报警流水使用
    USER_TANSFER_IN_AND_OUT_SUB_USER_BALANCE("转入转出减用户总余额", 303, 0, ""),
    ACCOUNT_RIGHTS_LESS_THAN_ZERO("合约用户账户权益小于零", 304, 0, ""),
    TYPE_FUTURE_K_LINE_FAIL_WARN_ZH("一分钟K线失败", 305, 0, "一分钟k线${marketFrom}超过5分钟未生成"),
    EXPLODE_NOW_CONTRACT_FREEZE_ERROR("合约爆仓逻辑: 持仓冻结数量大于零", 306, 0, "合约爆仓逻辑: 持仓冻结数量大于零, 明细: ${detailMsg}"),

    //爆仓短信  LOSS逐仓  DEPOSIT全仓  [合约预留 83 84 85]
    TYPE_FUTURE_LIQUIDATION_DONE_DEPOSIT_ZH("合约已爆仓", 83, 0, "尊敬的用户，因行情剧烈波动，您的${symbolDesc}虚拟仓位已爆仓。"),
    TYPE_FUTURE_LIQUIDATION_DONE_DEPOSIT_EN("合约已爆仓", 83, 1, "Dear User, your virtual position ${symbolDesc} has been forced to liquidate due to the market volatility."),
    TYPE_FUTURE_LIQUIDATION_DONE_LOSS_ZH("合约已爆仓", 84, 0, "尊敬的用户，因行情剧烈波动,您的${contractName}${direct}${leverRate}倍杠杆仓位已爆仓。"),
    TYPE_FUTURE_LIQUIDATION_DONE_LOSS_EN("合约已爆仓", 84, 1, "Dear User, your ${contractName}${direct}${leverRate} position has been forced to liquidate due to the market volatility."),

    //计划委托
    MSG_FUTURE_PLAN_ENTRUST_ZH("CoinMex 计划委托提醒邮件", 307, 0, "尊敬的${userName}用户: 您的${contractName}计划委托单已于${date}在价格为${price}时被触发。"),
    MSG_FUTURE_PLAN_ENTRUST_EN("Trigger Order Notification", 307, 1, "Hi ${userName}, Your ${contractName} trigger order has been triggered at ${price} at ${date}."),
    ;

    private final String descInfo;
    private final Integer typeCode;
    //0:中文 1:英文
    private final Integer languageType;
    private final String templateStr;

    public String getDescInfo() {
        return this.descInfo;
    }

    public Integer getTypeCode() {
        return this.typeCode;
    }

    public Integer getLanguageType() {
        return this.languageType;
    }

    public String getTemplateStr() {
        return this.templateStr;
    }

    MsgConfigEnum(final String descInfo, final Integer typeCode, final Integer languageType, final String templateStr) {
        this.descInfo = descInfo;
        this.typeCode = typeCode;
        this.languageType = languageType;
        this.templateStr = templateStr;
    }

    public static MsgConfigEnum getByTypeAndLanguage(final Integer typeCode, final Integer languageType) {
        for (final MsgConfigEnum futuresMsgConfigEnum : MsgConfigEnum.values()) {
            if (futuresMsgConfigEnum.getTypeCode() == typeCode && futuresMsgConfigEnum.getLanguageType() ==
                    languageType) {
                return futuresMsgConfigEnum;
            }
        }
        return null;
    }

}
