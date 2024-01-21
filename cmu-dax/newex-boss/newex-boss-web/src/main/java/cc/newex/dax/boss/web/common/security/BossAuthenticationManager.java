package cc.newex.dax.boss.web.common.security;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
public class BossAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        throw new NotImplementedException("NotImplementedException");
    }
}
