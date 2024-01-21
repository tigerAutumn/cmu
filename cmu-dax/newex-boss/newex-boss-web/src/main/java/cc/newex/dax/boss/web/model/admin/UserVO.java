package cc.newex.dax.boss.web.model.admin;

import lombok.*;

import java.util.Date;

/**
 * @author newex-team
 * @date 2018-05-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserVO {
    /**
     * 系统用户标识
     */
    private Integer id;

    /**
     * 组别name
     */
    private String groupName;
    /**
     * 组别id
     */
    private Integer groupId;
    /**
     * 系统用户所属角色集合(role_id以英文逗号分隔)
     */
    private String roles;
    /**
     * 系统用户账号
     */
    private String account;
    /**
     * 系统用户密码
     */
    private String password;
    /**
     * 系统用户姓名
     */
    private String name;
    /**
     * 系统用户电子邮箱
     */
    private String email;
    /**
     * 系统用户用户电话号码,多个用英文逗号分开
     */
    private String telephone;
    /**
     * 系统用户的状态,状态 0表示禁用;1表示启用;默认为1,其他保留
     */
    private Integer status;
    /**
     * 用户类型 1 客服人员。2 团队组长
     */
    private Integer type;
    /**
     * 照片
     */
    private String imagePath;
    /**
     * google双重认证密钥
     */
    private String totpKey;
    /**
     * 管理员值班状态1:上班 2:休息 3 下班
     */
    private Integer dutyStatus;
    /**
     * 订阅工单数量
     */
    private Integer orderNum;
    /**
     * 系统用户备注
     */
    private String comment;
    /**
     * 系统用户记录创建时间
     */
    private Date createdDate;
    /**
     * 系统用户记录更新时间戳
     */
    private Date updatedDate;

    private Integer brokerId;


    public static UserVO getInstance() {
        return UserVO.builder()
                .groupName("")
                .groupId(0)
                .roles("")
                .account("")
                .password("")
                .name("")
                .email("")
                .telephone("")
                .status(1)
                .type(1)
                .imagePath("")
                .totpKey("")
                .dutyStatus(1)
                .orderNum(10)
                .comment("")
                .updatedDate(new Date())
                .brokerId(0)
                .build();
    }
}
