package cc.newex.commons.support.i18n;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

/**
 * 自定义CookieLocaleResolver
 * 为了兼容language语言en-US/en_US两种模式
 *
 * @author newex-team
 * @date 2017/12/09
 */
public class CustomCookieLocaleResolver extends CookieLocaleResolver {
    @Override
    protected Locale parseLocaleValue(final String locale) {
        if (locale == null) {
            return this.getDefaultLocale();
        }
        return LocaleUtils.getSupportedLocale(locale);
    }
}
