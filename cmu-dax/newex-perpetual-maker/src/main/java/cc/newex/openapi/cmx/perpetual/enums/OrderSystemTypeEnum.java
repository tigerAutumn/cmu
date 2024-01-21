package cc.newex.openapi.cmx.perpetual.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderSystemTypeEnum {
    LIMIT(10), // 限价
    MARKET(11), // 市价
    FORCED_LIQUIDATION(13), // 强平
    EXPLOSION(14), // 爆仓
    CONTRA_TRADE(15), // 对敲
    ;

    private final int systemType;

    public int getSystemType() {
        return this.systemType;
    }
}
