package cc.newex.dax.users.service.verification.impl;

import cc.newex.dax.users.service.verification.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author newex-team
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public void send(final String phone, final String ip, final String templateId, final Map<String, String> param,
                     @Nullable final String captcha) {
    }

    @Override
    public String getCaptcha(final String phone) {
        return null;
    }

    @Override
    public String getRandomCode() {
        return null;
    }
}
