package cc.newex.dax.users.dto.security;

import lombok.*;

import java.util.Date;

/**
 * 用户法币交易设置
 *
 * @author newex-team
 * @date 2018-05-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFiatSettingDTO {
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
     * 银行编号
     */
    private String bankNo;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 开户支行
     */
    private String bankAddress;
    /**
     * 银行卡号
     */
    private String bankCard;
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
     * 预览支付宝收款码
     */
    private String previewAlipayReceivingImg;
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
     * 预览微信支付收款码
     */
    private String previewWechatReceivingImg;
    /**
     * 支付宝开关 0:关闭,1:打开
     */
    private Integer alipayAuthFlag;
    /**
     * 微信支付开关 0:关闭,1:打开
     */
    private Integer wechatPayAuthFlag;
    /**
     * 银行卡支付开关 0:关闭,1:打开
     */
    private Integer bankPayAuthFlag;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 验证码
     */
    private String verificationCode;
    /**
     * 支付方式 1:银行卡,2:支付宝,3:微信支付
     */
    private Integer payType;

}