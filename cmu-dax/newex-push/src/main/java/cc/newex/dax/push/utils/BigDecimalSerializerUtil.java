package cc.newex.dax.push.utils;

import java.math.BigDecimal;

/**
 * 序列化
 *
 * @author xionghui
 * @date 2018/09/19
 */
public class BigDecimalSerializerUtil {

  public static String serializer(BigDecimal value) {
    if (value == null) {
      return "0";
    }
    return value.stripTrailingZeros().toPlainString();
  }
}
