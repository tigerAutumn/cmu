package cc.newex.commons.lang.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class BigDecimalSerializer implements ObjectSerializer {
    @Override
    public void write(final JSONSerializer serializer, final Object object, final Object fieldName,
                      final Type fieldType,
                      final int features) {
        final SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeString("0");
            return;
        }
        final BigDecimal obj = (BigDecimal) object;
        out.writeString(obj.stripTrailingZeros().toPlainString());
    }
}
