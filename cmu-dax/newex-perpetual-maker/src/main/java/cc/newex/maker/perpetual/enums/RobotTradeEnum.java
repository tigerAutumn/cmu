package cc.newex.maker.perpetual.enums;

public enum RobotTradeEnum {

    FBTCUSD(10, 1000, 100, 500, 8, 30, 90, 4, AccountEnum.CMX_TRADE_1),;

    private int min;
    private int max;

    private int randomMin;
    private int randomMax;

    private int priceRate;

    private int minSleepTime;
    private int maxSleepTime;

    /**
     * 交易价格小数位数
     */
    private int priceDigit;

    private AccountEnum accountEnum;

    RobotTradeEnum(final int min, final int max, final int randomMin, final int randomMax, final int priceRate, final int minSleepTime, final int maxSleepTime, final int priceDigit, final AccountEnum accountEnum) {
        this.min = min;
        this.max = max;
        this.randomMin = randomMin;
        this.randomMax = randomMax;
        this.priceRate = priceRate;
        this.minSleepTime = minSleepTime;
        this.maxSleepTime = maxSleepTime;
        this.priceDigit = priceDigit;
        this.accountEnum = accountEnum;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public int getRandomMin() {
        return this.randomMin;
    }

    public int getRandomMax() {
        return this.randomMax;
    }

    public int getPriceRate() {
        return this.priceRate;
    }

    public int getMinSleepTime() {
        return this.minSleepTime;
    }

    public int getMaxSleepTime() {
        return this.maxSleepTime;
    }

    public int getPriceDigit() {
        return this.priceDigit;
    }

    public AccountEnum getAccountEnum() {
        return this.accountEnum;
    }
}
