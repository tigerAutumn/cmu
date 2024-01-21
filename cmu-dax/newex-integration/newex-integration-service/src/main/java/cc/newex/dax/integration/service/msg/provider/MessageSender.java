package cc.newex.dax.integration.service.msg.provider;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.domain.msg.MessageSendStatusDetail;
import cc.newex.dax.integration.service.conf.enums.RegionEnum;
import cc.newex.dax.integration.service.msg.MessageSendStatusDetailService;
import cc.newex.dax.integration.service.msg.enums.MessageStatusEnum;
import cc.newex.dax.integration.service.msg.enums.MessageTypeEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱与短信发送器
 *
 * @author newex-team
 * @date 2018-07-30
 */
@Slf4j
@Component
public class MessageSender {

    @Autowired
    private MessageProviderFactory messageProviderFactory;

    @Autowired
    private MessageSendStatusDetailService messageSendStatusDetailService;

    public boolean send(final Message message) {
        final List<MessageProvider> providers = this.messageProviderFactory.getMessageProviders(message.getType(),
                message.getCountryCode(), message.getBrokerId());

        if (CollectionUtils.isEmpty(providers)) {
            log.error("no providers, type: {}, countryCode: {}, brokerId: {}", message.getType(), message.getCountryCode(), message.getBrokerId());
            return false;
        }

        // providers = this.sortByEmailSuffix(message, providers);

        for (final MessageProvider provider : providers) {
            log.info("messageId : {}, messageType: {}, provider: {}", message.getId(), message.getType(), JSON.toJSON(provider));

            final boolean result = provider.send(message);
            this.saveMessageSendStatus(message, provider.getName(), result);
            if (result) {
                return true;
            }
        }

        return false;
    }

    /**
     * 优先使用相同域名的服务器发送邮件
     *
     * @param message
     * @param providers
     */
    private List<MessageProvider> sortByEmailSuffix(final Message message, final List<MessageProvider> providers) {
        final String type = StringUtils.trim(message.getType());
        if (!MessageTypeEnum.MAIL.getName().equalsIgnoreCase(type)) {
            return providers;
        }

        final String emailAddress = message.getEmailAddress();
        if (StringUtils.isBlank(emailAddress)) {
            return providers;
        }

        final String suffix = emailAddress.split("@", 2)[1];
        if (StringUtils.isBlank(suffix)) {
            return providers;
        }

        final List<MessageProvider> providerNewList = new ArrayList<>();

        final int size = providers.size();
        int count = 0;

        for (int i = 0; i < size; i++) {
            final MessageProvider provider = providers.get(i);
            final String username = MapUtils.getString(provider.getOptions(), "username");

            if (username.endsWith(suffix)) {
                providerNewList.add(count++, provider);
            } else {
                providerNewList.add(provider);

            }
        }

        return providerNewList;
    }

    /**
     * 记录当前消息发送状态用于统计分析各渠道的成功率
     *
     * @param message
     * @param channel
     * @param result
     */
    private void saveMessageSendStatus(final Message message, final String channel, final boolean result) {
        final MessageSendStatusDetail sendStatusDetail = MessageSendStatusDetail.builder()
                .brokerId(message.getBrokerId())
                .msgId(message.getId())
                .channel(channel)
                .type(message.getType())
                .status(result ?
                        MessageStatusEnum.SUCCESS.getValue() :
                        MessageStatusEnum.FAILURE.getValue())
                .region(StringUtils.equalsIgnoreCase(StringUtils.trim(message.getCountryCode()), "86") ?
                        RegionEnum.CHINA.getName() :
                        RegionEnum.INTERNATIONAL.getName()
                ).build();
        this.messageSendStatusDetailService.add(sendStatusDetail);
    }
}
