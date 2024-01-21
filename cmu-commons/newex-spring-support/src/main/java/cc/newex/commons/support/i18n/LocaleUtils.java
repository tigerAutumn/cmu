package cc.newex.commons.support.i18n;

import cc.newex.commons.support.consts.AppEnvConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author newex-team
 * @date 2017/12/09
 **/
@Slf4j
@Configuration
public class LocaleUtils {
    /**
     * 请求终端
     */
    public final static String SPIDER = "spider";
    public final static String ANDROID = "android";
    public final static String IPHONE = "iphone";
    public final static String IOS = "ios";

    /**
     * 请求头User-Agent
     */
    public final static String USER_AGENT = "User-Agent";

    /**
     * 请求头x-locale用于解决app(android,ios及feignclient)请求中含带语言设置
     */
    public final static String HEADER_X_LOCALE = "x-locale";

    /**
     * 繁体中文
     */
    public final static String[] TRADITIONAL_CHINESE = {"zh-tw", "zh_tw", "zh-hk", "zh_hk", "zh-hant", "zh_hant"};

    /**
     * 匹配手机端的UA中的本地化语言
     */
    private final static Pattern LOCALE_PATTERN = Pattern.compile("locale=(.*)$", Pattern.CASE_INSENSITIVE);

    private static final char UNDERLINE = '_';
    private static final char DASH = '-';

    private static LocaleResolver localeResolver;
    private static MessageSource messageSource;

    @Autowired
    public LocaleUtils(final LocaleResolver localeResolver, final MessageSource messageSource) {
        LocaleUtils.localeResolver = localeResolver;
        LocaleUtils.messageSource = messageSource;
    }

