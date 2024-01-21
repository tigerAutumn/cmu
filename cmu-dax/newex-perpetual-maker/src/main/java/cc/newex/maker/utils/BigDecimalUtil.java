package cc.newex.maker.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Optional;

/**
 * 常用计算公式工具
 *
 * @author newex-team
 * @date 2018/7/27
 */
public class BigDecimalUtil {
    private static final int scale = 0;
    private static final int scale_2 = 2;

    public static BigDecimal subtract(final BigDecimal b1, final BigDecimal b2) {
        return b1.subtract(b2).setScale(scale_2, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal multiply(final BigDecimal b1, final BigDecimal b2) {
        return b1.multiply(b2).setScale(scale_2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 除法
     *
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal divide(final BigDecimal b1, final BigDecimal b2) {
        return b1.divide(b2, scale_2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 获取挂空单-价格
     *
     * @param price 价格
     * @param num   循环次数
     * @return
     */
    static BigDecimal getShortOrderPrice(final BigDecimal price, final int num) {
        final BigDecimal xprice = BigDecimal.ONE.add(new BigDecimal(0.0005).multiply(new BigDecimal(num))).multiply(price);
        return xprice.setScale(BigDecimalUtil.scale_2, RoundingMode.DOWN);
    }

    /**
     * 获取挂空单-数量
     *
     * @param amount 数量
     * @param num    循环次数
     * @return
     */
    static BigDecimal getShortOrderAmount(final BigDecimal amount, final int num) {
        final BigDecimal yamount = new BigDecimal(2).add(new BigDecimal(0.1).multiply(new BigDecimal(num - 1)))
                .multiply(amount)
                .add(new BigDecimal(10).multiply(new BigDecimal(num - 1)));
        return yamount.setScale(BigDecimalUtil.scale, RoundingMode.DOWN);
    }


    /**
     * 获取挂多单-价格
     *
     * @param price 价格
     * @param num   循环次数
     * @return
     */
    static BigDecimal getLongOrderPrice(final BigDecimal price, final int num) {
        final BigDecimal xprice = BigDecimal.ONE.subtract(new BigDecimal(0.0005).multiply(new BigDecimal(num))).multiply(price);
        return xprice.setScale(BigDecimalUtil.scale_2, RoundingMode.DOWN);
    }


    /**
     * 获取挂多单-数量
     *
     * @param amount 数量
     * @param num    循环次数
     * @return
     */
    static BigDecimal getLongOrderAmount(final BigDecimal amount, final int num) {
        final BigDecimal yamount = new BigDecimal(2).multiply(amount)
                .add(new BigDecimal(10).multiply(new BigDecimal(num)));
        return yamount.setScale(BigDecimalUtil.scale, RoundingMode.DOWN);
    }

    /**
     * 保留size位小数
     *
     * @param size
     * @return
     */
    static DecimalFormat getDecimalPlace(final Integer size) {
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

    /**
     * 保留指定位数
     *
     * @param num
     * @param digit
     * @return
     */
    public static String setDigit(final BigDecimal num, final int digit) {
        return Optional.ofNullable(num).orElse(BigDecimal.ZERO).setScale(digit, RoundingMode.FLOOR).toPlainString();
    }


    public static void main(final String[] args) {
//        System.out.println(getShortOrderPrice(new BigDecimal(100), 1));
//        System.out.println(getShortOrderAmount(new BigDecimal(1000), 1));
//        System.out.println(getLongOrderPrice(new BigDecimal(100), 1));
//        System.out.println(getLongOrderAmount(new BigDecimal(1000), 1));
        final BigDecimal p = new BigDecimal(100);
        for (int i = 0; i < 20; i++) {

            final BigDecimal price = getLongOrderPrice(p, i);
//            System.out.println("i=" + i + "  price=" + price);
        }
    }
}
