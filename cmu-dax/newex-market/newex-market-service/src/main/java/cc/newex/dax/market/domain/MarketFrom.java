package cc.newex.dax.market.domain;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import cc.newex.dax.market.common.enums.MoneyTypeEnums;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketFrom {
    private String marketFrom;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal last;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal buy;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal sell;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal high;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal low;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal volume;
    private String name;
    private String nativeLast;
    /**
     * sign eg: ฿
     */
    private String sign;
    private String moneyType;
    private String symbol;
    private BigDecimal rate;
    private Date now;

    public static MarketFrom converter(final LatestTicker latestTicker, final Currency currency, final BigDecimal rate, final String nativeCurrency) {

        BigDecimal nativeLast = latestTicker.getLast().setScale(2, BigDecimal.ROUND_HALF_UP);
        if (nativeCurrency != null && latestTicker.getMoneytype() != null) {
            final int moneyType = latestTicker.getMoneytype();
            if (moneyType == MoneyTypeEnums.CNY.getCode()) {
                if (!nativeCurrency.equals("cny")) {
                    nativeLast = latestTicker.getLast().divide(rate, 2, BigDecimal.ROUND_HALF_UP);
                }
            } else if (moneyType == MoneyTypeEnums.USD.getCode()) {
                if (!nativeCurrency.equals("usd")) {
                    nativeLast = latestTicker.getLast().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            }
        }
        return MarketFrom.builder()
                .buy(latestTicker.getBuy())
                .high(latestTicker.getHigh())
                .last(latestTicker.getLast())
                .low(latestTicker.getLow())
                .marketFrom(latestTicker.getMarketFrom() == null ? "" : latestTicker.getMarketFrom().toString())
                .moneyType(latestTicker.getMoneytype() == 1 ? "usd" : "cny")
                .name(latestTicker.getName())
                .sell(latestTicker.getSell())
                .rate(rate)
                .symbol(currency.getSymbol())
                .volume(latestTicker.getVolume())
                .sign(currency.getSign())
                .now(new Date())
                .nativeLast(nativeLast.toString())
                .build();
    }
}
