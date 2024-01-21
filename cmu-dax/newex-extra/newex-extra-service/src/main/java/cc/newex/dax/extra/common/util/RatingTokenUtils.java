package cc.newex.dax.extra.common.util;

import cc.newex.commons.support.model.ResponseResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Objects;

import static cc.newex.dax.extra.common.enums.RatingTokenTemplateEnum.signature;

/**
 * The type Rating token utils.
 *
 * @author better
 * @date create in 2018/11/30 2:27 PM
 */
@Slf4j
public class RatingTokenUtils {

    private static final Integer RESPONSE_SUCCESS_CODE = 0;

    /**
     * Check response and get data json array.
     *
     * @param response the response
     * @return the json array
     */
    public static JSONArray checkResponseAndGetArrayData(final String response) {

        if (StringUtils.isEmpty(response)) {
            log.warn("response is empty");
            return new JSONArray();
        }
        final ResponseResult responseResult = JSON.parseObject(response, ResponseResult.class);
        if (!Objects.equals(RESPONSE_SUCCESS_CODE, responseResult.getCode())) {
            log.warn("response code is error => {}", responseResult.getCode());
            return new JSONArray();
        }
        final JSONObject jsonData = (JSONObject) responseResult.getData();
        return (JSONArray) jsonData.get("list");
    }

    /**
     * Check response and get data json object.
     *
     * @param response the response
     * @return the json object
     */
    public static JSONObject checkResponseAndGetData(final String response) {

        if (StringUtils.isEmpty(response)) {
            log.warn("response is empty");
            return new JSONObject();
        }
        final ResponseResult responseResult = JSON.parseObject(response, ResponseResult.class);
        if (!Objects.equals(RESPONSE_SUCCESS_CODE, responseResult.getCode())) {
            log.warn("response code is error => {}", responseResult.getCode());
            return new JSONObject();
        }
        return (JSONObject) responseResult.getData();
    }

    /**
     * Gets rt http uri request.
     *
     * @param url        the url
     * @param gmtDataStr the gmt data str
     * @return the rt http uri request
     */
    private static HttpUriRequest getRtHttpUriRequest(final String url, final String gmtDataStr) {

        final HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("X-Date", gmtDataStr);
        httpGet.addHeader("Url", url);
        httpGet.addHeader("Authorization", signature(gmtDataStr, url));
        return httpGet;
    }

    /**
     * Execute rt client string.
     *
     * @param httpClient the http client
     * @param url        the url
     * @param gmtDataStr the gmt data str
     * @return the string
     */
    public static String executeRtClient(final CloseableHttpClient httpClient, final String url, final String gmtDataStr) throws IOException {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(RatingTokenUtils.getRtHttpUriRequest(url, gmtDataStr));

            return EntityUtils.toString(response.getEntity());
        } finally {
            if (Objects.nonNull(response)) {
                response.close();
            }
        }
    }
}
