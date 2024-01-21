package cc.newex.dax.users.service.security.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.token.EmailTokenUtils;
import cc.newex.dax.users.common.util.DateUtils;
import cc.newex.dax.users.common.util.MobileUtils;
import cc.newex.dax.users.common.util.VerificationCodeUtils;
import cc.newex.dax.users.criteria.UserNoticeEventExample;
import cc.newex.dax.users.data.UserNoticeEventRepository;
import cc.newex.dax.users.domain.UserNoticeEvent;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 信息发送记录 服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class UserNoticeEventServiceImpl
        extends AbstractCrudService<UserNoticeEventRepository, UserNoticeEvent, UserNoticeEventExample, Long>
        implements UserNoticeEventService {

    @Autowired
    private UserNoticeEventRepository userNoticeRecordRepos;

    @Autowired
    private MessageClient messageClient;

    @Override
    protected UserNoticeEventExample getPageExample(final String fieldName, final String keyword) {
        final UserNoticeEventExample example = new UserNoticeEventExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public boolean sendEmail(final String locale, final BehaviorTheme behavior, final int business, final String target,
                             final String templateCode, final Long userId, final Map<String, Object> params, final Integer brokerId) {

        UserNoticeEvent userNoticeEvent = this.getByBehavior(userId, NoticeSendLogConsts.CHANNEL_EMAIL, behavior, target);

        int result = -1;

        if (userNoticeEvent != null) {
            //如果过了3分钟
            if (DateUtils.isExpired(userNoticeEvent.getUpdatedDate())) {
                result = this.updateStatus(userNoticeEvent);

                if (result <= 0) {
                    log.error("UserNoticeEventServiceImpl sendEmail, emailCode OVERDUE and update status falied!");
                    return false;
                }

                userNoticeEvent = null;
            } else {
                result = this.updateParams(userNoticeEvent, params);

                if (result <= 0) {
                    log.error("UserNoticeEventServiceImpl sendEmail, update userNoticeEvent falied!");
                    return false;
                }
            }

        }

        if (userNoticeEvent == null) {
            final String token = EmailTokenUtils.createEmailToken(userId, behavior.getBehavior(), 30);

            result = this.add(userId, NoticeSendLogConsts.CHANNEL_EMAIL, behavior, business, target, params, token);

            if (result <= 0) {
                log.error("UserNoticeEventServiceImpl sendEmail, save userNoticeEvent falied!");
                return false;
            }

        }

        return this.send(userNoticeEvent, null, locale, templateCode, target, null, params, brokerId);
    }

    private boolean send(final UserNoticeEvent userNoticeEvent, final String countryCode, final String locale, final String templateCode,
                         final String email, final String mobile, final Map<String, Object> params, final Integer brokerId) {
        // 邮件发送
        final MessageReqDTO messageReq = MessageReqDTO.builder()
                .templateCode(templateCode)
                .countryCode(countryCode)
                .locale(locale.toLowerCase())
                .email(email)
                .mobile(mobile)
                .params(JSON.toJSONString(params))
                .brokerId(brokerId)
                .build();

        final ResponseResult responseResult = this.messageClient.send(messageReq);

        log.info("send msg, req: {}, result: {}", JSON.toJSONString(messageReq), JSON.toJSONString(responseResult));

        return (responseResult != null && responseResult.getCode() == 0);
    }

    private UserNoticeEvent getByBehavior(final Long userId, final Integer channel, final BehaviorTheme behavior, final String target) {
        final UserNoticeEventExample example = new UserNoticeEventExample();
        final UserNoticeEventExample.Criteria criteria = example.createCriteria()
                .andBehaviorIdEqualTo(behavior.getBehavior())
                .andChannelEqualTo(channel)
                .andTypeEqualTo(NoticeSendLogConsts.BUSINESS_CODE)
                .andStatusEqualTo(NoticeSendLogConsts.STATUS_NEW)
                .andTargetEqualTo(target);

        if (userId != null && userId > 0) {
            criteria.andUserIdEqualTo(userId);
        }

        example.setOrderByClause(" id desc");

        return this.userNoticeRecordRepos.selectOneByExample(example);
    }

    private int updateStatus(final UserNoticeEvent userNoticeEvent) {
        userNoticeEvent.setStatus(NoticeSendLogConsts.STATUS_OVERDUE);

        return this.userNoticeRecordRepos.updateById(userNoticeEvent);
    }

    private int updateParams(final UserNoticeEvent userNoticeEvent, final Map<String, Object> params) {
        final JSONObject jsonObject = JSONObject.parseObject(userNoticeEvent.getParams());

        //处理提现逻辑
        if (userNoticeEvent.getParams().contains("coin")) {
            jsonObject.put("coin", params.get("coin"));
            jsonObject.put("toAddress", params.get("toAddress"));
        }

        params.putAll(JSON.parseObject(jsonObject.toJSONString()));

        userNoticeEvent.setParams(JSON.toJSONString(params));
        userNoticeEvent.setUpdatedDate(new Date());

        return this.userNoticeRecordRepos.updateById(userNoticeEvent);
    }

    private int add(final Long userId, final Integer channel, final BehaviorTheme behavior, final int business,
                    final String target, final Map<String, Object> params, final String token) {
        if (business == NoticeSendLogConsts.BUSINESS_CODE) {
            params.put("code", VerificationCodeUtils.getRandNumber(6));
        }

        final UserNoticeEvent userNoticeEvent = UserNoticeEvent.builder()
                .token(token)
                .status(NoticeSendLogConsts.STATUS_NEW)
                .channel(channel)
                .target(target)
                .behaviorId(behavior.getBehavior())
                .userId(userId)
                .type(business)
                .params(JSON.toJSONString(params))
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        return this.userNoticeRecordRepos.insert(userNoticeEvent);
    }

    @Override
    public boolean sendSMS(final String locale, final BehaviorTheme behavior, final int business, final String mobile,
                           final int areaCode, final String templateCode, final Long userId, final Map<String, Object> params, final Integer brokerId) {
        // 验证手机号码是否正确
        if (!MobileUtils.checkPhoneNumber(mobile, String.valueOf(areaCode))) {
            log.error("sendSMS, send falied! mobile format illegal. userId={}, mobile={}", userId, mobile);
            return false;
        }

        final String target = areaCode + mobile;

        UserNoticeEvent userNoticeEvent = this.getByBehavior(userId, NoticeSendLogConsts.CHANNEL_PHONE, behavior, target);

        int result = -1;

        if (userNoticeEvent != null) {
            //如果过了3分钟
            if (DateUtils.isExpired(userNoticeEvent.getUpdatedDate())) {
                result = this.updateStatus(userNoticeEvent);

                if (result <= 0) {
                    log.error("UserNoticeEventServiceImpl sendSMS, msgCode OVERDUE and update status falied!");
                    return false;
                }

                userNoticeEvent = null;
            } else {
                result = this.updateParams(userNoticeEvent, params);

                if (result <= 0) {
                    log.error("UserNoticeEventServiceImpl sendSMS, update userNoticeEvent falied!");
                    return false;
                }
            }

        }

        if (userNoticeEvent == null) {
            final String token = EmailTokenUtils.createEmailToken(userId, behavior.getBehavior(), -1);

            result = this.add(userId, NoticeSendLogConsts.CHANNEL_PHONE, behavior, business, target, params, token);

            if (result <= 0) {
                log.error("UserNoticeEventServiceImpl sendSMS, save userNoticeEvent falied!");
                return false;
            }

        }

        return this.send(userNoticeEvent, String.valueOf(areaCode), locale, templateCode, null, mobile, params, brokerId);
    }

}