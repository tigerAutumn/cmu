package cc.newex.dax.boss.web.model.extra.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 新闻文章表
 *
 * @author liutiejun
 * @date 2018-06-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewsExtraVO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 文章类别id
     */
    @NotNull
    private Integer categoryId;
    /**
     * 文章模版
     */
    @NotNull
    private Integer templateId;
    /**
     * 发布用户id对应boss系统的管理员id
     */
    private Integer publisherId;
    /**
     * 文章唯一编号
     */
    private String uid;
    /**
     * 本地化语言代号(zh-cn/en-us等）
     */
    @NotBlank
    private String locale;
    /**
     * 文章标题
     */
    @NotBlank
    private String title;
    /**
     * 文章内容
     */
    @NotBlank
    private String content;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static NewsExtraVO getInstance() {
        return NewsExtraVO.builder()
                .id(0)
                .categoryId(0)
                .templateId(0)
                .publisherId(0)
                .uid("")
                .locale("")
                .title("")
                .content("")
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();
    }
}