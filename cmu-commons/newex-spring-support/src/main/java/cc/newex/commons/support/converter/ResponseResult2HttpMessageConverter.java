package cc.newex.commons.support.converter;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * ResponseResult<T> 序列化输出转换类
 *
 * @author newex-team
 * @date 2017/12/09
 */
@Slf4j
public class ResponseResult2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public ResponseResult2HttpMessageConverter() {
        log.debug("load {}", this.getClass().getName());
    }

    @Override
    protected void writeInternal(final Object object, final Type type, final HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        if (object instanceof ResponseResult) {
            super.writeInternal(object, type, outputMessage);
        } else {
            final ResponseResult result = ResultUtils.success(object);
            super.writeInternal(result, type, outputMessage);
        }
    }

    @Override
    protected void writeInternal(final Object object, final HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        this.writeInternal(object, null, outputMessage);
    }
}
