package cc.newex.commons.support.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.owasp.encoder.Encode;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;


/**
 * [@RequestBody] Xss过滤器类
 *
 * @author newex-team
 * @date 2017/12/09
 */
public class XssFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

    @Override
    public Object read(final Type type, final Class<?> contextClass,
                       final HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return this.readType(this.getType(type, contextClass), inputMessage);
    }

    private Object readType(final Type type, final HttpInputMessage inputMessage) throws IOException {
        try {
            final InputStream in = inputMessage.getBody();
            final Object obj = JSON.parseObject(in,
                    this.getFastJsonConfig().getCharset(), type, this.getFastJsonConfig().getFeatures());
            final String json = JSON.toJSONString(obj);
            final String result = this.cleanXSS(json);
            return JSON.parseObject(result, type, this.getFastJsonConfig().getFeatures());
        } catch (final JSONException ex) {
            throw new HttpMessageNotReadableException("JSON parse error: " + ex.getMessage(), ex);
        } catch (final IOException ex) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", ex);
        }
    }

    @Override
    protected void writeInternal(final Object object, final HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        super.writeInternal(object, outputMessage);
    }

    private String cleanXSS(final String value) {
        return Encode.forHtmlContent(value);
    }
}
