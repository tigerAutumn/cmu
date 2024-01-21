package cc.newex.dax.boss.web.common.security;

import cc.newex.dax.boss.admin.domain.User;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
public final class BossUserFactory {

    private BossUserFactory() {
    }

    public static BossUserDetails create(final User user, final Set<String> authorities) {
        return new BossUserDetails(
                user.getId(),
                user.getAccount(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(authorities),
                BooleanUtils.toBoolean(user.getStatus())
        );
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(final Set<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
