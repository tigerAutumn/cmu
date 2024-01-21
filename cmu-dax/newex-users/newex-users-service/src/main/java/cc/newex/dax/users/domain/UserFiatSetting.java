package cc.newex.dax.users.domain;

import lombok.*;

import java.util.Date;

/**
 * 用户法币交易设置
 *
 * @author newex-team
 * @date 2018-05-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFiatSetting {
    /**
     *
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 银行卡号
     */
    private String bankCard;
    /**
     * 开户支行
     */
    private String bankAddress;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行编码
     */
    private String bankNo;
    /**
     * 支付宝收款人姓名
     */
    private String alipayName;
    /**
     * 支付宝账号
     */
    private String alipayCard;
    /**
     * 支付宝收款码
     */
    private String alipayReceivingImg;
    /**
     * 微信支付收款人姓名
     */
    private String wechatPayName;
    /**
     * 微信支付账号
     */
    private String wechatCard;
    /**
     * 微信支付收款码
     */
    private String wechatReceivingImg;
    /**
     * 状态 0:正常 1:冻结账户
     */
    private Integer status;
    /**
     * 备注信息
     */
    private String remarks;
    /**
     * 支付方式 1:银行卡,2:支付宝,3:微信支付
     */
    private Integer payType;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;

    public static UserFiatSetting getInstance() {
        return UserFiatSetting.builder()
                .id(0L)
                .userId(0L)
                .userName("")
                .bankCard("")
                .bankAddress("")
                .bankName("")
                .bankNo("")
                .alipayName("")
                .alipayCard("")
                .alipayReceivingImg("")
                .wechatPayName("")
                .wechatCard("")
                .wechatReceivingImg("")
                .status(0)
                .remarks("")
                .payType(1)
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();
    }
}