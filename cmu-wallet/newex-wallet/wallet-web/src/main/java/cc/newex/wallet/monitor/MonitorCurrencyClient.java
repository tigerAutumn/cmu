package cc.newex.wallet.monitor;

import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.currency.CurrencyOnlineEnum;
import cc.newex.wallet.pojo.BestBlockHeight;
import cc.newex.wallet.service.BestBlockHeightService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author newex-team
 * @data 2018/5/2
 */
@Slf4j
@Component
public class MonitorCurrencyClient {

    public static final String COUNTRY_CODE = "86";

    @Autowired
    private BestBlockHeightService blockHeightService;

    @Autowired
    private MessageClient messageClient;

    @Value("${newex.warning.contacts}")
    private String[] contacts;

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void currencyClientMonitor() {
        log.info("check currency client begin");

        try {
            final List<BestBlockHeight> heightList = this.blockHeightService.getAll();

            heightList.forEach((blockHeight) -> {
                final CurrencyEnum currency = CurrencyEnum.parseValue(blockHeight.getCurrency());

                final int online = currency.getOnline();
                final Long interval = blockHeight.getIntervalTime();

                if (online == CurrencyOnlineEnum.OFFLINE.getCode() || interval < 0) {
                    log.info("not check currency height, currency: {}, online: {}, interval: {}", blockHeight.getCurrency(), online, interval);

                    return;
                }

                final Long lastUpdateTime = blockHeight.getUpdateDate().getTime();
                final Long now = System.currentTimeMillis();

                if (lastUpdateTime + blockHeight.getIntervalTime() < now) {
                    // ${currency} 区块最后更新时间: ${updateTime}, 高度: ${height}, 请及时查看。
                    final JSONObject params = new JSONObject();

                    params.put("currency", currency.getName());
                    params.put("updateTime", DateFormatUtils.format(blockHeight.getUpdateDate(), "yyyy-MM-dd'T'HH:mm:ss"));
                    params.put("height", String.valueOf(blockHeight.getHeight()));

                    final String paramsJson = params.toJSONString();

                    log.error("currency block not update: {}", paramsJson);

                    for (final String contact : this.contacts) {
                        final MessageReqDTO messageReqDTO = MessageReqDTO.builder()
                                .countryCode(MonitorCurrencyClient.COUNTRY_CODE)
                                .locale("zh-cn")
                                .mobile(contact)
                                .templateCode(MessageTemplateConsts.SMS_WALLET_CLIENT_WARNING_SUCCESS)
                                .params(paramsJson)
                                .build();

                        this.messageClient.send(messageReqDTO);
                    }
                }
            });

            log.info("check currency client end");
        } catch (final Throwable e) {
            log.error("check currency client error", e);
        }

    }

}
