package cc.newex.dax.market.spider.jobs;

import cc.newex.dax.market.spider.common.config.UrlConfig;
import cc.newex.dax.market.spider.common.util.DataKey;
import cc.newex.dax.market.spider.common.util.HttpClientUtils;
import cc.newex.dax.market.spider.common.util.rate.RateUtil;
import cc.newex.dax.market.spider.domain.FetchingDataPath;
import cc.newex.dax.market.spider.domain.Rate;
import cc.newex.dax.market.spider.domain.ServerUrl;
import cc.newex.dax.market.spider.service.DataToRedisService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 汇率
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
@Component
public class RateJobs {
    @Autowired
    private DataToRedisService dataToRedisService;

    @Autowired
    private DataKey dataKey;

    @Autowired
    private ServerUrl url;

    /**
     * 汇率
     */
    @Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(cron = "0/5 * * * * ?")
    public void ratePush() {
        final List<FetchingDataPath> fetchingDataPaths = this.dataToRedisService.getAllPath();

        fetchingDataPaths.stream().filter(path -> path.getWebName().equalsIgnoreCase("rate")).forEach(this::accept);
    }

    private void accept(final FetchingDataPath fetchingDataPath) {
        final String urlTmp = fetchingDataPath.getPath() + "&radom" + System.currentTimeMillis();
        String result = null;
        try {
            final HttpClientUtils.Response response = HttpClientUtils.get(urlTmp, new HashMap<>(16));
            result = response.getBody();
        } catch (final Exception e) {
            log.error("msg:{}", "The exchange rate :  Data fetching failure ", e);
        }
        final String rateParities = RateUtil.getEurCnyRate(result);
        final Rate rate = new Rate();
        rate.setRateName(fetchingDataPath.getUrlKey());
        rate.setRateParities(rateParities);
        this.addRateToDB(UrlConfig.RATE_URL, rate);
    }

    private void addRateToDB(final String path, final Rate rate) {
        final Map<String, Object> map = new HashMap<>(16);
        map.put("rate", JSONObject.toJSONString(rate));
        map.put("key", this.dataKey.getDataKey());
        try {
            final HttpClientUtils.Response post = HttpClientUtils.post(this.url.getServerLocation() + path, map);
            log.info("code:{} ,The exchange rate {}", post.getCode(), post.getBody());
        } catch (final IOException e) {
            log.error("msg : {}  Ticker path【 httpclient connection exception ！！！ 】{} ", this.url.getServerLocation() + path, e.getMessage());
        }
    }
}
