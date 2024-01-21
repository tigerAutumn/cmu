package cc.newex.dax.integration.service.msg.enums;

import lombok.Getter;

/**
 * @author newex-team
 * @date 2018-07-30
 */
@Getter
public enum ProviderNameEnum {
    /**
     * 阿里云邮件
     */
    ALIYUN_EMAIL("aliyun-email"),

    /**
     * 美橙互联邮件
     */
    CNDNS_EMAIL("cndns-email"),

    /**
     * QQ邮件
     */
    QQ_EMAIL("qq-email"),

    /**
     * 创蓝短信（国际）
     */
    ZZ253_SMS("zz253-sms"),

    /**
     * 创蓝短信(中国）
     */
    ZZ253ZH_SMS("zz253zh-sms"),

    /**
     * twilio短信
     */
    TWILIO_SMS("twilio-sms"),

    /**
     * nexmo短信
     */
    NEXMO_SMS("nexmo-sms"),

    /**
     * 亿美短信
     */
    EMAY_SMS("emay-sms"),

    /**
     * 大汉三通
     */
    NET3TONG_SMS("3tong-sms");

    private final String name;

    ProviderNameEnum(final String name) {
        this.name = name;
    }
}
