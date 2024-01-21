package cc.newex.dax.perpetual.common.enums;

import lombok.AllArgsConstructor;

/**
 * 触发类型：指数价格market，标记价格mark，最新价格new
 *
 * @author xionghui
 * @date 2018/11/13
 */
@AllArgsConstructor
public enum ConditionOrderTypeEnum {
    INDEX("index"), //指数价格market
    MARK("mark"), //标记价格mark
    LAST("last"),//最新价格last
    ;

    private final String type;

    public String getType() {
        return this.type;
    }
}
