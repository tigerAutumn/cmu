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
public enum RtCurrencyTypeEnum {

    /**
     * 项目基础
     */
    BASE("base"),

    /**
     * 项目动态
     */
    DYNAMIC("dynamic"),

    /**
     * 项目类型
     */
    PROJECT("project"),

    /**
     * 社交类型
     */
    SOCIAL("social");

    /**
     * 类型
     */
    private final String type;
}
