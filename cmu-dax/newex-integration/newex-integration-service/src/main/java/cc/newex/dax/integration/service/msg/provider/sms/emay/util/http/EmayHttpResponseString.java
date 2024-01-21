package cc.newex.dax.integration.service.msg.provider.sms.emay.util.http;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 【自定义】String类型Http响应
 *
 * @author Frank
 */
public class EmayHttpResponseString {

    private EmayHttpResultCode resultCode; //HttpClient 结果代码
    private int httpCode; //Http链接Code
    private Map<String, String> headers;//Http响应头
    private List<String> cookies;//http响应Cookies
    private String resultString;//http响应数据
    private String charSet;//http响应编码

    /**
     * @param charSet      http响应编码
     * @param resultCode   HttpClient结果代码
     * @param httpCode     Http链接Code
     * @param headers      Http响应头
     * @param cookies      http响应Cookies
     * @param resultString http响应数据
     */
    public EmayHttpResponseString(final String charSet, final EmayHttpResultCode resultCode, final int httpCode, final Map<String, String> headers, final List<String> cookies, final String resultString) {
        this.resultCode = resultCode;
        this.httpCode = httpCode;
        this.headers = headers;
        this.cookies = cookies;
        this.resultString = resultString;
        this.charSet = charSet;
    }

    public EmayHttpResultCode getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(final EmayHttpResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public void setHttpCode(final int httpCode) {
        this.httpCode = httpCode;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public List<String> getCookies() {
        return this.cookies;
    }

    public void setCookies(final List<String> cookies) {
        this.cookies = cookies;
    }

    public String getResultString() {
        return this.resultString;
    }

    public void setResultString(final String resultString) {
        this.resultString = resultString;
    }

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer();
        for (final Field filed : this.getClass().getDeclaredFields()) {
            Object value = null;
            try {
                value = filed.get(this);
            } catch (final IllegalArgumentException e) {
                e.printStackTrace();
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            }
            buffer.append("[ ").append(filed.getName()).append(" : ").append(value).append(" ]");
        }
        return buffer.toString();

    }

    public String getCharSet() {
        return this.charSet;
    }

    public void setCharSet(final String charSet) {
        this.charSet = charSet;
    }

}
