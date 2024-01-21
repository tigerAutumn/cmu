package cc.newex.dax.extra.dto.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class NewsTemplateDTO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板内容
     */
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

    public static NewsTemplateDTO getInstance() {
        return NewsTemplateDTO.builder()
                .id(0)
                .name("")
                .content("")
                .memo("")
                .createdDate(new Date())
                .build();
    }
}