package cc.newex.dax.boss.security.service.impl;

import cc.newex.dax.boss.security.service.AdminUserPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户密码服务类
 *
 * @author newex-team
 * @date 2017-03-18
 **/
@Slf4j
@Service
public class AdminUserPasswordServiceImpl implements AdminUserPasswordService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String genreateSalt() {
        return StringUtils.EMPTY;
    }

    @Override
    public String encode(final CharSequence rawPassword, final String credentialsSalt) {
        return this.passwordEncoder.encode(rawPassword);
    }

    @Override
    public String encode(final CharSequence rawPassword) {
        return this.encode(rawPassword, "");
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return this.matches(rawPassword, encodedPassword, "");
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword, final String credentialsSalt) {
        return this.passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
