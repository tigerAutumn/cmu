package cc.newex.dax.integration.service.msg.impl;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.msg.MessageExample;
import cc.newex.dax.integration.data.msg.MessageRepository;
import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.domain.msg.MessageTemplate;
import cc.newex.dax.integration.service.cache.AppCacheService;
import cc.newex.dax.integration.service.conf.BrokerSignConfigService;
import cc.newex.dax.integration.service.msg.MessageService;
import cc.newex.dax.integration.service.msg.enums.MessageStatusEnum;
import cc.newex.dax.integration.service.msg.enums.MessageTypeEnum;
import cc.newex.dax.integration.service.msg.provider.MessageSender;
import cc.newex.dax.integration.service.msg.resolver.MessageTemplateResolver;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 信息发送服务实现
 *
 * @author newex-team
 * @date 2018-05-10
 */
@Slf4j
@Service
public class MessageServiceImpl
        extends AbstractCrudService<MessageRepository, Message, MessageExample, Long>
        implements MessageService {

    /**
     * 最大重试次数
     */
    private static final int MAX_RETRY_TIMES = 3;
    /**
     * 线程安全无界队列
     */
    private final ConcurrentLinkedQueue<Message> MESSAGE_QUEUE = new ConcurrentLinkedQueue<>();

    @Resource
    private MessageRepository messageRepository;

    @Resource
    private AppCacheService appCacheService;

    @Resource
    private MessageSender messageSender;

    @Resource(name = "stringReplaceTemplateResolver")
    private MessageTemplateResolver templateResolver;

    @Resource
    private BrokerSignConfigService brokerSignConfigService;

    @PostConstruct
    public void loadInitData() {
        this.loadMessageToQueue();
    }

    @Override
    protected MessageExample getPageExample(final String fieldName, final String keyword) {
        final MessageExample example = new MessageExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public Queue<Message> getMessageQueue() {
        return this.MESSAGE_QUEUE;
    }

    @Override
    public void sendSmsAsync(final String templateCode, final String locale, final String countryCode, final String mobile, final String params, final Integer brokerId) {
        Preconditions.checkNotNull(templateCode, "templateCode is null");
        Preconditions.checkNotNull(mobile, "phoneNumber is null");
        Preconditions.checkNotNull(countryCode, "countryCode is null");
        Preconditions.checkNotNull(params, "template params is null");

        final String sign = this.brokerSignConfigService.takeSignByBroker(brokerId);

        final String[] phones = StringUtils.split(mobile, ',');

        for (final String phone : phones) {
            final Message message = Message.builder()
                    .brokerId(brokerId)
                    .templateCode(templateCode)
                    .phoneNumber(phone)
                    .countryCode(countryCode)
                    .templateParams(params)
                    .retryTimes(StringUtil.isEmail(phone) ? MessageStatusEnum.INVALID_SMS_MESSAGE.getValue() : 0)
                    .nextRetryTime(DateUtils.addSeconds(new Date(), -1))
                    .locale(StringUtils.defaultIfBlank(locale, Locale.US.toLanguageTag()).toLowerCase())
                    .createdDate(new Date())
                    .modifyDate(new Date())
                    .build();

            this.addMessage(message, sign);
        }
    }

    @Override
    public void sendEmailAsync(final String templateCode, final String locale, final String countryCode, final String toAddress, final String params, final Integer brokerId) {
        Preconditions.checkNotNull(templateCode, "templateCode is null");
        Preconditions.checkNotNull(toAddress, "toAddress is null");
        Preconditions.checkNotNull(params, "template params is null");

        final String sign = this.brokerSignConfigService.takeSignByBroker(brokerId);

        final String[] emails = StringUtils.split(toAddress, ',');

        for (final String email : emails) {
            final Message message = Message.builder()
                    .brokerId(brokerId)
                    .templateCode(templateCode)
                    .emailAddress(email)
                    .countryCode(countryCode)
                    .templateParams(params)
                    .retryTimes(StringUtil.isEmail(email) ? 0 : MessageStatusEnum.INVALID_EMAIL_MESSAGE.getValue())
                    .nextRetryTime(DateUtils.addSeconds(new Date(), -1))
                    .locale(StringUtils.defaultIfBlank(locale, Locale.US.toLanguageTag()).toLowerCase())
                    .createdDate(new Date())
                    .modifyDate(new Date())
                    .build();

            this.addMessage(message, sign);
        }
    }

    private void addMessage(final Message message, final String sign) {
        if (StringUtils.isBlank(sign)) {
            log.error("message sign is empty, message : {}", JSON.toJSONString(message));

            message.setRetryTimes(MessageStatusEnum.NOT_FOUND_SIGN.getValue());

            this.addMessage(message);

            return;
        }

        final MessageTemplate template = this.getMessageTemplate(message);

        //如果当前信息的模版不存在则直接不发送
        if (template == null) {
            log.warn("Locale: {}, Template: {} Not Found", message.getLocale(), message.getTemplateCode());

            message.setRetryTimes(MessageStatusEnum.NOT_FOUND_TEMPLATE.getValue());

            this.addMessage(message);

            return;
        }

        //解析消息模板并设置消息主题与内容及签名
        message.setFromAlias(sign);
        message.setSubject(template.getSubject());
        message.setContent(this.templateResolver.render(message, template));
        message.setType(this.getMessageType(message));

        this.addMessage(message);

        if (this.isInvalidMessage(message)) {
            this.updateMessage(message);
        } else {
            this.MESSAGE_QUEUE.add(message);
        }
    }

    private void addMessage(final Message message) {
        if (message == null) {
            return;
        }

        final Message message2 = Message.builder()
                .id(message.getId())
                .createdDate(message.getCreatedDate())
                .modifyDate(message.getModifyDate())
                .countryCode(message.getCountryCode())
                .phoneNumber(message.getPhoneNumber())
                .emailAddress(message.getEmailAddress())
                .fromAlias(message.getFromAlias())
                .templateCode(message.getTemplateCode())
                .templateParams(message.getTemplateParams())
                .subject(message.getSubject())
                .content(message.getContent())
                .isSent(message.isSent())
                .retryTimes(message.getRetryTimes())
                .nextRetryTime(message.getNextRetryTime())
                .locale(message.getLocale())
                .channelTplCode(message.getChannelTplCode())
                .type(message.getType())
                .brokerId(message.getBrokerId())
                .build();

        final String templateParams = message2.getTemplateParams();
        final String content = message2.getContent();

        final int len = 2000;

        if (StringUtils.isNotBlank(templateParams) && templateParams.length() > len) {
            message2.setTemplateParams(templateParams.substring(0, len));
        }

        if (StringUtils.isNotBlank(content) && content.length() > len) {
            message2.setContent(content.substring(0, len));
        }

        this.messageRepository.insert(message2);

        message.setId(message2.getId());
    }

    private void updateMessage(final Message message) {
        final Message message2 = Message.builder()
                .id(message.getId())
                .isSent(message.isSent())
                .retryTimes(message.getRetryTimes())
                .nextRetryTime(message.getNextRetryTime())
                .modifyDate(new Date())
                .build();

        this.messageRepository.updateById(message2);
    }

    private String getMessageType(final Message message) {
        return StringUtils.substringBefore(message.getTemplateCode(), "_");
    }

    @Override
    public List<Message> getOutboxMessages(final int offset, final int length) {
        //获取当半分钟之前的未处理的消息
        final Date halfMinutesBefore = DateUtils.addSeconds(new Date(), -30);
        return this.messageRepository.selectOutboxMessages(offset, length, halfMinutesBefore);
    }

    @Override
    public void send(final Message message) {
        log.debug("send message, 1, {}, ", message);

        //已经发送过或超过最大重试次数
        if (this.isSentOrExceedMaxRetryTimes(message)) {
            log.debug("send message, 2, {}, ", message);
            return;
        }

        //如果没到达重试时间则再次进入队列等待下次发送
        if (this.isNotTickNextRetryTime(message)) {
            log.debug("send message, 3, {}, ", message);
            this.MESSAGE_QUEUE.add(message);
            return;
        }

        log.info("send message, 4, {}, ", message);

        try {
            if (this.messageSender.send(message)) {
                log.debug("send message, 5, {}, ", message);
                message.setSent(true);
                this.updateMessage(message);
                log.debug("Msg ID:[{}],Template:{},Receiver:{},Message Send Success",
                        message.getId(), message.getTemplateCode(),
                        StringUtils.join(message.getEmailAddress(), message.getPhoneNumber()));
                return;
            }
        } catch (final Exception e) {
            log.error("Msg ID:[{}],Template:{},Receiver:{},Message Send Failure,Error:{}",
                    message.getId(), message.getTemplateCode(),
                    StringUtils.join(message.getEmailAddress(), message.getPhoneNumber()), e.getMessage());
            log.error("send message failed", e);
        }

        log.debug("send message, 6, {}, ", message);

        //进入重试队列
        this.entryNextRetry(message);
    }

    private void entryNextRetry(final Message message) {
        message.setRetryTimes(message.getRetryTimes() + 1);
        message.setNextRetryTime(new Date(System.currentTimeMillis() + message.getRetryTimes() * 2000));
        this.MESSAGE_QUEUE.add(message);
        this.updateMessage(message);
    }

    private void loadMessageToQueue() {
        final List<Message> messages = this.getOutboxMessages(0, 200);
        if (CollectionUtils.isNotEmpty(messages)) {
            for (final Message message : messages) {
                message.setType(this.getMessageType(message));
                this.MESSAGE_QUEUE.add(message);
            }
        }
    }

    private boolean isSentOrExceedMaxRetryTimes(final Message message) {
        return (message.isSent() || message.getRetryTimes() >= MessageServiceImpl.MAX_RETRY_TIMES);
    }

    private boolean isNotTickNextRetryTime(final Message message) {
        return (message.getNextRetryTime() != null &&
                message.getNextRetryTime().getTime() > System.currentTimeMillis());
    }

    private boolean isInvalidMessage(final Message message) {
        if (this.isInBlacklist(message)) {
            log.warn("Message Is In Blacklist！MsgID:{},type:{},number:{}",
                    message.getId(), message.getType(), StringUtils.join(message.getEmailAddress(), message.getPhoneNumber()));

            message.setRetryTimes(MessageStatusEnum.BLACK_LIST.getValue());

            return true;
        }

        //如果当前消息模板解析出错
        if (message.getRetryTimes() == MessageStatusEnum.MESSAGE_TEMPLATE_RENDER_ERROR.getValue()) {
            return true;
        }

        //如果是邮件模版，但不是合法的邮箱地址则过滤掉
        final boolean isInvalidEmail = (StringUtils.isEmpty(message.getEmailAddress()) && !StringUtil.isEmail(message.getEmailAddress()));
        if (StringUtils.equalsIgnoreCase(message.getType(), MessageTypeEnum.MAIL.getName()) && isInvalidEmail) {
            log.error("Message Is Invalid！MsgID:{},type:{},email:{}", message.getId(), message.getType(), message.getEmailAddress());
            message.setRetryTimes(MessageStatusEnum.INVALID_EMAIL_MESSAGE.getValue());
            return true;
        }

        //如果是短信模版，但不是合法的手机地址则过滤掉
        final boolean isInvalidMobile = (StringUtils.isEmpty(message.getPhoneNumber()) || StringUtil.isEmail(message.getPhoneNumber()));
        if (StringUtils.equalsIgnoreCase(message.getType(), MessageTypeEnum.SMS.getName()) && isInvalidMobile) {
            log.error("Message Is Invalid！MsgID:{},type:{},mobile:{}", message.getId(), message.getType(), message.getPhoneNumber());
            message.setRetryTimes(MessageStatusEnum.INVALID_SMS_MESSAGE.getValue());
            return true;
        }
        return false;
    }

    private MessageTemplate getMessageTemplate(final Message message) {
        MessageTemplate template = this.appCacheService.getMessageTemplate(message.getLocale(), message.getTemplateCode());
        if (template != null) {
            return template;
        }

        //如果没找到模版且当前语言不为英语
        //则查找默认语言(en-us)的模版
        final String locale = Locale.US.toLanguageTag().toLowerCase();

        if (StringUtil.notEqualsIgnoreCaseWithTrim(message.getLocale(), locale)) {
            template = this.appCacheService.getMessageTemplate(locale, message.getTemplateCode());
        }

        return template;
    }

    private boolean isInBlacklist(final Message message) {
        if (StringUtils.equalsIgnoreCase(message.getType(), MessageTypeEnum.MAIL.getName())) {
            return this.appCacheService.isInMessageBlacklist(message.getType(), message.getEmailAddress(), message.getBrokerId());
        }

        if (StringUtils.equalsIgnoreCase(message.getType(), MessageTypeEnum.SMS.getName())) {
            return this.appCacheService.isInMessageBlacklist(message.getType(), message.getPhoneNumber(), message.getBrokerId());
        }

        return false;
    }

}
