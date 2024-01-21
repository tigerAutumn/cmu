package cc.newex.dax.integration.service.msg.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
public enum MessageTypeEnum {
    /**
     * 邮件
     */
    MAIL("mail"),
    /**
     * 短信
     */
    SMS("sms");

    private final String name;

    MessageTypeEnum(final String name) {
        this.name = name;
    }

    public static MessageTypeEnum getByName(final String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        return Arrays.stream(values()).filter(x -> x.getName().equals(name)).findFirst().orElse(null);
    }
}
