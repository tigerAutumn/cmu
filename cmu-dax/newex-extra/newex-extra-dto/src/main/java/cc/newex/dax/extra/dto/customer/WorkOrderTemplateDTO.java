package cc.newex.dax.extra.dto.customer;

import lombok.*;

import java.util.Date;

/**
 * @author allen
 * @date 2018/6/9
 * @des
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkOrderTemplateDTO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 菜单id
     */
    private Integer menuId;
    /**
     * 问题模版
     */
    private String template;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;
}
