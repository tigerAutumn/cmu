package cc.newex.dax.boss.admin.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 后台系统用户表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupUser {
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
     * 状态0表示启用，1表示禁用
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
     * 系统用户账号
     */
    private String userAccount;
    /**
     * 系统用户电子邮箱
     */
    private String userEmail;
}
