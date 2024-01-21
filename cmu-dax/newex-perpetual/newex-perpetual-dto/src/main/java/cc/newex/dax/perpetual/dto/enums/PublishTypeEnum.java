package cc.newex.dax.perpetual.dto.enums;

/**
 * Created by wj on 2018/9/26.
 */
public enum PublishTypeEnum {
    /**
     * 深度
     */
    DEPTH("DEPTH"),
    /**
     * 资产
     */
    BALANCE("BALANCE"),
    /**
     * 订单
     */
    ORDER("ORDER"),
    /**
     * 行情
     */
    TICKER("TICKER"),
    /**
     * 交割类型变化
     */
    DELIVERY("DELIVERY"),
    /**
     * 预估交割价
     */
    FORECAST("FORECAST"),
    /**
     * 账号信息
     */
    ACCOUNT("ACCOUNT"),
    /**
     * 持仓信息
     */
    POSITION("POSITION"),
    /**
     * 限价
     */
    PRICE_RANGE("PRICE_RANGE"),
    /**
     *
     */
    FILLS("FILLS"),
    /**
     * K线
     */
    CANDLES("CANDLES"),
    /**
     * 合约信息
     */
    CONTRACT_MESSAGE("CONTRACT_MESSAGE");
    private String name;

    PublishTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
