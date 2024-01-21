package cc.newex.dax.market.spider.common.util.rate;

import cc.newex.dax.market.spider.common.config.UrlConfig;
import cc.newex.dax.market.spider.common.util.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 汇率
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
public class RateUtil {

    /**
     * 获取汇率
     */
    public static String getEurCnyRate(String result) {
        result = result.replace(" ", "");
        if (result.contains("number2")) {
            int number2 = result.indexOf("number2");
            number2 += 10;
            int end = result.indexOf("content1Mini");
            end -= 3;
            final String rateStr = result.substring(number2, end);
            return rateStr;
        }
        return null;
    }

    public static BigDecimal getRateData(final String name) {
        final Map<String, String> param = new HashMap<>();
        param.put("rateName", name);
        try {
            final HttpClientUtils.Response response = HttpClientUtils.get(UrlConfig.GET_RATE_URL, param);
            if (response.getCode() == 200) {
                final String body = response.getBody();
                final JSONObject jsonObject = JSONObject.parseObject(body);
                final JSONObject data = jsonObject.getJSONObject("data");
                return data.getBigDecimal("rateParities");

            }
        } catch (final Exception e) {
            log.error("msg: rate 获取失败，info {}", e.getMessage());
        }

        return null;
    }

}
