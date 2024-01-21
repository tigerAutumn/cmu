package cc.newex.dax.extra.dto.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class NewsCategoryDTO {
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
    private String locale;
    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static NewsCategoryDTO getInstance() {
        return NewsCategoryDTO.builder()
                .id(0)
                .parentId(0)
                .locale("")
                .name("")
                .createdDate(new Date())
                .build();
    }
}