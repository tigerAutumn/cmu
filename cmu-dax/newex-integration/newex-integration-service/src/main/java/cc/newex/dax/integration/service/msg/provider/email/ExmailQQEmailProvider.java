package cc.newex.dax.integration.service.msg.provider.email;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.service.msg.provider.MessageProvider;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 腾讯企业邮箱
 *
 * @author newex-team
 * @date 2018-05-18
 */
public class ExmailQQEmailProvider
        extends AbstractEmailProvider implements MessageProvider {

    public ExmailQQEmailProvider() {
    }

    public static void main(final String... args) {
        final Map<String, Object> options = Maps.newHashMap();
        options.put("host", "smtp.exmail.qq.com");
        options.put("port", "465");
        options.put("username", "service@coinmex.com");
        options.put("password", "Coin0417");

        final Message message = Message.builder()
                .subject("I am coinmex.com owner")
                .emailAddress("central@sonatype.com")
                .content("hi,I'm the www.coinmex.com owner,please check https://issues.sonatype.org/browse/OSSRH-41465,thanks!")
                .build();

        final CndnsEmailProvider provider = new CndnsEmailProvider();
        provider.setOptions(options);
        provider.send(message);
    }

    @Override
    public String getName() {
        return "qq-email";
    }
}
