package cc.newex.dax.extra.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author liutiejun
 * @date 2018-08-21
 */
public class SimpleLocaleUtils {

    /**
     * 请求头x-locale用于解决app(android,ios及feignclient)请求中含带语言设置
     */
    public final static String HEADER_X_LOCALE = "x-locale";

    private static final char UNDERLINE = '_';
    private static final char DASH = '-';

    public static String getLocaleByDefault(final HttpServletRequest request, String defaultVal) {
        if (StringUtils.isBlank(defaultVal)) {
            defaultVal = Locale.US.toLanguageTag().toLowerCase();
        }

        final String locale = getLocale(request);

        if (StringUtils.isBlank(locale)) {
            return defaultVal;
        }

        if (StringUtils.startsWithIgnoreCase(locale, "zh")) {
            return Locale.CHINA.toLanguageTag().toLowerCase();
        } else {
            return Locale.US.toLanguageTag().toLowerCase();
        }

    }

    /**
     * 从当前请求中获取请求本地化(locale)语言设置值
     *
     * @param request @see #HttpServletRequest
     * @return 返回Locale对象的toLanguageTag名称(如zh - CN | en - US等)
     */
    public static String getLocale(final HttpServletRequest request) {
        //如果当前请求是来自apps(android,ios)与feign client的
        final String xLocale = request.getHeader(HEADER_X_LOCALE);
        if (StringUtils.isNotBlank(xLocale)) {
            return getSupportedLocale(xLocale).toLanguageTag();
        }
        return getLocale(request, getLocaleCookieName());
    }

    /**
     * 从当前请求cookie中获取请求本地化(locale)语言设置值
     *
     * @param request    @see #HttpServletRequest
     * @param cookieName locale cookie 名称
     * @return 返回Locale对象的toLanguageTag名称(如zh - CN | en - US等)
     */
    private static String getLocale(final HttpServletRequest request, final String cookieName) {
        final Cookie cookie = WebUtils.getCookie(request, cookieName);
        final String lang = (cookie != null ? parseToLanguageTag(cookie.getValue()) : StringUtils.EMPTY);
        return getSupportedLocale(lang).toLanguageTag();
    }

    /**
     * 获取多语言cookie名称
     *
     * @return 多语言cookie名称
     */
    private static String getLocaleCookieName() {
        return "locale";
    }

    /**
     * 获取当前系统支持的locale
     *
     * @param lang 当前请求中的lang
     * @return {@link Locale}
     */
    private static Locale getSupportedLocale(String lang) {
        Locale locale = Locale.US;
        if (StringUtils.isNotBlank(lang)) {
            lang = StringUtils.trim(parseToLanguageTag(lang));

            locale = Locale.forLanguageTag(lang);
        }

        return locale;
    }

    /**
     * 把language 字符串转成LanguageTag格式
     *
     * @param locale (en-US|zh-CN)
     * @return {@see Locale#toLanguageTag()}
     */
    private static String parseToLanguageTag(final String locale) {
        return StringUtils.replaceChars(locale, UNDERLINE, DASH);
    }

}
