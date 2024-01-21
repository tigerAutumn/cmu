package cc.newex.dax.market.spider.common.util;

import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
public class HttpClientUtils {

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();


    /**
     * HTTP Post请求
     *
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     */
    public static Response post(final String url, final Map<String, ? extends Object> paramMap) throws IOException {


        final FormBody.Builder formBuilder = new FormBody.Builder();

        for (final Entry<String, ? extends Object> entry : paramMap.entrySet()) {
            formBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
        }

        final Request request = new Request.Builder().addHeader("Referer", "okex.com").url(url).post(formBuilder.build()).build();

        final okhttp3.Response response = HttpClientUtils.HTTP_CLIENT.newCall(request).execute();

        return new Response(response.code(), response.body().string());
    }

    /**
     * Http Get 请求
     *
     * @param url
     * @param paramMap
     * @return
     * @throws IOException
     */
    public static Response get(final String url, final Map<String, ?> paramMap) throws IOException {
        final HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            throw new RuntimeException("invalid url");
        }

        final HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
        for (final Entry<String, ?> entry : paramMap.entrySet()) {
            httpUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue().toString());
        }

        final Request request = new Request.Builder().url(httpUrlBuilder.build().url()).get().build();

        final okhttp3.Response response = HttpClientUtils.HTTP_CLIENT.newCall(request).execute();

        return new Response(response.code(), response.body().string());
    }

    public static void main(final String[] args) {
        final String url = "https://poloniex.com/public?command=returnTicker";
        try {
            final Response response = HttpClientUtils.get(url, new HashMap<>(16));
            System.out.println(response.getBody());
            System.out.println(HttpClientUtils.getTicker(response.getBody()).toString());
        } catch (final IOException e) {
            HttpClientUtils.log.info("msg{}", e.getMessage());
        }

    }

    private static LatestTicker getTicker(final String json) {

        final JSONObject result = JSON.parseObject(json);

        final JSONObject ticker = result.getJSONObject("BTC_DASH");

        final BigDecimal bid = ticker.getBigDecimal("highestBid");
        final BigDecimal ask = ticker.getBigDecimal("lowestAsk");
        final BigDecimal percentChange = ticker.getBigDecimal("percentChange");
        final BigDecimal volume = ticker.getBigDecimal("quoteVolume");
        final BigDecimal last = ticker.getBigDecimal("last");
        final BigDecimal high = ticker.getBigDecimal("high24hr");
        final BigDecimal low = ticker.getBigDecimal("low24hr");


        final LatestTicker ticker1 = new LatestTicker();

        ticker1.setVolume(volume);
        ticker1.setLast(last);
        ticker1.setSell(ask);
        ticker1.setBuy(bid);
        ticker1.setHigh(high);
        ticker1.setLow(low);
        ticker1.setChange(percentChange);
        return ticker1;
    }

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private int code;
        private String body;
    }

}

