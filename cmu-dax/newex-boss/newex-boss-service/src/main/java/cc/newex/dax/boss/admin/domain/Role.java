package cc.newex.dax.boss.admin.domain;

import lombok.*;

import java.util.Date;

/**
 * 后台系统角色表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Role {
    /**
     * 系统角色标识
     */
    private Integer id;
    /**
     * 创建管理员用户id
     */
    private Integer adminUserId;
    /**
     * 系统角色所拥有的模块集合(module_id以英文逗号分隔)
     */
    private String modules;
    /**
     * 系统角色所拥有的操作集合(permission_id以英文逗号分隔)
     */
    private String permissions;
    /**
     * 系统角色名称
     */
    private String name;
    /**
     * 系统角色英语名
     */
    private String code;
    /**
     * 是否为系统角色,1表示是，0表示否,默认为0
     */
    private Integer isSystem;
    /**
     * 系统角色的状态,状态 0表示禁用;1表示启用;默认为1,其他保留
     */
    private Integer status;
    /**
     * 系统角色的排序顺序
     */
    private Integer sequence;
    /**
     * 创建管理用户名
     */
    private String adminUsername;
    /**
     * 系统角色备注
     */
    private String comment;
    /**
     * 系统角色记录创建时间
     */
    private Date createdDate;
    /**
     * 系统角色记录更新时间戳
     */
    private Date updatedDate;

    /**
     * brokerId
     */
    private Integer brokerId;

    public static Role getInstance() {
        return Role.builder()
                .adminUserId(-1)
                .modules("")
                .permissions("")
                .name("")
                .code("")
                .isSystem(0)
                .status(1)
                .sequence(10)
                .adminUsername("")
                .comment("")
                .updatedDate(new Date())
                .brokerId(0)
                .build();
    }
}