package cc.newex.dax.extra.domain.customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 工单问题模版表
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkOrderTemplate {
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

    public static WorkOrderTemplate getInstance() {
        return WorkOrderTemplate.builder()
                .id(0)
                .menuId(0)
                .template("")
                .createdDate(new Date())
                .build();
    }
}