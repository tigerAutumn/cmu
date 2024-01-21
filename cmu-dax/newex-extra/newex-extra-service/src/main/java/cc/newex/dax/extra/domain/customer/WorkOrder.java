package cc.newex.dax.extra.domain.customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工单表
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkOrder {
    /**
     * 工单表
     */
    private Long id;
    /**
     * 菜单id
     */
    private Integer menuId;
    /**
     * 组别id
     */
    private Integer groupId;
    /**
     * 状态 0:未分配 1:未处理 2:处理中 3:已完成 4评价完成
     */
    private Integer status;
    /**
     * 打开次数 0
     */
    private Integer unfoldCount;
    /**
     * 0：默认 1：紧急
     */
    private Integer urgent;
    /**
     * 0:Default
     */
    private Integer siteType;
    /**
     * zh-cn/en-us
     */
    private String locale;
    /**
     * 0: 客户 1: 运营
     */
    private Integer fromType;
    /**
     * 0: 不对前端显示 1: 显示
     */
    private Integer isShow;
    /**
     * 工单来源 0：默认 1：c2c申诉 2:开放区申诉
     */
    private Integer source;
    /**
     * 问题描述
     */
    private String content;
    /**
     * 汇款人姓名
     */
    private String remitName;
    /**
     * 汇款人卡号
     */
    private String remitCardNumber;
    /**
     * 支付宝账号
     */
    private String remitAlipay;
    /**
     * 汇款人金额
     */
    private String remitAmount;
    /**
     * 币种
     */
    private String coinType;
    /**
     * 提币地址
     */
    private String withdrawAddress;
    /**
     * 提币数量
     */
    private BigDecimal withdrawNumber;
    /**
     * 绑定手机可否接听电话状态 0:不可接听 1:可接听
     */
    private Integer answerStatus;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 手机号
     */
    private String userPhone;
    /**
     * 满意度 -1:不满意  1:满意
     */
    private Integer satisfaction;
    /**
     * 首次回复时间
     */
    private Date responseTime;
    /**
     * 解决时间
     */
    private Date solveTime;
    /**
     * 首次回复减去受理时间
     */
    private Long handleTime;
    /**
     * 解决时间减去受理时间
     */
    private Long disposeTime;
    /**
     * 评价内容
     */
    private String comment;
    /**
     * 受理时间
     */
    private Date acceptTime;
    /**
     * 最后一次回复时间
     */
    private Date lastReplyTime;
    /**
     * 是否为新版本数据 1:是；0:否
     */
    private Integer fresh;
    /**
     * 处理人
     */
    private Integer adminUserId;
    /**
     * 处理人账号
     */
    private String adminAccount;
    /**
     * 处理人姓名
     */
    private String adminName;
    /**
     * 创建人
     */
    private Integer createAdminUserId;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static WorkOrder getInstance() {
        return WorkOrder.builder()
                .id(0L)
                .menuId(0)
                .groupId(0)
                .status(0)
                .unfoldCount(0)
                .urgent(0)
                .siteType(0)
                .locale("")
                .fromType(0)
                .isShow(0)
                .source(0)
                .content("")
                .remitName("")
                .remitCardNumber("")
                .remitAlipay("")
                .remitAmount("")
                .coinType("")
                .withdrawAddress("")
                .withdrawNumber(BigDecimal.ZERO)
                .answerStatus(0)
                .userId(0L)
                .userName("")
                .userEmail("")
                .userPhone("")
                .satisfaction(0)
                .responseTime(null)
                .solveTime(null)
                .handleTime(0L)
                .disposeTime(0L)
                .comment("")
                .acceptTime(null)
                .lastReplyTime(null)
                .fresh(0)
                .adminUserId(0)
                .adminAccount("")
                .adminName("")
                .createAdminUserId(0)
                .createdDate(new Date())
                .build();
    }
}