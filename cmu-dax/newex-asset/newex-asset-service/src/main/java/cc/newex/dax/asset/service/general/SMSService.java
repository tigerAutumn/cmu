package cc.newex.dax.asset.service.general;

import cc.newex.commons.dictionary.consts.BrokerIdConsts;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class SMSService {

    @Autowired
    private MessageClient messageClient;

    @Autowired
    private UsersClient usersClient;

    public enum SMSType {
        /**
         * 短信
         */
        SMS,
        /**
         * 邮件
         */
        MAIL
    }

    public void sendSMSOrMail(JSONObject content, String template, Long userId, Integer brokerId) {
        try {
            UserInfoResDTO data = this.usersClient.getUserInfo(userId, BrokerIdConsts.COIN_MEX).getData();
            if (!StringUtils.isEmpty(data.getMobile())) {
                this.sendSMSOrMail(content, SMSType.SMS, template, data.getMobile(), brokerId);
            } else if (!StringUtils.isEmpty(data.getEmail())) {
                this.sendSMSOrMail(content, SMSType.MAIL, template, data.getEmail(), brokerId);
            }
        } catch (Throwable e) {
            log.error("发送短信获取用户信息失败userId={}", userId, e);
        }
    }

    public void sendSMSOrMail(JSONObject content, SMSType type, String template, Long userId) {
        try {
            UserInfoResDTO data = this.usersClient.getUserInfo(userId, BrokerIdConsts.COIN_MEX).getData();
            if (type.equals(SMSType.SMS)) {
                this.sendSMSOrMail(content, type, template, data.getMobile(), null);
            } else if (type.equals(SMSType.MAIL)) {
                this.sendSMSOrMail(content, type, template, data.getEmail(), null);
            }
        } catch (Throwable e) {
            log.error("发送短信获取用户信息失败userId={}", userId, e);
        }
    }

    public void sendSMSOrMail(JSONObject content, SMSType type, String template, String target, Integer brokerId) {
        if (StringUtils.isEmpty(content)) {
            return;
        }

        MessageReqDTO messageReqDTO = MessageReqDTO.builder()
                .countryCode(template)
                .locale("zh-cn")
                .templateCode(template)
                .params(content.toJSONString())
                .build();
        if (!ObjectUtils.isEmpty(brokerId)) {
            messageReqDTO.setBrokerId(brokerId);
        }
        if (type.equals(SMSType.SMS)) {
            messageReqDTO.setMobile(target);
        } else if (type.equals(SMSType.MAIL)) {
            messageReqDTO.setEmail(target);
        }

        try {
            ResponseResult<String> send = this.messageClient.send(messageReqDTO);
            log.info("messageReqDTO={}，发送状态{}", messageReqDTO, send.getMsg());
        } catch (Throwable e) {
            log.error("发送短信失败messageReqDTO={}", messageReqDTO, e);
        }
    }
}
