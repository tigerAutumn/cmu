package cc.newex.dax.perpetual.util;

import cc.newex.dax.perpetual.common.constant.PerpetualConstants;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;

/**
 * 常用计算公式工具
 *
 * @author newex-team
 * @date 2018/7/27
 */
public class BigDecimalUtil {

    public static BigDecimal add(final BigDecimal b1, final BigDecimal b2) {
        return b1.add(b2).setScale(PerpetualConstants.SCALE, BigDecimal.ROUND_DOWN);
    }

    /**
     * 多个数据相加
     *
     * @param b
     * @return
     */
    public static BigDecimal add(final BigDecimal... b) {
        if (b == null || b.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal ret = BigDecimal.ZERO;
        for (final BigDecimal tmp : b) {
            ret = BigDecimalUtil.add(ret, tmp);
        }
        return ret;
    }

    /**
     * 判断是否为0
     *
     * @param num
     * @return
     */
    public static boolean isZero(final BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否大于0
     *
     * @param num
     * @return
     */
    public static boolean isGreeaterThanZero(final BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否小于0
     *
     * @param num
     * @return
     */
    public static boolean isLessThanZero(final BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) < 0) {
            return true;
        }
        return false;
    }

    public static BigDecimal subtract(final BigDecimal b1, final BigDecimal b2) {
        return b1.subtract(b2).setScale(PerpetualConstants.SCALE, BigDecimal.ROUND_DOWN);
    }

    /**
     * 减法
     *
     * @param minuend    被减数
     * @param subtractor 减数数组
     * @return
     */
    public static BigDecimal subtract(final BigDecimal minuend, final BigDecimal... subtractor) {
        if (subtractor == null || subtractor.length == 0) {
            return minuend;
        }
        BigDecimal ret = BigDecimal.ZERO;
        for (final BigDecimal tmp : subtractor) {
            ret = BigDecimalUtil.subtract(minuend, tmp);
        }
        return ret;
    }

    public static BigDecimal multiply(final BigDecimal b1, final BigDecimal b2) {
        return b1.multiply(b2).setScale(PerpetualConstants.SCALE, BigDecimal.ROUND_DOWN);
    }

    /**
     * 多个数相乘
     *
     * @param b
     * @return
     */
    public static BigDecimal multiply(final BigDecimal... b) {
        if (b == null || b.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal ret = BigDecimal.ONE;
        for (final BigDecimal tmp : b) {
            ret = BigDecimalUtil.multiply(ret, tmp);
        }
        return ret;
    }

    /**
     * 除法
     *
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal divide(final BigDecimal b1, final BigDecimal b2) {
        return b1.divide(b2, PerpetualConstants.SCALE, BigDecimal.ROUND_DOWN);
    }

    /**
     * 除法,向上去
     *
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal divideUp(final BigDecimal b1, final BigDecimal b2) {
        return b1.divide(b2, PerpetualConstants.SCALE, BigDecimal.ROUND_UP);
    }

    /**
     * 除法,保留scale位小数
     *
     * @param b1
     * @param b2
     * @param scale
     * @return
     */
    public static BigDecimal divide(final BigDecimal b1, final BigDecimal b2, final int scale) {
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN);
    }

    /**
     * 除法,保留scale位小数
     *
     * @param b1
     * @param b2
     * @param scale
     * @return
     */
    public static BigDecimal divideUp(final BigDecimal b1, final BigDecimal b2, final int scale) {
        return b1.divide(b2, scale, BigDecimal.ROUND_UP);
    }

    /**
     * 判断是否整数
     *
     * @param b
     * @return
     */
    public static boolean isInteger(final BigDecimal b) {
        if (new BigDecimal(b.intValue()).compareTo(b) == 0) {
            return true;
        }
        return false;
    }

    public static BigDecimal clamp(final BigDecimal val, final BigDecimal min, final BigDecimal max) {
        return min.max(max.min(val));
    }

    /**
     * 保留size位小数
     *
     * @param size
     * @return
     */
    public static DecimalFormat getDecimalPlace(final Integer size) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0.");
        // 默认2位小数
        if (size <= 0) {
            return new DecimalFormat("0.00");
        }
        for (int i = 0; i < size; i++) {
            stringBuilder.append("0");
        }
        return new DecimalFormat(stringBuilder.toString());
    }

    public static boolean isNotBigDecimal(final String value) {
        return !isBigDecimal(value);
    }

    /**
     * 判断是否BigDecimal类型
     *
     * @param value
     * @return
     */
    public static boolean isBigDecimal(final String value) {
        try {
            new BigDecimal(value);

            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * 保留指定位数
     *
     * @param num
     * @param digit
     * @return
     */
    public static String setDigit(final BigDecimal num, final int digit) {
        return Optional.ofNullable(num).orElse(BigDecimal.ZERO).setScale(digit, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString();

    }
}
