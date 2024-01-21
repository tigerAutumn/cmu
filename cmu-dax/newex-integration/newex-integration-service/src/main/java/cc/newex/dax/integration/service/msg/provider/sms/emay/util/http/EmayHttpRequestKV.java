package cc.newex.dax.integration.service.msg.provider.sms.emay.util.http;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 传输数据为Key-Value的请求实体
 *
 * @author Frank
 */
public class EmayHttpRequestKV extends EmayHttpRequest<Map<String, String>> {

    public EmayHttpRequestKV(final String url, final String charSet, final String method, final Map<String, String> headers, final String cookies, final Map<String, String> params) {
        super(url, charSet, method, headers, cookies, params);
    }

    @Override
    public byte[] paramsToBytesForPost() {
        final String paramStr = this.paramsToStringForGet();
        if (paramStr == null) {
            return null;
        }
        byte[] param = null;
        try {
            param = paramStr.getBytes(this.getCharSet());
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return param;
    }

    @Override
    public String paramsToStringForGet() {
        final Map<String, String> params = this.getParams();
        if (params == null || params.size() == 0) {
            return null;
        }
        final StringBuffer buffer = new StringBuffer();
        for (final Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                buffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        String param = buffer.toString();
        param = param.substring(0, param.length() - 1);
        return param;
    }

}
