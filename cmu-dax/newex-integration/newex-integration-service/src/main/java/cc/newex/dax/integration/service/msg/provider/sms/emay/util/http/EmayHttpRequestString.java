package cc.newex.dax.integration.service.msg.provider.sms.emay.util.http;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 传输数据为String的请求实体
 *
 * @author Frank
 */
public class EmayHttpRequestString extends EmayHttpRequest<String> {

    public EmayHttpRequestString(final String url, final String charSet, final String method, final Map<String, String> headers, final String cookies, final String params) {
        super(url, charSet, method, headers, cookies, params);
    }

    @Override
    public byte[] paramsToBytesForPost() {
        try {
            return this.getParams().getBytes(this.getCharSet());
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String paramsToStringForGet() {
        return this.getParams();
    }


}
