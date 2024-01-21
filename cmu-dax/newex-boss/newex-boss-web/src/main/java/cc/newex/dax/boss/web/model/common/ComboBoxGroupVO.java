package cc.newex.dax.boss.web.model.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * display combobox items in groups
 *
 * @author liutiejun
 * @date 2018-06-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ComboBoxGroupVO {

    /**
     * 组名
     */
    private String group;

    /**
     * 对应数据的值
     */
    private String value;

    /**
     * 显示的名称
     */
    private String text;

}
