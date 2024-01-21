package cc.newex.dax.market.spider.service.impl;

import cc.newex.dax.market.spider.common.config.UrlConfig;
import cc.newex.dax.market.spider.common.util.DataKey;
import cc.newex.dax.market.spider.common.util.HttpClientUtils;
import cc.newex.dax.market.spider.domain.FetchingDataPath;
import cc.newex.dax.market.spider.domain.ServerUrl;
import cc.newex.dax.market.spider.service.DataToRedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
@Service
public class DataToRedisServiceImpl implements DataToRedisService {

    /**
     * data path cache
     */
    private static List<FetchingDataPath> fetchingDataPathCacheList;

    @Autowired
    private ServerUrl serverUrl;

    @Autowired
    private DataKey dataKey;

    @Override
    public void pathToRedis() {
        final String url = UrlConfig.PATH_REDIS_URL;
        this.dataToRedis(url);

    }

    @Override
    public void tickerToRedis() {
        final String url = UrlConfig.TICKER_REDIS_URL;
        this.dataToRedis(url);

    }

    @Override
    public void rateToRedis() {
        final String url = UrlConfig.RATE_RESDIS_URL;
        this.dataToRedis(url);

    }

    @Override
    public void orePoolToRedis() {
        final String url = UrlConfig.POOL_REDIS_URL;
        this.dataToRedis(url);

    }

    private void dataToRedis(final String url) {
        final Map<String, Object> map = new HashMap<>(16);
        map.put("key", this.dataKey.getDataKey());
        try {
            HttpClientUtils.get(this.serverUrl.getServerLocation() + url, map);
        } catch (final IOException e) {
            log.error("url : {}", url, e);
        }
    }

    @Override
    public List<FetchingDataPath> getAllPath() {
        if (CollectionUtils.isNotEmpty(fetchingDataPathCacheList)) {
            return fetchingDataPathCacheList;
        }

        List<FetchingDataPath> fetchingDataPaths = null;

        final Map<String, Object> map = new HashMap<>(16);
        final String dataKey = this.dataKey.getDataKey();
        map.put("key", dataKey);

        try {
            final HttpClientUtils.Response response = HttpClientUtils.get(this.serverUrl.getServerLocation() + UrlConfig.ALL_PATH_URL,
                    map);

            if (response.getCode() == 200) {
                final JSONObject jsonObject = JSON.parseObject(response.getBody());
                fetchingDataPaths = JSONArray.parseArray(jsonObject.getString("data"), FetchingDataPath.class);
            } else {
                log.error("getAllPath error, code: {}, body: {}", response.getCode(), response.getBody());
            }

            fetchingDataPathCacheList = fetchingDataPaths;
        } catch (final Exception e) {
            log.error("getAllPath error, msg: {}", e.getMessage(), e);
        }

        return Optional.ofNullable(fetchingDataPaths).orElse(Collections.emptyList());
    }
}
