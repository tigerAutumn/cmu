package com.xxl.job.admin.service.impl;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xxl.job.admin.config.CommonProperties;
import com.xxl.job.admin.consts.Constants;
import com.xxl.job.admin.consts.OperationEnum;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.dao.XxlJobInfoDao;
import com.xxl.job.admin.service.MsgSendService;
import com.xxl.job.admin.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Locale;
import java.util.Map;

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
    private static String ALERT_MSG_TMP = "任务调度:%s于%s-%s-%s-负责人[%s]";

    @Resource
    private XxlJobInfoDao xxlJobInfoDao;


    @Override
    public void sendSMS(int id, XxlJobUser xxlJobUser, OperationEnum operationEnum) {
        if (this.isProd()) {
            final Map<String, Object> map = Maps.newHashMapWithExpectedSize(1);
            XxlJobInfo xxlJobInfo = xxlJobInfoDao.loadById(id);
            if (xxlJobInfo == null) {
                log.error("error xxlJobInfo is null");
                return;
            }
            String msg = String.format(ALERT_MSG_TMP, xxlJobUser.getUserName(), DateUtil.getFormatDate(), operationEnum.name, xxlJobInfo.getJobDesc(), xxlJobInfo.getAuthor());
            map.put("msg", msg);

            //发送短信
            final MessageReqDTO messageReqDTO = MessageReqDTO.builder()
                    .countryCode(Constants.SMS_SEND_AREA)
                    .locale(Locale.SIMPLIFIED_CHINESE.toLanguageTag())
                    .mobile(commonProperties.getAdminPhone())
                    .templateCode(MessageTemplateConsts.SMS_SYSTEM_XXL_WARN)
                    .params(JSON.toJSONString(map))
                    .build();
            final ResponseResult<String> smsSendResultApiResult = this.messageClient.send(messageReqDTO);
            log.info("code:{} msg:{} ", smsSendResultApiResult.getCode(), smsSendResultApiResult.getMsg());
        }
    }


    @Override
    public void sendSMS(String msg) {
        if (this.isProd()) {
            final Map<String, Object> map = Maps.newHashMapWithExpectedSize(1);
            map.put("msg", msg);
            //发送短信
            final MessageReqDTO messageReqDTO = MessageReqDTO.builder()
                    .countryCode(Constants.SMS_SEND_AREA)
                    .locale(Locale.SIMPLIFIED_CHINESE.toLanguageTag())
                    .mobile(commonProperties.getAdminPhone())
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
        //System.out.println(IpUtil.getIP());
        return "prod".equalsIgnoreCase(commonProperties.getEvn());
    }

}
