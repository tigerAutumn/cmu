package cc.newex.dax.boss.web.model.extra.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 标签分类
 *
 * @author liutiejun
 * @date 2018-07-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagCategoryExtraVO {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 语言
     */
    private String locale;

    /**
     * 分类名称
     */
    @NotBlank
    private String name;

    /**
     * 类别，1-币种标签，2-币对标签
     */
    @NotNull
    private Integer type;

    /**
     * 分类编码
     */
    @NotBlank
    private String code;

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
