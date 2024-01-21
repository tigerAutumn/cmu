package cc.newex.dax.extra.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ratingToken代币类型枚举
 *
 * @author better
 * @date create in 2018/11/28 3:33 PM
 */
@Getter
@AllArgsConstructor
public enum RtSocialCategoryEnum {

    /**
     * 情感
     */
    EMOTION("emotion"),

    /**
     * 社区
     */
    COMMUNITY("community"),

    /**
     * 所有
     */
    ALL("all");

    /**
     * 类型
     */
    private final String socialType;
}
