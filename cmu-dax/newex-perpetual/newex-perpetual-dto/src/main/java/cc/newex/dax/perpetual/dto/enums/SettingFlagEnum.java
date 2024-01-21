package cc.newex.dax.perpetual.dto.enums;

/**
 * Created by bs.yang on 2017/10/24.
 * setting_flag字段 枚举类
 */
public enum SettingFlagEnum {
    GOLDEN_ACCOUNT(1,"金账户"),
    REAL_NAME_AUTH_ACCOUNT(2,"实名认证"),
    BORROW_MONEY(3,"是否借款"),
    AUTO_CONVERT(4,"是否自动转款"),
    // 5. 此冻结状态是，原来，代码混合时候，杠杆业务的冻结，非合约业务冻结
    EXPLODE_FREEZE(5,"是否爆仓冻结"),
    WITHOUT_INPUT_PWD(6,"是否开启免输"),
    CONTRACT_STRATEGY_AGREEMENT(7,"是否同意策略交易协议"),
    BORROW_MONEY_AGREEMENT(8,"是否同意借款协议"),
    LOAN_AGREEMENT(9,"是否同意放款协议"),
    OPEN_FUND(10,"是否开放基金"),

    CONTRACT_AUTO_CONVERT_BOND(11,"是否开启合约自动追加保证金"),
    // 12. 爆仓冻结 或 市价全平冻结（新改后将去除）共用一个位置，叫合约冻结，用户冻结状态从用户表下放到资产表，继续使用第12个位置（futures_user_config --> user_futures_balance [setting_flag]）
    CONTRACT_FREEZE(12,"合约冻结"),
    CERTIFICATION_LEVEL2(13,"是否认证等级2"),
    OPEN_CONTRACT(14,"是否开放合约"),
    IP_OFFSITE_LOGIN_WARN(15,"是否关闭ip异地登录提示"),
    PERSONAL_OR_COMPANY_AUTH(16,"个人身份认证还是企业身份认证（0个人，1企业)"),
    CONTRACT_ENTRUST_AGREEMENT(17,"同意合约委托协议"),
    WITHDRAW_DEPOSITE_FREEZE(18,"提现操作冻结"),
    TRADE_UNIT(19,"交易单位"),
    TRADE_CURRENCY(20,"计价货币"),

    CONTRACT_TRACK_ENTRUST_AGREEMENT(21,"同意合约跟踪委托协议"),
    CONTRACT_ICE_ENTRUST_AGREEMENT(22,"同意合约冰山委托协议"),
    PROHIBIT_CHAT(23,"聊天室是否被禁言"),
    CONTRACT_INITIATIVE_ENTRUST_AGREEMENT(24,"同意合约时间加权委托协议"),
    SETTING_CHAT_ADMINISTRATOR(25,"设置聊天管理员"),
    CAN_OPER_AUTH_ADDRESS(26,"是否添加、修改认证地址"),
    WITHDRAW_INTERCEPT(27,"提现拦截"),
    PHONE_BOUND_LOTTERY(28,"手机绑定抽奖"),
    OPEN_LEVER_RATE_FIVE(29,"开启5倍杠杆"),
    DESKTOP_NOTIFY(30,"桌面通知设置"),

    FIRST_FINANCING_CUTTENCY (31,"第一次融资融币"),
    FIRST_CONTRACT_TRADE(32,"第一次期货开仓"),
    VERIFY_EMAIL(33,"是否验证邮箱"),
    EXPERIENCE_USER(34,"是否是体验金用户"),
    OPEN_FINANCING_CUTTENCY_ON_TRADE(35,"是否开启交易时使用融资融币"),
    AUTH_WITH_INTERFACE(36,"认证是否走接口"),
    BOUND_CARD_WITH_INTERFACE(37,"绑卡是否走接口"),
    ;

    private Integer bit;
    private String  descInfo;

    SettingFlagEnum(Integer bit, String descInfo) {
        this.bit = bit;
        this.descInfo = descInfo;
    }

    public Integer getBit() {
        return bit;
    }

    public String getDescInfo() {
        return descInfo;
    }
}
