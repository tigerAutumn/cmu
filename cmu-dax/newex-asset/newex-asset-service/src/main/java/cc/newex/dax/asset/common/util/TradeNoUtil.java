package cc.newex.dax.asset.common.util;

import java.util.UUID;

public class TradeNoUtil {
    public static String getTradeNo() {
        return UUID.randomUUID().toString() + System.currentTimeMillis();
    }
}
