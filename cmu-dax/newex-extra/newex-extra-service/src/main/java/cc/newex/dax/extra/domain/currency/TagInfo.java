package cc.newex.dax.extra.domain.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 标签信息表
 *
 * @author newex-team
 * @date 2018-09-27 14:03:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagInfo {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 本地化语言代号(zh-cn/en-us等)
     */
    private String locale;

    /**
     * 标签编码
     */
    private String code;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 所属标签分类编码
     */
    private String tagCategoryCode;

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