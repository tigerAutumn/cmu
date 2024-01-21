package cc.newex.dax.boss.web.common.security;

import cc.newex.commons.security.MembershipFacade;
import cc.newex.dax.boss.admin.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Function;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
@Service
public class BossUserDetailsServiceImpl implements BossUserDetailsService {
    @Autowired
    private MembershipFacade<User> membershipFacade;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.loadUserByUserName(username, this.membershipFacade::getUser);
    }

    @Override
    public String loadGoogleKeyByUsername(final String username) throws UsernameNotFoundException {
        return StringUtils.EMPTY;
    }

    @Override
    public BossUserDetails loadUserNonSensitiveInfoByUsername(final String username) throws UsernameNotFoundException {
        return this.loadUserByUserName(username, this.membershipFacade::getUserNonSensitiveInfo);
    }

    private BossUserDetails loadUserByUserName(final String username, final Function<String, User> func) {
        final User user = func.apply(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        final Set<String> authorities = this.membershipFacade.getPermissionSet(user.getRoles());
        return BossUserFactory.create(user, authorities);
    }
}
