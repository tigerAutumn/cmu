package cc.newex.dax.integration.service.msg.provider.email;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.dax.integration.common.html.HTMLDecoder;
import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.service.msg.provider.AbstractMessageProvider;
import cc.newex.dax.integration.service.msg.provider.MessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件发送基类
 *
 * @author newex-team
 * @date 2018-05-18
 */
@Slf4j
public abstract class AbstractEmailProvider
        extends AbstractMessageProvider implements MessageProvider {
    private JavaMailSenderImpl mailSender;
    private Map<String, Object> options;

    protected void build() {
        this.mailSender = new JavaMailSenderImpl();
        this.mailSender.setHost(MapUtils.getString(this.options, "host"));
        this.mailSender.setPort(MapUtils.getInteger(this.options, "port"));
        this.mailSender.setUsername(MapUtils.getString(this.options, "username"));
        this.mailSender.setPassword(MapUtils.getString(this.options, "password"));

        final Properties props = this.mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.connectiontimeout", 5000);
        props.put("mail.smtp.timeout", 5000);
    }

    @Override
    public void setOptions(final Map<String, Object> options) {
        this.options = options;
        this.build();
    }

    @Override
    public Map<String, Object> getOptions() {
        return this.options;
    }

    @Override
    public boolean send(final Message message) {
        if (!StringUtil.isEmail(message.getEmailAddress())) {
            log.warn("Msg ID:[{}],Invalid email address:{}", message.getId(), message.getEmailAddress());
            return false;
        }

        try {
            final MimeMessage mimeMsg = this.mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, StandardCharsets.UTF_8.name());
            helper.setFrom(this.mailSender.getUsername(), message.getFromAlias());
            helper.setTo(message.getEmailAddress());
            helper.setSubject(message.getSubject());

            String content = HTMLDecoder.decode(message.getContent());
            helper.setText(content, true);

            this.mailSender.send(mimeMsg);

            log.debug("Msg ID:[{}],Sender:{},Template:{},Receiver:{},Success",
                    message.getId(), this.getName(), message.getTemplateCode(), message.getEmailAddress());
            return true;
        } catch (final Exception e) {
            log.error("Msg ID:[{}],Sender:{},Template:{},Receiver:{},failure,Error:{}",
                    message.getId(), this.getName(), message.getTemplateCode(), message.getEmailAddress(), e.getMessage());
            return false;
        }
    }
}
