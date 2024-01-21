package cc.newex.dax.extra.domain.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 新闻文章表
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class News {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 文章模版id
     */
    private Integer templateId;

    /**
     * 发布用户id对应boss系统的管理员id
     */
    private Integer publisherId;

    /**
     * 文章类别id
     */
    private Integer categoryId;

    /**
     * 访问链接
     */
    private String link;

    /**
     * 文章唯一编号
     */
    private String uid;

    /**
     * 本地化语言代号(zh-cn/en-us等）
     */
    private String locale;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

}