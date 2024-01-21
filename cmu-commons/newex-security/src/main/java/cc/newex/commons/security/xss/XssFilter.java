package cc.newex.commons.security.xss;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * XSS过滤器
 *
 * @author newex-team
 * @date 2017/12/09
 * @see <a href="https://en.wikipedia.org/wiki/Cross-site_scripting">XSS</a>
 */
public class XssFilter extends OncePerRequestFilter {
    /**
     * Constant <code>FILTER_POLICY="filterPolicy"</code>
     */
    public final static String FILTER_POLICY = "filterPolicy";
    /**
     * Constant <code>EXCLUDE_URL_PATTERNS="excludeUrlPatterns"</code>
     */
    public final static String EXCLUDE_URL_PATTERNS = "excludeUrlPatterns";
    private FilterPolicy filterPolicy = null;
    private String excludeUrlPatterns = null;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initFilterBean() throws ServletException {
        final String policyName = this.getFilterConfig().getInitParameter(FILTER_POLICY);
        this.excludeUrlPatterns = this.getFilterConfig().getInitParameter(EXCLUDE_URL_PATTERNS);
        this.filterPolicy = this.createFilterPolicy(policyName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest,
                                    final HttpServletResponse httpServletResponse,
                                    final FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(
                new XssHttpServletRequestWrapper(httpServletRequest, this.filterPolicy, this.excludeUrlPatterns),
                httpServletResponse
        );
    }

    private FilterPolicy createFilterPolicy(final String policyName) {
        if (StringUtils.equalsIgnoreCase(policyName, XssFilterPolicyEnum.CAL.getName())) {
            return new CalFilterPolicy();
        }
        return new OwaspFilterPolicy();
    }
}
