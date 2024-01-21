package cc.newex.dax.users.service.verification;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * 短信服务
 *
 * @author newex-team
 */
public interface SmsService {

    void send(String phone, String ip, String templateId, Map<String, String> param, @Nullable String captcha);

    String getCaptcha(String phone);

    String getRandomCode();
}
