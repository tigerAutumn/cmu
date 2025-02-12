package cc.newex.dax.integration.service.msg.provider.sms.emay.util.http;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Http响应解析器
 *
 * @param <T> http响应数据转换后实体
 * @author Frank
 */
public interface EmayHttpResponsePraser<T extends Object> {

    /**
     * 解析
     *
     * @param charSet      响应编码
     * @param resultCode   HttpClient结果编码
     * @param httpCode     Http状态码
     * @param headers      http响应头
     * @param cookies      http响应Cookies
     * @param outputStream http响应数据
     * @return T http响应数据转换后实体
     */
    public T prase(String charSet, EmayHttpResultCode resultCode, int httpCode, Map<String, String> headers,
                   List<String> cookies, ByteArrayOutputStream outputStream);

}
