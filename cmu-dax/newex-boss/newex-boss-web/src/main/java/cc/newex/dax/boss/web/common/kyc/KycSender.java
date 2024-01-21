package cc.newex.dax.boss.web.common.kyc;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.integration.client.MessageClient;
import cc.newex.dax.integration.dto.message.MessageReqDTO;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author liutiejun
 * @date 2018-07-27
 */
@Slf4j
@Component
public class KycSender {

    @Autowired
    private MessageClient messageClient;

    /**
     * 发送邮件或者短信
     * <ul>
     * <li>1、国内，优先发短信</li>
     * <li>2、国外，优先发邮件</li>
     * </ul>
     *
     * @param userInfo
     * @param countryCode
     * @param reason
     * @param pass           1-审核通过，0-审核不通过
     * @param responseResult
     * @return
     */
    public ResponseResult<String> sendMessage(final UserInfoResDTO userInfo, final String countryCode,
                                              final String reason, final Integer pass, final ResponseResult responseResult) {
        if (responseResult == null || responseResult.getCode() != 0 || userInfo == null) {
            return null;
        }

        final String mobile = userInfo.getMobile();
        final String email = userInfo.getEmail();

        if (StringUtils.isBlank(mobile) && StringUtils.isBlank(email)) {
            log.warn("mobile and email is null");

            return null;
        }

        final String locale = this.getLocale(countryCode);

        final String templateCode = this.getTemplateCode(countryCode, pass, mobile, email);

        final String countryCallingCode = userInfo.getAreaCode();

        final String params = this.getParams(reason, pass);

        final MessageReqDTO messageReq = MessageReqDTO.builder()
                .templateCode(templateCode)
                .locale(locale)
                .countryCode(countryCallingCode)
                .mobile(mobile)
                .email(email)
                .params(params)
                .build();

        final ResponseResult<String> sendResult = this.messageClient.send(messageReq);

        return sendResult;
    }

    /**
     * 生成发送短信、邮件的参数，json格式字符串
     *
     * @param reason
     * @param pass
     * @return
     */
    private String getParams(final String reason, final Integer pass) {
        if (StringUtils.isBlank(reason) || pass.equals(1)) {
            return "{}";
        }

        final Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("reason", reason);

        final String params = new Gson().toJson(paramsMap);

        return params;
    }

    private String getLocale(final String countryCode) {
        String locale = Locale.US.toLanguageTag();

        if ("156".equals(countryCode)) {
            locale = Locale.CHINA.toLanguageTag();
        }

        return locale;
    }

    private static final String MOBILE_REG = "\\d+";
    private static final String EMAIL_REG = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 获取模板编码
     *
     * @param countryCode 国家码
     * @param pass        1-审核通过，0-审核不通过
     * @param mobile      国外用户优先发邮件
     * @param email       国内用户有限发短信
     * @return
     */
    private String getTemplateCode(final String countryCode, final Integer pass, final String mobile, final String email) {
        if (StringUtils.isBlank(mobile) && StringUtils.isBlank(email)) {
            return null;
        }

        if ("156".equals(countryCode)) {
            return this.getChinaTemplateCode(pass, mobile, email);
        } else {
            return this.getOtherTemplateCode(pass, mobile, email);
        }

    }

    /**
     * 国内，优先发短信
     *
     * @param pass
     * @param mobile
     * @param email
     * @return
     */
    private String getChinaTemplateCode(final Integer pass, final String mobile, final String email) {
        String templateCode = this.getSmsTemplateCode(pass, mobile);

        if (templateCode == null) {
            templateCode = this.getMailTemplateCode(pass, email);
        }

        return templateCode;
    }

    /**
     * 国外，优先发邮件
     *
     * @param pass
     * @param mobile
     * @param email
     * @return
     */
    private String getOtherTemplateCode(final Integer pass, final String mobile, final String email) {
        String templateCode = this.getMailTemplateCode(pass, email);

        if (templateCode == null) {
            templateCode = this.getSmsTemplateCode(pass, mobile);
        }

        return templateCode;
    }

    private String getSmsTemplateCode(final Integer pass, final String mobile) {
        if (StringUtils.isBlank(mobile) || !mobile.matches(MOBILE_REG)) {
            return null;
        }

        if (pass.equals(1)) {
            // 审核通过
            return "SMS_KYC_BOSS_PASS";
        } else {
            // 审核拒绝
            return "SMS_KYC_BOSS_UNPASS";
        }
    }

    private String getMailTemplateCode(final Integer pass, final String email) {
        if (StringUtils.isBlank(email) || !email.matches(EMAIL_REG)) {
            return null;
        }

        if (pass.equals(1)) {
            // 审核通过
            return "MAIL_KYC_BOSS_PASS";
        } else {
            // 审核拒绝
            return "MAIL_KYC_BOSS_UNPASS";
        }
    }

}
