package cc.newex.dax.boss.web.common.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
public interface BossUserDetailsService extends UserDetailsService {
    /**
     * 获取用户非敏感数据列信息
     *
     * @param username 用户名
     * @return @see UserDetails
     */
    BossUserDetails loadUserNonSensitiveInfoByUsername(String username) throws UsernameNotFoundException;

    /**
     * 获取用户谷歌验证key
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    String loadGoogleKeyByUsername(final String username) throws UsernameNotFoundException;
}
