package cc.newex.dax.integration.service.msg.provider.sms.emay.util.http;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * EMAY http客户端
 *
 * @author Frank
 */
@Slf4j
public class EmayHttpClient {

    private static final String POST_METHOD = "POST";
    /**
     * 链接超时时间(s)
     */
    private int httpConnectionTimeOut = 5;
    /**
     * 数据传输超时时间(s)
     */
    private int httpReadTimeOut = 5;

    public EmayHttpClient() {

    }

    /**
     * @param httpConnectionTimeOut 链接超时时间(s)
     * @param httpReadTimeOut       数据传输超时时间(s)
     */
    public EmayHttpClient(final int httpConnectionTimeOut, final int httpReadTimeOut) {
        this.httpConnectionTimeOut = httpConnectionTimeOut;
        this.httpReadTimeOut = httpReadTimeOut;
    }

    /**
     * 发送HTTP请求
     *
     * @param request 请求
     * @param praser  响应解析器
     * @return T 响应
     */
    public <T> T service(final EmayHttpRequest<?> request, final EmayHttpResponsePraser<T> praser) {
        EmayHttpResultCode code = EmayHttpResultCode.SUCCESS;
        if (request.getUrl() == null || request.getUrl().length() == 0) {
            code = EmayHttpResultCode.ERROR_URL_NULL;
            return praser.prase(request.getCharSet(), code, 0, null, null, null);
        }
        HttpURLConnection conn = null;
        int httpCode = 0;
        Map<String, String> headers = null;
        List<String> cookies = null;
        ByteArrayOutputStream outputStream = null;
        try {
            conn = this.createConnection(request);
            this.fillConnection(conn, request);
            this.request(conn, request);
            httpCode = conn.getResponseCode();
            headers = this.getHeaders(conn, request.getCharSet());
            cookies = this.getCookies(conn, request.getCharSet());
            outputStream = this.getResultOutputStream(conn);
        } catch (final KeyManagementException | NoSuchAlgorithmException e) {
            code = EmayHttpResultCode.ERROR_HTTPS_SSL;
            log.error("", e);
        } catch (final ProtocolException e) {
            code = EmayHttpResultCode.ERROR_METHOD;
            log.error("", e);
        } catch (final UnsupportedEncodingException e) {
            code = EmayHttpResultCode.ERROR_CHARSET;
            log.error("", e);
        } catch (final MalformedURLException e) {
            code = EmayHttpResultCode.ERROR_URL;
            httpCode = 500;
            log.error("", e);
        } catch (final IOException e) {
            code = EmayHttpResultCode.ERROR_CONNECT;
            log.error("", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        T t = null;
        try {
            t = praser.prase(request.getCharSet(), code, httpCode, headers, cookies, outputStream);
        } catch (final Exception e) {
            log.error("", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (final IOException e) {
                    log.error("", e);
                }
            }
        }
        return t;
    }

    /**
     * 获取HTTP响应头
     *
     * @param conn
     * @param charSet
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map<String, String> getHeaders(final HttpURLConnection conn, final String charSet) throws UnsupportedEncodingException {
        final Map<String, String> resultHeaders = new HashMap<>();
        final Map<String, List<String>> header = conn.getHeaderFields();
        if (header != null && header.size() > 0) {
            for (final Entry<String, List<String>> entry : header.entrySet()) {
                if (!"Set-Cookie".equalsIgnoreCase(entry.getKey())) {
                    String valuer = "";
                    if (entry.getValue() != null && entry.getValue().size() > 0) {
                        for (final String value : entry.getValue()) {
                            valuer += new String(value.getBytes("ISO-8859-1"), charSet) + ",";
                        }
                        valuer = valuer.substring(0, valuer.length() - 1);
                    }
                    resultHeaders.put(entry.getKey(), valuer);
                }
            }
        }
        return resultHeaders;
    }

    /**
     * 获取HTTP响应Cookies
     *
     * @param conn
     * @param charSet
     * @return
     * @throws UnsupportedEncodingException
     */
    private List<String> getCookies(final HttpURLConnection conn, final String charSet) throws UnsupportedEncodingException {
        final List<String> resultC = new ArrayList<>();
        List<String> cookies = null;
        final Map<String, List<String>> header = conn.getHeaderFields();
        if (header != null && header.size() > 0) {
            cookies = header.get("Set-Cookie");
        }
        if (cookies != null) {
            for (final String cookie : cookies) {
                resultC.add(new String(cookie.getBytes("ISO-8859-1"), charSet));
            }
        }
        return cookies;
    }

    /**
     * 获取HTTP响应数据流
     *
     * @param conn
     * @return
     * @throws IOException
     */
    private ByteArrayOutputStream getResultOutputStream(final HttpURLConnection conn) throws IOException {
        final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try (final InputStream is = conn.getInputStream()) {
            if (is != null) {
                final byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
            }
        } catch (final IOException e) {
            throw e;
        }
        return outStream;
    }

    /**
     * 发送Http请求
     *
     * @param conn
     * @param request
     * @throws IOException
     */
    private void request(final HttpURLConnection conn, final EmayHttpRequest<?> request) throws IOException {
        if (request.getMethod().equalsIgnoreCase(POST_METHOD)) {
            conn.setDoOutput(true);
            if (request.getParams() != null) {
                final DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(request.paramsToBytesForPost());
                out.flush();
                out.close();
            }
        } else {
            conn.connect();
        }
    }

    /**
     * 添加请求信息
     *
     * @param conn
     * @param request
     * @throws ProtocolException
     */
    private void fillConnection(final HttpURLConnection conn, final EmayHttpRequest<?> request) throws ProtocolException {
        this.fillTimeout(conn);
        this.filleMethod(conn, request);
        this.fillHeaders(conn, request);
        this.fillCookies(conn, request);
    }

    /**
     * 添加超时时间
     *
     * @param conn
     */
    private void fillTimeout(final HttpURLConnection conn) {
        if (this.httpConnectionTimeOut != 0) {
            conn.setConnectTimeout(this.httpConnectionTimeOut * 1000);
        }
        if (this.httpReadTimeOut != 0) {
            conn.setReadTimeout(this.httpReadTimeOut * 1000);
        }
    }

    /**
     * 指定HTTP方法
     *
     * @param conn
     * @param request
     * @throws ProtocolException
     */
    private void filleMethod(final HttpURLConnection conn, final EmayHttpRequest<?> request) throws ProtocolException {
        conn.setRequestMethod(request.getMethod().toUpperCase());
    }

    /**
     * 添加头信息
     *
     * @param conn
     * @param request
     */
    private void fillHeaders(final HttpURLConnection conn, final EmayHttpRequest<?> request) {
        if (request.getHeaders() != null) {
            for (final Entry<String, String> entry : request.getHeaders().entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 添加Cookies
     *
     * @param conn
     * @param request
     */
    private void fillCookies(final HttpURLConnection conn, final EmayHttpRequest<?> request) {
        if (request.getCookies() != null) {
            conn.setRequestProperty("Cookie", request.getCookies());
        }
    }

    /**
     * 创建Http链接
     *
     * @param request
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws MalformedURLException
     * @throws IOException
     */
    private HttpURLConnection createConnection(final EmayHttpRequest<?> request) throws NoSuchAlgorithmException, KeyManagementException, MalformedURLException, IOException {
        final URL console = new URL(request.getUrl());
        final HttpURLConnection conn;
        if (request.isHttps()) {
            final SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new X509TrustManager() {

                @Override
                public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }

            }}, new java.security.SecureRandom());
            final HttpsURLConnection sconn = (HttpsURLConnection) console.openConnection();
            sconn.setSSLSocketFactory(sc.getSocketFactory());
            sconn.setHostnameVerifier((hostname, session) -> true);
            conn = sconn;
        } else {
            conn = (HttpURLConnection) console.openConnection();
        }
        return conn;
    }

}
