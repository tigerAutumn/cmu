package cc.newex.dax.extra.domain.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 标签分类表
 *
 * @author newex-team
 * @date 2018-09-27 14:03:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagCategory {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 本地化语言代号(zh-cn/en-us等)
     */
    private String locale;

    /**
     * 分类编码
     */
    private String code;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 类别，1-币种标签，2-币对标签
     */
    private Integer type;

    /**
     * 选择类型，该分类下面的标签是否可以多选，1-多选，2-单选
     */
    private Integer choose;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}