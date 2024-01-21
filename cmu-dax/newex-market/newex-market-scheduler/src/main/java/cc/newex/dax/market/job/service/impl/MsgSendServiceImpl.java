package cc.newex.dax.market.job.service.impl;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.market.job.config.CommonProperties;
import cc.newex.dax.market.job.service.MsgSendService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Locale;
import java.util.Map;

import static cc.newex.dax.market.common.consts.IndexConst.SMS_SEND_AREA;

/**
 * @author minhan
 * @Date: 2018/7/19 15:32
 * @Description:
 */
@Service("msgSendService")
@Slf4j
public class MsgSendServiceImpl implements MsgSendService {

    @Resource
    private MessageClient messageClient;
    @Resource
    private CommonProperties commonProperties;



    @Override
    public void sendSMS(String msg,String phones) {
        if (this.isProd()) {
            final Map<String, Object> map = Maps.newHashMapWithExpectedSize(1);
            map.put("msg", msg);
            //发送短信
            final MessageReqDTO messageReqDTO = MessageReqDTO.builder()
                    .countryCode(SMS_SEND_AREA)
                    .locale(Locale.SIMPLIFIED_CHINESE.toLanguageTag())
                    .mobile(phones)
                    .templateCode(MessageTemplateConsts.SMS_SYSTEM_XXL_WARN)
                    .params(JSON.toJSONString(map))
                    .build();
            final ResponseResult<String> smsSendResultApiResult = this.messageClient.send(messageReqDTO);
            log.info("code:{} msg:{} ", smsSendResultApiResult.getCode(), smsSendResultApiResult.getMsg());
        }
    }

    @Override
    public void sendMail(String mails, String msg) {
        if (this.isProd()) {
            final Map<String, Object> map = Maps.newHashMapWithExpectedSize(1);
            map.put("msg", msg);
            //发送短信
            final MessageReqDTO messageReqDTO = MessageReqDTO.builder()
                    .templateCode(MessageTemplateConsts.MAIL_SYSTEM_XXL_WARN)
                    .locale(Locale.SIMPLIFIED_CHINESE.toLanguageTag())
                    .email(mails)
                    .params(JSON.toJSONString(map))
                    .build();
            final ResponseResult<String> smsSendResultApiResult = this.messageClient.send(messageReqDTO);
            log.info("code:{} msg:{} ", smsSendResultApiResult.getCode(), smsSendResultApiResult.getMsg());
        }
    }

    private boolean isProd() {
        return "prod".equalsIgnoreCase(commonProperties.getEvn());
    }

}
