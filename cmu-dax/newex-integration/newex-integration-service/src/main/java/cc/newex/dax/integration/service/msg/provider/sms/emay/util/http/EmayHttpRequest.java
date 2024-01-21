package cc.newex.dax.integration.service.msg.provider.sms.emay.util.http;

import java.util.Map;

/**
 * Http请求实体
 *
 * @param <T> 传输数据类型
 * @author Frank
 */
public abstract class EmayHttpRequest<T extends Object> {

    private String url;// URL
    private String charSet = "UTF-8";// 编码
    private String method = "GET";// Http方法
    private Map<String, String> headers;// 头信息
    private String cookies;// cookie信息
    private T params;// 传输数据

    /**
     * @param url     URL
     * @param charSet 编码
     * @param method  Http方法
     * @param headers 头信息
     * @param cookies cookie信息
     * @param params  传输数据
     */
    public EmayHttpRequest(final String url, final String charSet, final String method, final Map<String, String> headers, final String cookies, final T params) {
        if (url != null) {
            this.url = url.trim();
        }
        if (charSet != null) {
            this.charSet = charSet;
        }
        if (method != null) {
            this.method = method;
        }
        this.headers = headers;
        this.cookies = cookies;
        this.params = params;
        this.fillGetUrl();
    }

    /**
     * 将传输数据转换为byte[]类型
     *
     * @return
     */
    public abstract byte[] paramsToBytesForPost();

    /**
     * 将传输数据转换为String类型
     *
     * @return
     */
    public abstract String paramsToStringForGet();

    /**
     * 是否https请求
     *
     * @return
     */
    public boolean isHttps() {
        if (this.url == null) {
            return false;
        }
        if (this.url.startsWith("https")) {
            return true;
        }
        return false;
    }

    /**
     * 构建GET URL
     */
    private void fillGetUrl() {
        if (this.url == null || this.params == null) {
            return;
        }
        if (this.getMethod().equalsIgnoreCase("GET")) {
            final String getprams = this.paramsToStringForGet();
            if (this.url.indexOf("?") > 0) {
                this.url = this.url + "&" + getprams;
            } else {
                this.url = this.url + "?" + getprams;
            }
        }
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getCharSet() {
        return this.charSet;
    }

    public void setCharSet(final String charSet) {
        this.charSet = charSet;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public String getCookies() {
        return this.cookies;
    }

    public void setCookies(final String cookies) {
        this.cookies = cookies;
    }

    public T getParams() {
        return this.params;
    }

    public void setParams(final T params) {
        this.params = params;
    }

}
