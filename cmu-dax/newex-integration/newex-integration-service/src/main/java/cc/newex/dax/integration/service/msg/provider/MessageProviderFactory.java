package cc.newex.dax.integration.service.msg.provider;

import cc.newex.dax.integration.domain.conf.ProviderConf;
import cc.newex.dax.integration.service.conf.ProviderConfService;
import cc.newex.dax.integration.service.conf.enums.RegionEnum;
import cc.newex.dax.integration.service.msg.enums.MessageTypeEnum;
import cc.newex.dax.integration.service.msg.enums.ProviderNameEnum;
import cc.newex.dax.integration.service.msg.provider.email.AliyunEmailProvider;
import cc.newex.dax.integration.service.msg.provider.email.CndnsEmailProvider;
import cc.newex.dax.integration.service.msg.provider.email.ExmailQQEmailProvider;
import cc.newex.dax.integration.service.msg.provider.sms.Net3tongSmsProvider;
import cc.newex.dax.integration.service.msg.provider.sms.NexmoSmsProvider;
import cc.newex.dax.integration.service.msg.provider.sms.TwilioSmsProvider;
import cc.newex.dax.integration.service.msg.provider.sms.ZZ253SmsProvider;
import cc.newex.dax.integration.service.msg.provider.sms.ZZ253ZhSmsProvider;
import cc.newex.dax.integration.service.msg.provider.sms.emay.EmaySmsProvider;
import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @date 2018-05-09
 */
@Slf4j
@Component
public class MessageProviderFactory {

