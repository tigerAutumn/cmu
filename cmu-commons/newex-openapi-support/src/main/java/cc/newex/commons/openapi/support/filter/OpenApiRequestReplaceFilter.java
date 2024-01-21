package cc.newex.commons.openapi.support.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OpenApiRequestReplaceFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        if (!(request instanceof OpenApiRequestInputStreamCacheWrapper)) {
            request = new OpenApiRequestInputStreamCacheWrapper(request);
        }
        filterChain.doFilter(request, response);
    }
}
