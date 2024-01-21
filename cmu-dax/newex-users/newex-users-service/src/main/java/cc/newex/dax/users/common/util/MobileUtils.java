package cc.newex.dax.users.common.util;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.stream.Stream;

public class MobileUtils {
    /**
     * 手机号加密
     *
     * @param phoneNo 手机号码
     * @return
     */
    public static String getScreenPhoneNumber(final String phoneNo) {
        return MobileUtils.getScreenPhoneNumber(3, phoneNo, 4);
    }

    /**
     * 获取手机号后四位
     *
     * @param mobile
     * @return
     */
    public static String getMobileSuffix(final String mobile, final int len) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }

        if (mobile.length() < len) {
            return mobile;
        }

        return mobile.substring(mobile.length() - len);
    }

    /**
     * 手机号加密
     *
     * @param prefixLength 前缀长度
     * @param phoneNo      手机号码
     * @param suffixLength 后缀长度
     * @return
     */
    public static String getScreenPhoneNumber(final int prefixLength, final String phoneNo, final int suffixLength) {
        String result = "";
        if (StringUtils.isNotBlank(phoneNo)) {
            final int length = phoneNo.length();
            final String prefix = phoneNo.substring(0, prefixLength);
            final int i = length - (prefixLength + suffixLength);
            final StringBuilder stringBuilder = new StringBuilder();

            Stream.iterate("*", item -> "*").limit(i).forEach(event -> {
                stringBuilder.append(event);
            });
            final String body = stringBuilder.toString();
            final String suffix = phoneNo.substring(prefixLength + suffixLength, length);
            result = prefix + body + suffix;
        }
        return result;
    }

    /**
     * 根据国家代码和手机号 判断手机号是否有效
     *
     * @param phoneNumber
     * @param countryCode
     * @return true|false
     */
    public static boolean checkPhoneNumber(final String phoneNumber, final String countryCode) {
        final int ccode = NumberUtils.toInt(countryCode, 0);
        final long phone = NumberUtils.toLong(phoneNumber, 0L);

        final PhoneNumber pn = new PhoneNumber();
        pn.setCountryCode(ccode);
        pn.setNationalNumber(phone);
        return PhoneNumberUtil.getInstance().isValidNumber(pn);
    }


    public static void main(final String[] args) {
        System.out.println(checkPhoneNumber("18513838912", "86"));
    }
}
