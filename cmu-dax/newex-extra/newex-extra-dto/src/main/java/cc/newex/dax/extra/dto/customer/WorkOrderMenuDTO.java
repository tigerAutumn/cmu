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
public class WorkOrderMenuDTO {

    /**
     * 菜单表
     */
    private Integer id;
    /**
     * 菜单父级id
     */
    private Integer parentId;
    /**
     * 本地化语言(zh-cn/en-us等)
     */
    private String locale;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 状态 0 启用 1 停用
     */
    private Integer status;
    /**
     * 处理问题负责问题的组id
     */
    private String groupId;
    /**
     * 创建人id对应后台管理员id
     */
    private Integer adminUserId;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date updatedDate;
}
