package cc.newex.dax.integration.service.msg.provider.sms;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.service.msg.provider.MessageProvider;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;

/**
 * author: lingqing.wan
 * date: 2018-03-27 上午11:24
 */
@Slf4j
public class AliyunSmsProvider
        extends AbstractSmsProvider implements MessageProvider {

    private String product;
    private String domain;
    private String key;
    private String secret;

    private IAcsClient acsClient;
    private Map<String, Object> options;

    public AliyunSmsProvider() {
    }

    private void build() {
        final IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", this.key, this.secret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", this.product, this.domain);
        } catch (final ClientException e) {
            throw new RuntimeException(e);
        }
        this.acsClient = new DefaultAcsClient(profile);
    }

    @Override
    public String getName() {
        return "aliyun-sms";
    }

    @Override
    public void setOptions(final Map<String, Object> options) {
        this.options = options;
        this.product = MapUtils.getString(options, "product");
        this.domain = MapUtils.getString(options, "domain");
        this.key = MapUtils.getString(options, "key");
        this.secret = MapUtils.getString(options, "secret");
        this.build();
    }

    @Override
    public boolean send(final Message message) {
        final SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(message.getPhoneNumber());
        request.setSignName(message.getFromAlias());
        request.setTemplateCode(message.getChannelTplCode());
        request.setTemplateParam(message.getTemplateParams());
        request.setHttpContent(StringUtils.getBytesUtf8(message.getContent()), "utf-8", FormatType.JSON);
        final SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = this.acsClient.getAcsResponse(request);
        } catch (final ClientException e) {
            log.error("Msg ID:[{}],Sender:{},Template:{},Receiver:{},failure,Error:{}",
                    message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber(), e.getMessage());
            return false;
        }
        if (sendSmsResponse.getCode() == null || !sendSmsResponse.getCode().equals("OK")) {
            log.error("Msg ID:[{}],Sender:{},Template:{},Receiver:{},failure,Response Code:{}",
                    message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber(), sendSmsResponse.getCode());
            return false;
        }

        log.debug("Msg ID:[{}],Sender:{},Template:{},Receiver:{},Success",
                message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber());
        return true;
    }
}
