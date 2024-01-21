package cc.newex.dax.integration.service.msg.enums;

import lombok.Getter;

/**
 * @author newex-team
 * @date 2018-05-14
 */
@Getter
public enum MessageStatusEnum {
    /**
     * 失败
     */
    FAILURE(0),
    /**
     * 成功
     */
    SUCCESS(1),
    /**
     * 没找到对应的消息模版
     */
    NOT_FOUND_TEMPLATE(11),
    /**
     * 无效的email消息
     */
    INVALID_EMAIL_MESSAGE(12),
    /**
     * 无效的sms消息
     */
    INVALID_SMS_MESSAGE(13),
    /**
     * 解析消息模板出错
     */
    MESSAGE_TEMPLATE_RENDER_ERROR(14),
    /**
     * 黑名单
     */
    BLACK_LIST(15),
    /**
     * 没有找到对应的 broker 配置的签名
     */
    NOT_FOUND_SIGN(16),;

    private final int value;

    MessageStatusEnum(final int value) {
        this.value = value;
    }
}
