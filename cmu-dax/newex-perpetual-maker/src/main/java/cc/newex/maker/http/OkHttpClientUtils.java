package cc.newex.maker.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author cmx-sdk-team
 * @date 2018-12-26
 */
@Slf4j
public class OkHttpClientUtils {

    public static final long TIME_OUT = 15L;

    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json;charset=utf-8");

    private static final OkHttpClient OK_HTTP_CLIENT = initClient();

    private static OkHttpClient initClient() {
        final ConnectionPool connectionPool = new ConnectionPool(10, 10, TimeUnit.MINUTES);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectionPool(connectionPool)
                .build();

        return okHttpClient;
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param jsonContent
     * @param headerMap
     * @return
     */
    public static String post(final String url, final String jsonContent, final Map<String, String> paramMap, final Map<String, String> headerMap) {
        final RequestBody requestBody = buildRequestBody(jsonContent, paramMap);

        return post(url, requestBody, headerMap);
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param queryParams
     * @param headerMap
     * @return
     */
    public static String post(final String url, final List<ParamPair> queryParams, final Map<String, String> headerMap) {
        final RequestBody requestBody = buildRequestBody(queryParams);

        return post(url, requestBody, headerMap);
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param requestBody
     * @param headerMap
     * @return
     */
    public static String post(final String url, final RequestBody requestBody, final Map<String, String> headerMap) {
        log.debug("url：{}", url);

        final Request.Builder builder = new Request.Builder();
        builder.url(url);

        if (MapUtils.isNotEmpty(headerMap)) {
            headerMap.forEach((k, v) -> builder.header(k, v));
        }

        builder.post(requestBody);

        final Request request = builder.build();

        try (final Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            return handleResponse(url, response);
        } catch (final Exception e) {
            log.error("post API exception：" + url, e);
        }

        return null;
    }

    /**
     * 发送get请求
     *
     * @param url
     * @param headerMap
     * @return
     */
    public static String get(final String url, final Map<String, String> headerMap) {
        return get(url, "", headerMap);
    }

    /**
     * 发送get请求
     *
     * @param url
     * @param paramMap
     * @param headerMap
     * @return
     */
    public static String get(final String url, final Map<String, String> paramMap, final Map<String, String> headerMap) {
        final String queryString = buildQueryString(paramMap);

        return get(url, queryString, headerMap);
    }

    public static String get(final String url, final List<ParamPair> queryParams, final Map<String, String> headerMap) {
        final String queryString = buildQueryString(queryParams);

        return get(url, queryString, headerMap);
    }

    public static String get(final String url, final String queryString, final Map<String, String> headerMap) {
        log.debug("url：{}", url);

        final Request.Builder builder = new Request.Builder();

        if (StringUtils.isNotBlank(queryString)) {
            builder.url(url + "?" + queryString);
        } else {
            builder.url(url);
        }

        if (MapUtils.isNotEmpty(headerMap)) {
            headerMap.forEach((k, v) -> builder.header(k, v));
        }

        final Request request = builder.build();

        try (final Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            return handleResponse(url, response);
        } catch (final Exception e) {
            log.error("get API exception：" + url, e);
        }

        return null;
    }

    private static String handleResponse(final String url, final Response response) throws IOException {
        final int code = response.code();

        final String responseStr = response.body().string();

        if (!response.isSuccessful()) {
            log.info("url: {}, code: {}, response: {}", url, code, responseStr);
        }

        return responseStr;
    }

    public static RequestBody buildRequestBody(final String jsonContent, final Map<String, String> paramMap) {
        RequestBody requestBody = null;

        if (MapUtils.isNotEmpty(paramMap)) {
            final FormBody.Builder formBuilder = new FormBody.Builder();

            paramMap.forEach((k, v) -> formBuilder.add(k, v));

            requestBody = formBuilder.build();
        } else {
            requestBody = RequestBody.create(MEDIA_TYPE, jsonContent);
        }

        return requestBody;
    }

    public static RequestBody buildRequestBody(final List<ParamPair> queryParams) {
        final FormBody.Builder formBuilder = new FormBody.Builder();

        queryParams.forEach(paramPair -> formBuilder.add(paramPair.getName(), paramPair.getValue()));

        final RequestBody requestBody = formBuilder.build();

        return requestBody;
    }

    public static String buildQueryString(final Map<String, String> queryParams) {
        final StringBuilder queryString = new StringBuilder();

        if (MapUtils.isNotEmpty(queryParams)) {
            queryParams.forEach((k, v) -> queryString.append(escapeString(k)).append("=").append(escapeString(v)).append("&"));

            queryString.setLength(queryString.length() - 1);
        }

        return queryString.toString();
    }

    public static String buildQueryString(final List<ParamPair> queryParams) {
        final StringBuilder queryString = new StringBuilder();

        if (CollectionUtils.isNotEmpty(queryParams)) {
            queryParams.forEach(param -> queryString.append(escapeString(param.getName())).append("=").append(escapeString(param.getValue())).append("&"));

            queryString.setLength(queryString.length() - 1);
        }

        return queryString.toString();
    }

    /**
     * Build full API_BASE_URL by concatenating base path, the given sub path and query parameters.
     *
     * @param path        The sub path
     * @param queryParams The query parameters
     * @return The full API_BASE_URL
     */
    public static String buildUrl(final String path, final List<ParamPair> queryParams) {
        final StringBuilder url = new StringBuilder();
        url.append(path);

        if (CollectionUtils.isNotEmpty(queryParams)) {
            // support (constant) query string in `path`, e.g. "/posts?draft=1"
            final String prefix = path.contains("?") ? "&" : "?";

            final String queryString = buildQueryString(queryParams);

            url.append(prefix).append(queryString);
        }

        return url.toString();
    }

    /**
     * Escape the given string to be used as API_BASE_URL query value.
     *
     * @param str String to be escaped
     * @return Escaped string
     */
    private static String escapeString(final String str) {
        try {
            return URLEncoder.encode(str, "utf8").replaceAll("\\+", "%20");
        } catch (final UnsupportedEncodingException e) {
            return str;
        }
    }

}
