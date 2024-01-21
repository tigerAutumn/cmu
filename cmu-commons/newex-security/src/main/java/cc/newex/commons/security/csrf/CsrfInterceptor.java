package cc.newex.commons.security.csrf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于http header referer方式的crsf实现
 *
 * @author newex-team
 * @date 2017/12/09
 * @see <a href="https://docs.spring.io/spring-security/site/docs/current/reference/html/csrf.html">CSRF</a>
 */
@Slf4j
public class CsrfInterceptor extends HandlerInterceptorAdapter {
    private static final String HEADER_REFERER = "Referer";
    private static final String HEADER_X_CSRF_ATTACK = "x-csrf-request";
    private static final String ALL = "all";
    private static final String GET_METHOD = "get";
    private final Pattern pattern;

    /**
     * <p>Constructor for CsrfInterceptor.</p>
     *
     * @param pattern a {@link java.lang.String} object.
     */
    public CsrfInterceptor(final String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        if (GET_METHOD.equalsIgnoreCase(request.getMethod()) ||
                StringUtils.equalsIgnoreCase(ALL, this.pattern.pattern())) {
            return true;
        }

        String referer = StringUtils.defaultIfBlank(request.getHeader(HEADER_REFERER), StringUtils.EMPTY);
        referer = referer.replace("&#47;", "/");
        final Matcher matcher = this.pattern.matcher(referer);
        if (matcher.find()) {
            return true;
        }

        final String urlAndReferer = String.format("url:%s;referer:%s", request.getRequestURL().toString(), referer);
        log.info("x-csrf-request:{}", urlAndReferer);
        response.setHeader(HEADER_X_CSRF_ATTACK, urlAndReferer);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return false;
    }
}
