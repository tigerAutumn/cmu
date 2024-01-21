package cc.newex.dax.market.common.consts;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface IndexConst {

    /**
     * 更新100条指数数据至缓存
     */
    String KEY_INDEX = "%sindex";
    /**
     * 最新lastIndex指数数据
     */
    String FUTURE_KEY_LAST_INDEX = "future%slastindex";

    String KEY_SUB_INDEX = "sub_%s_index";

    /**
     * 指数订阅channel
     */
    String INDEX_CHANNEL = "INDEXES_";

    /**
     * 指数显示
     */
    String KEY_LAST_INDEX_CALCULATION = "lastindexcalculation_";

    String KEY_OPEN_PRICE_BEFORE_24HOUR_CACHE = "open_price_before_24hour_%s";

    String MARKET_DATA_ARRAY = "marketdataarraycache";

    String KEY_INDEX_TICKER = "index_ticker_%s";

    String MARKET_DATA_ARRAY_UPDATE = "marketdataarraycacheupdate";

    String MARKET_DATA_JSON = "marketdatajsoncache";

    /**
     * 汇率 redis key
     */
    String MARKET_RATE_LIST = "MARKET_RATE_LIST";
    /**
     * 价格异常 高
     */
    double index_maxPercent = 1.10;
    /**
     * 价格异常 低
     */
    double index_minPercent = 0.90;

    /**
     * 指数阈值 最大无效条数
     */
    double INVALID_RATE_MAX = 0.9;


    String LASTTWOWEEKRATE = "LASTTWOWEEKRATE";


    /**
     * 短信发送区域
     */
    String SMS_SEND_AREA = "86";

    BigDecimal AVAILABLE_PERPETUAL_MARKET_RATE = new BigDecimal("0.02");
}