    private final LoadingCache<RegionEnum, List<MessageProvider>> smsProviderCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<RegionEnum, List<MessageProvider>>() {
                        @Override
                        public List<MessageProvider> load(final RegionEnum region) throws Exception {
                            final MessageTypeEnum messageType = MessageTypeEnum.SMS;

                            return MessageProviderFactory.this.getMessageProviders(messageType, region);
                        }
                    }
            );

    private final LoadingCache<RegionEnum, List<MessageProvider>> emailProviderCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<RegionEnum, List<MessageProvider>>() {
                        @Override
                        public List<MessageProvider> load(final RegionEnum region) throws Exception {
                            final MessageTypeEnum messageType = MessageTypeEnum.MAIL;

                            return MessageProviderFactory.this.getMessageProviders(messageType, region);
                        }
                    }
            );

    private final UnknowMessageProvider unknowMessageProvider;
    private final ProviderConfService providerConfService;

    public MessageProviderFactory(final UnknowMessageProvider unknowMessageProvider, final ProviderConfService providerConfService) {
        this.unknowMessageProvider = unknowMessageProvider;
        this.providerConfService = providerConfService;
    }

    private List<MessageProvider> getMessageProviders(final MessageTypeEnum messageType, final RegionEnum region) {
        final List<ProviderConf> providerConfList = this.providerConfService.getByType(messageType.getName(), region.getName());

        final List<MessageProvider> messageProviderList = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(providerConfList)) {
            for (final ProviderConf conf : providerConfList) {
                final MessageProvider provider = this.convert(conf);

                if (provider != null) {
                    messageProviderList.add(provider);
                }
            }
        }

        this.sortProvidersByWeight(messageProviderList);

        return messageProviderList;
    }

    private MessageProvider convert(final ProviderConf conf) {
        log.info("========ProviderConf: {}======", conf);

        final MessageProvider provider = this.createMessageProvider(conf.getName());
        if (provider == null) {
            log.warn("========provider name: {} not found======", conf.getName());
            return null;
        }

        provider.setOptions(JSON.parseObject(conf.getOptions()));
        provider.setWeight(conf.getWeight());
        provider.setBrokerId(conf.getBrokerId());

        return provider;
    }

    public List<MessageProvider> getMessageProviders(final String messageType, final String countryCode, final Integer brokerId) {
        final RegionEnum region = StringUtils.equalsIgnoreCase(StringUtils.trim(countryCode), "86") ?
                RegionEnum.CHINA : RegionEnum.INTERNATIONAL;

        final String type = StringUtils.trim(messageType);

        List<MessageProvider> messageProviderList = null;

        if (MessageTypeEnum.SMS.getName().equalsIgnoreCase(type)) {
            messageProviderList = this.getSmsProviders(region, brokerId);
        }

        if (MessageTypeEnum.MAIL.getName().equalsIgnoreCase(type)) {
            messageProviderList = this.getMailProviders(region, brokerId);
        }

        if (CollectionUtils.isNotEmpty(messageProviderList)) {
            return messageProviderList;
        }

        log.error("no message provider, messageType: {}, countryCode: {}, brokerId: {}", messageType, countryCode, brokerId);

        return Lists.newArrayList(this.unknowMessageProvider);
    }

    private List<MessageProvider> getSmsProviders(final RegionEnum region, final Integer brokerId) {
        List<MessageProvider> messageProviderList = this.getMessageProviders(this.smsProviderCache, region, brokerId);

        if (CollectionUtils.isEmpty(messageProviderList)) {
            messageProviderList = this.getMessageProviders(this.smsProviderCache, RegionEnum.ALL, brokerId);
        }

        return messageProviderList;
    }

    private List<MessageProvider> getMailProviders(final RegionEnum region, final Integer brokerId) {
        List<MessageProvider> messageProviderList = this.getMessageProviders(this.emailProviderCache, region, brokerId);

        if (CollectionUtils.isEmpty(messageProviderList)) {
            messageProviderList = this.getMessageProviders(this.emailProviderCache, RegionEnum.ALL, brokerId);
        }

        return messageProviderList;
    }

    private List<MessageProvider> getMessageProviders(final LoadingCache<RegionEnum, List<MessageProvider>> providerCache, final RegionEnum region, final Integer brokerId) {
        List<MessageProvider> messageProviderList = providerCache.getUnchecked(region);

        if (CollectionUtils.isNotEmpty(messageProviderList)) {
            messageProviderList = messageProviderList.stream()
                    .filter(provider -> Objects.equals(brokerId, provider.getBrokerId()))
                    .collect(Collectors.toList());
        }

        return messageProviderList;
    }

    public MessageProvider createMessageProvider(final String name) {
        if (StringUtils.equalsIgnoreCase(name, ProviderNameEnum.ALIYUN_EMAIL.getName())) {
            return new AliyunEmailProvider();
        }
        if (StringUtils.equalsIgnoreCase(name, ProviderNameEnum.CNDNS_EMAIL.getName())) {
            return new CndnsEmailProvider();
        }
        if (StringUtils.equalsIgnoreCase(name, ProviderNameEnum.QQ_EMAIL.getName())) {
            return new ExmailQQEmailProvider();
        }
        if (StringUtils.equalsIgnoreCase(name, ProviderNameEnum.ZZ253_SMS.getName())) {
            return new ZZ253SmsProvider();
        }
        if (StringUtils.equalsIgnoreCase(name, ProviderNameEnum.ZZ253ZH_SMS.getName())) {
            return new ZZ253ZhSmsProvider();
        }
        if (StringUtils.equalsIgnoreCase(name, ProviderNameEnum.TWILIO_SMS.getName())) {
            return new TwilioSmsProvider();
        }
        if (StringUtils.equalsIgnoreCase(name, ProviderNameEnum.EMAY_SMS.getName())) {
            return new EmaySmsProvider();
        }
        if (StringUtils.equalsIgnoreCase(name, ProviderNameEnum.NEXMO_SMS.getName())) {
            return new NexmoSmsProvider();
        }
        if (StringUtils.equalsIgnoreCase(name, ProviderNameEnum.NET3TONG_SMS.getName())) {
            return new Net3tongSmsProvider();
        }
        return null;
    }

    public void sortProvidersByWeight(final List<MessageProvider> messageProviderList) {
        if (CollectionUtils.isEmpty(messageProviderList)) {
            return;
        }

        messageProviderList.sort(Comparator.comparing(MessageProvider::getWeight));
    }
}
