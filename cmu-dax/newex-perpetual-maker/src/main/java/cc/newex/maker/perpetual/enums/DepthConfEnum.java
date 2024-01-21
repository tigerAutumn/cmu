package cc.newex.maker.perpetual.enums;

/**
 * 深度枚举
 *
 * @author newex-team
 * @date 2018-12-17
 */
public enum DepthConfEnum {

    FBTCUSD(0.0001D, 1000D, 1000D, 30, 2, 0.00005D, 10),;

    /**
     * 价格调整比例
     */
    private final double adjustRatio;
    /**
     * 卖深度限额
     */
    private final double askQuota;
    /**
     * 买深度限额
     */
    private final double bidQuota;
    /**
     * 深度数量
     */
    private final int cnt;
    /**
     * 放大倍数
     */
    private final int multiple;

    /**
     * 盘口偏离外部范围
     */
    private final double outRisk;

    /**
     * 最小交易量
     */
    private double minAmount;

    DepthConfEnum(final double adjustRatio, final double askQuota, final double bidQuota, final int cnt, final int multiple, final double outRisk, final double minAmount) {
        this.adjustRatio = adjustRatio;
        this.askQuota = askQuota;
        this.bidQuota = bidQuota;
        this.cnt = cnt;
        this.multiple = multiple;
        this.outRisk = outRisk;
        this.minAmount = minAmount;
    }

    public double getAdjustRatio() {
        return this.adjustRatio;
    }

    public double getAskQuota() {
        return this.askQuota;
    }

    public double getBidQuota() {
        return this.bidQuota;
    }

    public int getCnt() {
        return this.cnt;
    }

    public int getMultiple() {
        return this.multiple;
    }

    public double getOutRisk() {
        return this.outRisk;
    }

    public double getMinAmount() {
        return this.minAmount;
    }
}
