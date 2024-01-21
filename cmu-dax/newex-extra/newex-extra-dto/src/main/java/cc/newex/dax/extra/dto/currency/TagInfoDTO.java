package cc.newex.dax.extra.dto.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 标签表
 *
 * @author mbg.generated
 * @date 2018-08-20 17:32:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagInfoDTO {
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