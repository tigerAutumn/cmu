package cc.newex.wallet.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
public class EthereumUtil {
    public static final int PASS_RADIX = 16;
    static final double PASS_VALUE = 1.0E18;

    public static BigInteger hexToBigInteger(String hex) {
        if (hex == null || hex.length() < 2) {
            return new BigInteger("0");
        }
        hex = hex.toLowerCase();
        if (!hex.startsWith("0x")) {
            return new BigInteger("0");
        }
        final String longStr = hex.substring(2);
        return new BigInteger(longStr, EthereumUtil.PASS_RADIX);
    }

    public static long hexToLong(String hex) {
        if (hex == null || hex.length() < 2) {
            return 0;
        }
        hex = hex.toLowerCase();
        if (!hex.startsWith("0x")) {
            return 0;
        }
        final String longStr = hex.substring(2);
        return Long.parseLong(longStr, EthereumUtil.PASS_RADIX);
    }

    public static String longToHex(final long value) {
        return "0x" + Long.toHexString(value);
    }

    public static String doubleToHex(final double value) {
        // 转成 hex
        final BigDecimal bd1 = new BigDecimal(Double.toString(value));
        final BigDecimal bd2 = new BigDecimal(Double.toString(EthereumUtil.PASS_VALUE));
        final BigInteger a = bd1.multiply(bd2).toBigInteger();
        return "0x" + a.toString(EthereumUtil.PASS_RADIX);
    }

    public static double hexToDouble(String hex) {
        if (hex == null || hex.length() < 2) {
            return 0;
        }
        hex = hex.toLowerCase();
        if (!hex.startsWith("0x")) {
            return 0;
        }
        final String longStr = hex.substring(2);
        final BigInteger in = new BigInteger(longStr, EthereumUtil.PASS_RADIX);
        return DoubleUtil.round(in.doubleValue(), 8);
    }

}