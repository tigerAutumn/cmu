package cc.newex.commons.security.xss;

import org.owasp.encoder.Encode;

/**
 * 基于@see org.owasp.encoder.Encode 实现的XssFilter
 *
 * @author newex-team
 * @date 2017/12/09
 */
public class OwaspFilterPolicy implements FilterPolicy {
    /**
     * {@inheritDoc}
     */
    @Override
    public String filter(final String input) {
        return Encode.forHtmlContent(input);
    }
}
