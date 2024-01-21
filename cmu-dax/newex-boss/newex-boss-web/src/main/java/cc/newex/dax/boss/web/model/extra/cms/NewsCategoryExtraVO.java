package cc.newex.dax.boss.web.model.extra.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 新闻或文章类别表
 *
 * @author liutiejun
 * @date 2018-06-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewsCategoryExtraVO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 父节点id
     */
    private Integer parentId;
    /**
     * 语言(zh-cn\en-us等)
     */
    @NotBlank
    private String locale;
    /**
     * 名称
     */
    @NotBlank
    private String name;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static NewsCategoryExtraVO getInstance() {
        return NewsCategoryExtraVO.builder()
                .id(0)
                .parentId(0)
                .locale("")
                .name("")
                .createdDate(new Date())
                .build();
    }
}