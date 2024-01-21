package cc.newex.dax.market.common.util;

import com.alibaba.fastjson.JSON;

public class JSONUtil {

    public static String toJSONString(final Object obj) {
        final String result = JSON.toJSONString(obj);
        return result;
    }

}
