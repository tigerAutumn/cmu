package cc.newex.dax.perpetual.common.enums;

/**
 * @author newex-team
 * @date 2018/7/11
 */
public enum ConfigNameEnum {
    /**
     * 对账报价阈值
     */
    SYSTEM_BILL_TOTAL_WARNING_JSON("system_bill_total_warning_json"),
    /**
     * 账不平的报警手机号，用逗号分隔
     */
    BILL_WARNING_PHONES("bill_warning_phones"),
    /**
     * K线重新加载开关
     */
    KLINE_RELOAD_SWITCH("kline_reload_switch"),
    /**
     * 连续几次账不平报警
     */
    BILL_WARNING_TIMES("bill_warning_times"),
    /**
     * 结算失败的报警手机号，用逗号分隔
     */
    SETTLEMENT_WARNING_PHONES("settlement_warning_phones"),
    //
    ;

    private final String name;

    ConfigNameEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
