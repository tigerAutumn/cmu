package cc.newex.dax.users.common.enums;

import lombok.Getter;

@Getter
public enum ActivityEnum {
    /**
     * 活动-注册
     */
    ACTIVITY_REGISTER("register");
    /**
     * 类型
     */
    private final String type;

    ActivityEnum(final String type) {
        this.type = type;
    }
}
