package cc.newex.dax.extra.domain.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 新闻或文章类别表
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewsCategory {
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

    public static NewsCategory getInstance() {
        return NewsCategory.builder()
                .id(0)
                .parentId(0)
                .locale("")
                .name("")
                .createdDate(new Date())
                .build();
    }
}