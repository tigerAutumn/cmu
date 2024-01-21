package cc.newex.dax.integration.service.msg.provider.sms;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.service.msg.provider.MessageProvider;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author newex-team
 * @link https://www.twilio.com/
 * @date 2018-06-09
 */
@Slf4j
public class TwilioSmsProvider
        extends AbstractSmsProvider implements MessageProvider {
    private String accountSid;
    private String authToken;
    private String from;
    private Map<String, Object> options;

    @Override
    public String getName() {
        return "twilio-sms";
    }

    @Override
    public void setOptions(final Map<String, Object> options) {
        this.options = options;
        this.accountSid = MapUtils.getString(this.options, "accountSid");
        this.authToken = MapUtils.getString(this.options, "authToken");
        this.from = MapUtils.getString(this.options, "from");
        Twilio.init(this.accountSid, this.authToken);
    }

    @Override
    public boolean send(final Message message) {
        final com.twilio.rest.api.v2010.account.Message sms = com.twilio.rest.api.v2010.account.Message.creator(
                new PhoneNumber(StringUtils.join("+", message.getCountryCode(), message.getPhoneNumber())),
                new PhoneNumber(this.from),
                String.format("【%s】%s", message.getFromAlias(), message.getContent())
        ).create();

        if (sms.getErrorCode() != null) {
            log.error("Msg ID:[{}],Sender:{},Template:{},Receiver:{},failure,Error:{}",
                    message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber(), sms.getErrorMessage());
            return false;
        }

        log.debug("Msg ID:[{}],Sender:{},Template:{},Receiver:{},Success",
                message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber());
        return true;
    }
}
