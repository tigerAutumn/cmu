package cc.newex.dax.integration.service.msg.provider.sms.emay.util.http;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * 解析自定义响应的解析器
 *
 * @author Frank
 */
public class EmayHttpResponseBytesPraser implements EmayHttpResponsePraser<EmayHttpResponseBytes> {

    @Override
    public EmayHttpResponseBytes prase(final String charSet, final EmayHttpResultCode resultCode, final int httpCode, final Map<String, String> headers, final List<String> cookies, final ByteArrayOutputStream outputStream) {
        return new EmayHttpResponseBytes(charSet, resultCode, httpCode, headers, cookies, outputStream == null ? null : outputStream.toByteArray());
    }


}
