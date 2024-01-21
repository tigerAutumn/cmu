package cc.newex.dax.users.dto.security;

import lombok.*;

/**
 * @author gi
 * @date 11/9/18
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPaymentTypeDTO {

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
     * 是否是否绑定银行卡
     */
    private Boolean bankCardFlag;

    /**
     * 银行卡状态
     */
    private Boolean bankCardAuthFlag;

    /**
     * 微信状态
     */
    private Boolean wechatAuthFlag;

    /**
     * 支付宝状态
     */
    private Boolean alipayAuthFlag;
}