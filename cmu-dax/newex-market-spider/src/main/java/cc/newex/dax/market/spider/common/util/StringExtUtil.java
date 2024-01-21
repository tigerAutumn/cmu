package cc.newex.dax.market.spider.common.util;

import java.text.DecimalFormat;

public class StringExtUtil {
    public static String doubleFormatCommonCut(final double value, final int rate) {
        final StringBuffer format = new StringBuffer("0.");
        if (rate >= 0) {
            for (int i = 0; i < rate; ++i) {
                format.append("0");
            }
        }

        final DecimalFormat df = new DecimalFormat(format.toString());
        return df.format(value).replaceAll("0+?$", "").replaceAll("[.]$", "");
    }
}
