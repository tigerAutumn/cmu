package cc.newex.maker.perpetual.enums;

/**
 * Created by fang on 2018/5/17.
 */
public enum TradeConfEnum {

    FBTCUSD(0.1D, 10, 1000, 4),;
    /**
     * 下单数量比例
     */
    private final double amountRatio;
    /**
     * 最小量
     */
    private final double min;
    /**
     * 最大量
     */
    private final double max;

    /**
     * 基础货币最小交易小数位
     */
    private final int minDigit;

    TradeConfEnum(final double amountRatio, final double min, final double max, final int minDigit) {
        this.amountRatio = amountRatio;
        this.min = min;
        this.max = max;
        this.minDigit = minDigit;
    }

    public double getAmountRatio() {
        return this.amountRatio;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public int getMinDigit() {
        return this.minDigit;
    }

}
