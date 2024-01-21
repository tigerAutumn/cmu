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
 * 标签
 *
 * @author liutiejun
 * @date 2018-07-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagInfoExtraVO {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 标签名称
     */
    @NotBlank
    private String name;

    /**
     * 本地化语言代号(zh-cn/en-us等)
     */
    @NotBlank
    private String locale;

    /**
     * 标签编码
     */
    @NotBlank
    private String code;

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
