package cc.newex.dax.market.service.impl;

import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.util.StringUtil;
import cc.newex.dax.market.domain.MarketData;
import cc.newex.dax.market.service.RedisService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 生成外部数据指数服务公共父类
 *
 * @author newex-team
 * @date 2018/03/18
 */
public abstract class MarketDataOuterBaseServiceImpl {
    @Autowired
    protected RedisService redisService;

    private static final Map<String, MarketCacheDataArrayTemp> apiCacheArrayMap = new ConcurrentHashMap<>();

    protected List<String[]> getMarketCacheDataArray(final int marketFrom, final int type) {
        final String key = IndexConst.MARKET_DATA_ARRAY + "_" + marketFrom + "_" + type;
        final MarketCacheDataArrayTemp tempOld = MarketDataOuterBaseServiceImpl.apiCacheArrayMap.get(key);

        final List<String[]> list;

        if (tempOld == null || System.currentTimeMillis() - tempOld.getUpdateTime() > 2 * 1000) {
            final MarketCacheDataArrayTemp temp = this.setMarketCacheDataArray(marketFrom, type);
            list = temp.getMarketCacheDate();
            return list;
        }
        list = tempOld.getMarketCacheDate();
        return list;
    }


    protected void setMarketDateUpdate(final int marketFrom, final int type, final int update) {
        final String key = IndexConst.MARKET_DATA_ARRAY_UPDATE;
        this.redisService.setInfo(key, String.valueOf(update));
    }

    public MarketCacheDataArrayTemp setMarketCacheDataArray(final int marketFrom, final int type) {
        final String key = IndexConst.MARKET_DATA_ARRAY + "_" + marketFrom + "_" + type;

        MarketCacheDataArrayTemp temp = MarketDataOuterBaseServiceImpl.apiCacheArrayMap.get(key);
        if (temp == null) {
            temp = new MarketCacheDataArrayTemp();
            temp.setMarketCacheDate(null);
            MarketDataOuterBaseServiceImpl.apiCacheArrayMap.put(key, temp);
        }

        synchronized (temp) {
            final long time = System.currentTimeMillis();
            if (time - temp.getUpdateTime() > 2 * 1000) {
                final String json = this.redisService.getInfo(key);
                if (json == null) {
                    return temp;
                }
                temp.setMarketCacheDate(MarketDataOuterBaseServiceImpl.convertedList(json));
                temp.setUpdateTime(time);
            }
        }
        return temp;
    }


    static List<String[]> convertedList(final String str) {
        List<String[]> list = null;
        if (!StringUtil.isEmpty(str)) {
            list = new CopyOnWriteArrayList<>();
            final String[] obj = str.split(";");
            for (final String string : obj) {
                final String[] arr = string.split((char) 129 + "");
                list.add(arr);
            }
        }
        return list;
    }

    @Data
    class MarketCacheDataArrayTemp {
        private long updateTime;
        private List<String[]> jsonArray;

        List<String[]> getMarketCacheDate() {
            return this.jsonArray;
        }

        void setMarketCacheDate(final List<String[]> jsonArray) {
            this.jsonArray = jsonArray;
        }
    }

}
