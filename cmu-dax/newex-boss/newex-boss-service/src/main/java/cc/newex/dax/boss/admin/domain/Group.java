package cc.newex.dax.boss.admin.domain;

import lombok.*;

import java.util.Date;

/**
 * 后台系统企业组织机构表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Group {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 父节点id
     */
    private Integer parentId;
    /**
     * 创建管理员用户id
     */
    private Integer adminUserId;
    /**
     * 节点id
     */
    private String path;
    /**
     * 机构名称
     */
    private String name;
    /**
     * 机构类型
     */
    private Integer type;
    /**
     * 状态 0表示禁用;1表示启用;默认为1
     */
    private Integer status;
    /**
     * 在当前节点下的顺序
     */
    private Integer sequence;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 修改时间
     */
    private Date updatedDate;

    /**
     * brokerId
     */
    private Integer brokerId;


    public static Group getInstance() {
        return Group.builder()
                .parentId(0)
                .adminUserId(-1)
                .path("")
                .name("")
                .type(0)
                .status(1)
                .sequence(10)
                .updatedDate(new Date())
                .brokerId(0)
                .build();
    }
}