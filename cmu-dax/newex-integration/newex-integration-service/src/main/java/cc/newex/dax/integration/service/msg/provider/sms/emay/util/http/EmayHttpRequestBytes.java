package cc.newex.dax.integration.service.msg.provider.sms.emay.util.http;

import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * 传输数据为byte[]的请求实体
 *
 * @author Frank
 */
public class EmayHttpRequestBytes extends EmayHttpRequest<byte[]> {

    public EmayHttpRequestBytes(final String url, final String charSet, final String method, final Map<String, String> headers, final String cookies, final byte[] params) {
        super(url, charSet, method, headers, cookies, params);
    }

    @Override
    public byte[] paramsToBytesForPost() {
        return this.getParams();
    }

    @Override
    public String paramsToStringForGet() {
        try {
            return new String(this.getParams(), this.getCharSet());
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
