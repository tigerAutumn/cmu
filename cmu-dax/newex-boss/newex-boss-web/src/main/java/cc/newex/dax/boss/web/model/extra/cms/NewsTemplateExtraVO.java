package cc.newex.dax.boss.web.model.extra.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 新闻模板表
 *
 * @author liutiejun
 * @date 2018-06-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewsTemplateExtraVO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 模板名称
     */
    @NotBlank
    private String name;
    /**
     * 模板内容
     */
    @NotBlank
    private String content;
    /**
     * 模板备注
     */
    private String memo;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static NewsTemplateExtraVO getInstance() {
        return NewsTemplateExtraVO.builder()
                .id(0)
                .name("")
                .content("")
                .memo("")
                .createdDate(new Date())
                .build();
    }
}