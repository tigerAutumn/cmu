package cc.newex.dax.market.domain;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import cc.newex.commons.lang.json.BigDecimalSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LatestTicker {

    /**
     * 最新报价
     */
    private Long id;
    /**
     * 最高成交价
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal high = BigDecimal.ZERO;
    /**
     * 最低成交价
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal low = BigDecimal.ZERO;
    /**
     * 当日最高成交价
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal dayHigh = BigDecimal.ZERO;
    /**
     * 当日最低成交价
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal dayLow = BigDecimal.ZERO;
    /**
     * btc成交量
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal volume = BigDecimal.ZERO;
    /**
     * 22小时成交量
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal volume22 = BigDecimal.ZERO;
    /**
     * 24小时涨跌幅
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal change24 = BigDecimal.ZERO;
    /**
     * 成交单价
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal last = BigDecimal.ZERO;
    /**
     * 最新买入价
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal buy = BigDecimal.ZERO;
    /**
     * 最新卖出价
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal sell = BigDecimal.ZERO;
    /**
     * 24小时 open
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal open = BigDecimal.ZERO;
    /**
     * 24小时 close
     */
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private BigDecimal close = BigDecimal.ZERO;
    /**
     * 0:okcoin1:mtgox2:btcchina3:okcoinLtc4:bitstamp5:btceLtc6:btcchinaWeixin7:btceBtc
     */
    private Integer marketFrom;
    /**
     * 买参考偏移量
     */
    private Double offsetBuy;
    /**
     * 卖参考偏移量
     */
    private Double offsetSell;
    /**
     * 模式0:自动1:手动
     */
    private Byte auto;
    /**
     * 是否立即执行0:否1:是
     */
    private Byte delay;
    /**
     * 排序
     */
    private Byte orderIndex;
    /**
     *
     */
    private String name;

    /**
     * 0:btc 1:ltc
     */
    private Integer baseCurrency;

    private Integer quoteCurrency;
    /**
     * 0:cny 1:usd
     */
    private Byte moneytype;
    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * BTC/USD
     */
    private String symbol;

    private Byte valid;

    /**
     * 从JSON格式的字符串中解析对象。
     *
     * @param str
     * @return
     */
    public static LatestTicker parseFromJSON(final String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        try {
            return JSONObject.parseObject(str, LatestTicker.class);
        } catch (final Exception e) {
            log.error("LatestTicker parseFromJSON fail!", e);
        }
        return null;
    }

    /**
     * 从JSON格式的字符串中解析对象列表。
     *
     * @param str
     * @return
     */
    public static List<LatestTicker> parseListFromJSON(final String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }

        try {
            return JSONObject.parseArray(str, LatestTicker.class);
        } catch (final Exception e) {
            log.error("LatestTicker parseListFromJSON fail!", e);
        }
        return null;
    }


}