package cc.newex.dax.boss.web.common.security;

import cc.newex.commons.security.MembershipFacade;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.dax.boss.admin.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
@Component
public class BossAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private BossUserDetailsService userDetailsService;
    @Autowired
    private MembershipFacade<User> membershipFacade;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain chain) throws ServletException, IOException {
        final String authToken = request.getHeader("x-auth-token");
        if (request.getSession().getAttribute(UserAuthConsts.CURRENT_USER) == null) {
            chain.doFilter(request, response);
            return;
        }

        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
        log.debug("authentication user {}", user.getAccount());
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, null,
                BossUserFactory.mapToGrantedAuthorities(this.membershipFacade.getPermissionSet(user.getRoles()))
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        log.debug("authenticated user {}, setting security context", user.getAccount());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.debug("authenticated user {}, setting request context", user.getAccount());
        request.setAttribute(UserAuthConsts.CURRENT_USER, user);

        chain.doFilter(request, response);
    }
}