package cc.newex.dax.extra.dto.currency;

import java.math.BigDecimal;
import java.util.List;

/**
 * 币种的交易信息
 *
 * @author liutiejun
 * @date 2018-08-21
 */
public class CurrencyTradeInfoDTO {

    /**
     * 币种唯一代码（币种简称）
     */
    private String code;

    /**
     * 24小时成交量
     */
    private String tradeVolume;

    /**
     * 最新价格
     */
    private BigDecimal price;

    /**
     * 交易币对
     */
    private List<String> pairs;

}
