package cc.newex.dax.integration.service.msg.provider.sms.emay.util.http;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 解析自定义响应的解析器
 *
 * @author Frank
 */
public class EmayHttpResponseStringPraser implements EmayHttpResponsePraser<EmayHttpResponseString> {

    @Override
    public EmayHttpResponseString prase(final String charSet, final EmayHttpResultCode resultCode, final int httpCode, final Map<String, String> headers, final List<String> cookies, final ByteArrayOutputStream outputStream) {
        String st = null;
        try {
            if (outputStream != null) {
                final byte[] resultBytes = outputStream.toByteArray();
                st = new String(resultBytes, charSet);
            }
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new EmayHttpResponseString(charSet, resultCode, httpCode, headers, cookies, st);
    }


}
