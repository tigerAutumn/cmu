package cc.newex.dax.market.common.enums;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public enum PublishTypeEnum {
    //指数
    INDEX("index"),
    //行情
    TICKER("ticker"),
    //k线
    CANDLES("candles");
    private String name;

    PublishTypeEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
