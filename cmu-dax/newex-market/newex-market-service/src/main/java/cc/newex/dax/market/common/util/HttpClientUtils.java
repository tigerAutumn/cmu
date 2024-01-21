package cc.newex.dax.market.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Slf4j
public abstract class HttpClientUtils {

    private static final HttpParams params = new BasicHttpParams();

    public HttpClientUtils() {
    }


    public static String get(final String url) {
        final HttpRequestBase request = new HttpGet(url);
        return HttpClientUtils.execute(request, null);
    }

    public static String post(final String url) {
        final HttpRequestBase request = new HttpPost(url);
        return HttpClientUtils.execute(request, null);
    }

    public static String post(final String url, final Map<String, String> params, final Map<String, String> headerMap) {
        return HttpClientUtils.post(url, params, headerMap, "UTF-8");
    }

    public static String post(final String url, final Map<String, String> params, final Map<String, String> headerMap, final String charset) {
        final HttpPost request = new HttpPost(url);
        if (params != null && params.size() > 0) {
            List<NameValuePair> paramList = null;
            final Set<Entry<String, String>> entrySet = params.entrySet();
            paramList = new ArrayList();
            final Iterator it = entrySet.iterator();

            while (it.hasNext()) {
                final Entry<String, String> entry = (Entry) it.next();
                final String key = entry.getKey();
                final String value = entry.getValue();
                if (key != null && value != null) {
                    final NameValuePair nvp = new BasicNameValuePair(key, value);
                    paramList.add(nvp);
                }
            }

            try {
                if (StringUtil.isEmpty(charset)) {
                    request.setEntity(new UrlEncodedFormEntity(paramList));
                } else {
                    request.setEntity(new UrlEncodedFormEntity(paramList, Charset.forName(charset)));
                }
            } catch (final Exception var12) {
                HttpClientUtils.log.error("HttpClientUtils post", var12);
                return null;
            }
        }

        return HttpClientUtils.execute(request, headerMap);
    }

    public static String post(final String url, final HttpEntity entity, final Map<String, String> headerMap) {
        final HttpPost request = new HttpPost(url);
        request.setEntity(entity);
        return HttpClientUtils.execute(request, headerMap);
    }

    public static String get(final String url, final Map<String, String> headerMap) {
        final HttpRequestBase request = new HttpGet(url);
        return HttpClientUtils.execute(request, headerMap);
    }

    public static String post(final String url, final Map<String, String> headerMap) {
        final HttpRequestBase request = new HttpPost(url);
        return HttpClientUtils.execute(request, headerMap);
    }

    private static String execute(final HttpRequestBase request, final Map<String, String> headerMap) {
        return HttpClientUtils.execute(null, request, headerMap, false);
    }

    private static String execute(CloseableHttpClient httpclient, final HttpRequestBase request, final Map<String, String> headerMap, final boolean ignoreStatusCode) {
        final StringBuffer stringBuffer = new StringBuffer("HttpClientUtils execute  method:" + request.getMethod() + " url:" + request.getURI());
        boolean isClose = false;
        if (httpclient == null) {
            httpclient = HttpClients.createDefault();
            isClose = true;
        }

        boolean isLog = false;
        InputStream resStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        String result = "{}";
        if (headerMap != null && headerMap.size() > 0) {
            final Iterator iterator = headerMap.keySet().iterator();

            while (iterator.hasNext()) {
                final String key = (String) iterator.next();
                request.setHeader(key, headerMap.get(key));
            }
        }

        Object entity;
        try {
            final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            request.setConfig(requestConfig);
            final CloseableHttpResponse response = httpclient.execute(request);

            try {
                if (ignoreStatusCode || response.getStatusLine().getStatusCode() == 200) {
                    entity = response.getEntity();
                    if (entity != null) {
                        resStream = ((HttpEntity) entity).getContent();

                        try {
                            try {
                                inputStreamReader = new InputStreamReader(resStream, "UTF-8");
                                br = new BufferedReader(inputStreamReader);
                                final StringBuffer resBuffer = new StringBuffer();
                                String resTemp = "";

                                while ((resTemp = br.readLine()) != null) {
                                    resBuffer.append(resTemp);
                                }

                                result = resBuffer.toString();
                            } catch (final Exception var45) {
                                isLog = true;
                                HttpClientUtils.log.error(stringBuffer.toString(), var45);
                            }

                            return result;
                        } finally {
                            if (br != null) {
                                br.close();
                            }

                            if (inputStreamReader != null) {
                                inputStreamReader.close();
                            }

                            if (resStream != null) {
                                resStream.close();
                            }

                        }
                    }

                    return result;
                }

                request.abort();
                if (isClose) {
                    httpclient.close();
                }

                entity = result;
            } catch (final Exception var47) {
                entity = var47;
                isLog = true;
                HttpClientUtils.log.error(stringBuffer.toString(), var47);
                return result;
            } finally {
                response.close();
            }
        } catch (final Exception var49) {
            request.abort();
            HttpClientUtils.log.error(stringBuffer.toString(), var49);
            isLog = true;
            return result;
        } finally {
            request.abort();
            if (!isClose) {
                return result;
            }

            try {
                httpclient.close();
                if (isLog) {
                    HttpClientUtils.log.error(stringBuffer.toString() + " shutdown ");
                }
            } catch (final IOException var44) {
                HttpClientUtils.log.error(stringBuffer.toString(), var44);
            }

        }

        return (String) entity;
    }


    static {
        HttpProtocolParams.setVersion(HttpClientUtils.params, HttpVersion.HTTP_1_1);
        HttpConnectionParams.setConnectionTimeout(HttpClientUtils.params, 30000);
        HttpClientUtils.log.debug("ThreadSafeClient connectionTimeoutMillis:{30000}");
        HttpConnectionParams.setSoTimeout(HttpClientUtils.params, 30000);
        HttpClientUtils.log.debug("ThreadSafeClient socketTimeoutMillis:{30000}");
        ConnManagerParams.setTimeout(HttpClientUtils.params, 1000L);
        HttpClientUtils.log.debug("ThreadSafeClient connManagerTimeout:{1000}");
    }


}