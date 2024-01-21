package cc.newex.dax.extra.scheduler.service.impl;

import cc.newex.dax.extra.common.enums.push.PushChannel;
import cc.newex.dax.extra.common.enums.push.PushDataEnum;
import cc.newex.dax.extra.domain.cms.MessagePush;
import cc.newex.dax.extra.scheduler.service.MessagePushManagementService;
import cc.newex.dax.extra.service.admin.cms.MessagePushService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Message push management service.
 *
 * @author better
 * @date create in 2018/11/9 4:24 PM
 */
@Component
@Slf4j
public class MessagePushManagementServiceImpl implements MessagePushManagementService {

    private final MessagePushService messagePushService;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * Instantiates a new Message push management service.
     *
     * @param messagePushService  the message push service
     * @param stringRedisTemplate the redis template
     */
    @Autowired
    public MessagePushManagementServiceImpl(MessagePushService messagePushService, StringRedisTemplate stringRedisTemplate) {
        this.messagePushService = messagePushService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * Send message.
     */
    @Override
    public void sendMessage() {

        List<MessagePush> messages = messagePushService.listNotPushMessageAndPushTimeBeforeNow(LocalDateTime.now());

        if (ObjectUtils.isEmpty(messages)) {
            log.warn("Waiting for push message is => {}", messages);
            return;
        }
        PushDataEnum.PushData pushData = PushDataEnum.EXTRA_MESSAGE_MANAGEMENT.getPushData(messages);
        stringRedisTemplate.convertAndSend(PushChannel.MESSAGE_MANAGEMENT_PUSH_CHANNEL.getChannelName(), JSON.toJSONString(pushData));

        messages.forEach(item -> item.setStatus(MessagePushService.PUSH_STATUS));
        messagePushService.batchEdit(messages);
    }
}