    /**
     * 根据当前request对象中的locale(Header的Accept属性)初始化系统国际化语言区域环境
     *
     * @param request  当前请求对象
     * @param response 当前响应对象
     */
    public static void setInitLocale(final HttpServletRequest request, final HttpServletResponse response) {
        final Locale locale = request.getLocale();

        if (localeResolver instanceof CookieLocaleResolver) {
            final CookieLocaleResolver cookieLocaleResolver = (CookieLocaleResolver) localeResolver;
            final Cookie cookie = WebUtils.getCookie(request, cookieLocaleResolver.getCookieName());
            if (cookie == null) {
                setLocale(request, response, locale,
                        "Init CookieLocaleResolver locale url :{},country:{},lang:{}");
                return;
            }
            log.debug("CookieLocaleResolver locale name:{} ,value:{}", cookie.getName(), cookie.getValue());
        }

        if (localeResolver instanceof SessionLocaleResolver) {
            final Locale sessionLocale = (Locale) WebUtils.getRequiredSessionAttribute(
                    request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
            if (sessionLocale == null) {
                setLocale(request, response, locale,
                        "Init SessionLocaleResolver locale url :{}, country:{},lang:{}");
                return;
            }
            log.debug("SessionLocaleResolver Locale: {}", sessionLocale.toLanguageTag());
        }
    }

    /**
     * @param request
     * @param response
     * @param locale
     * @param format
     */
    private static void setLocale(final HttpServletRequest request, final HttpServletResponse response,
                                  final Locale locale, final String format) {
        setLocale(locale.toLanguageTag(), request, response);
        log.debug(format, request.getRequestURL().toString(), locale.getCountry(), locale.toLanguageTag());
    }

    /**
     * 设置国际化语言区域环境
     * 自动判断多请求端(web,android,ios,spider)
     *
     * @param request
     * @param response
     */
    public static void setLocale(final HttpServletRequest request, final HttpServletResponse response) {
        //如果当前请求已经带上本地化语言的cookie（即已经设置了当前语言环境）就不再次进行设置
        final Cookie cookie = WebUtils.getCookie(request, getLocaleCookieName());
        if (cookie != null) {
            return;
        }

        //如果当前请求是来自apps(android,ios)与feign client的
        final String xLocale = request.getHeader(HEADER_X_LOCALE);
        if (StringUtils.isNotBlank(xLocale)) {
            setLocale(xLocale, request, response);
            return;
        }

        //如果当前请求是来自spider
        final String userAgent = StringUtils.defaultIfBlank(
                request.getHeader(USER_AGENT), StringUtils.EMPTY).toLowerCase();
        log.debug("USER_AGENT :{}", userAgent);

        if (StringUtils.containsIgnoreCase(userAgent, SPIDER)) {
            final Matcher matcher = LOCALE_PATTERN.matcher(userAgent);
            if (matcher.find()) {
                final String lang = matcher.group(1);
                setLocale(lang, request, response);
            } else {
                localeResolver.setLocale(request, response, Locale.US);
            }
            return;
        }
        setInitLocale(request, response);
    }

    /**
     * 设置国际化语言区域环境
     *
     * @param lang     国际化语言名称
     * @param request  当前请求对象
     * @param response 当前响应对象
     */
    public static void setLocale(final String lang, final HttpServletRequest request, final HttpServletResponse response) {
        final Locale locale = getSupportedLocale(lang);
        localeResolver.setLocale(request, response, locale);
    }

    /**
     * 获取当前上下文本地化(locale)语言设置值
     *
     * @return 返回Locale对象的toLanguageTag名称(如zh - CN | en - US等)
     */
    public static String getLocale() {
        return LocaleContextHolder.getLocale().toLanguageTag();
    }

    /**
     * 获取当前系统支持的locale
     *
     * @param lang 当前请求中的lang
     * @return {@link Locale}
     */
    public static Locale getSupportedLocale(String lang) {
        Locale locale = Locale.US;
        if (StringUtils.isNotBlank(lang)) {
            lang = StringUtils.trim(parseToLanguageTag(lang));
            if (StringUtils.equalsAny(lang.toLowerCase(), AppEnvConsts.SUPPORTED_LOCALES)) {
                locale = Locale.forLanguageTag(lang);
            }
        }
        return locale;
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
    public static String getLocale(final HttpServletRequest request, final String cookieName) {
        final Cookie cookie = WebUtils.getCookie(request, cookieName);
        final String lang = (cookie != null ? parseToLanguageTag(cookie.getValue()) : StringUtils.EMPTY);
        return getSupportedLocale(lang).toLanguageTag();
    }

    /**
     * @param code 对应messages配置的key.
     * @return String
     */
    public static String getMessage(final String code) {
        return getMessage(code, null);
    }

    /**
     * @param code 对应messages配置的key.
     * @param args 数组参数.
     * @return String
     */
    public static String getMessage(final String code, final Object[] args) {
        return getMessage(code, args, "");
    }

    /**
     * @param code           对应messages配置的key.
     * @param args           数组参数.
     * @param defaultMessage 没有设置key的时候的默认值.
     * @return String
     */
    public static String getMessage(final String code, final Object[] args, final String defaultMessage) {
        final Locale locale = LocaleContextHolder.getLocale();
        log.debug("Message Locale tag:{},value:{}", locale.toLanguageTag(), locale.toString());
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    /**
     * 获取指定前辍对应的所有的Message集合
     *
     * @param codePrefixes code前辍
     * @return Map[Key, Value]
     */
    public static Map<String, String> getMessages(final String... codePrefixes) {
        final Locale locale = LocaleContextHolder.getLocale();
        return ((CustomResourceBundleMessageSource) messageSource).getMessages(locale, codePrefixes);
    }

    /**
     * locale是否为繁体中文语言地区
     *
     * @param lang locale
     * @return true|false
     */
    public static boolean isTraditionalChinese(final String lang) {
        return StringUtils.equalsAnyIgnoreCase(lang, TRADITIONAL_CHINESE);
    }

    /**
     * 获取多语言cookie名称
     *
     * @return 多语言cookie名称
     */
    public static String getLocaleCookieName() {
        if (localeResolver instanceof CookieLocaleResolver) {
            return ((CookieLocaleResolver) localeResolver).getCookieName();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 把language 字符串转成LanguageTag格式
     *
     * @param locale (en-US|zh-CN)
     * @return {@see Locale#toLanguageTag()}
     */
    public static String parseToLanguageTag(final String locale) {
        return StringUtils.replaceChars(locale, UNDERLINE, DASH);
    }
}
