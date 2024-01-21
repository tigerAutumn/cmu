package cc.newex.dax.users.rest.model;

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
public class UserFiatSettingVO {
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
    private String bankNo;
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
     * 支付宝开关 0:关闭,1:打开
     */
    private String alipayAuthFlag;
    /**
     * 微信支付开关 0:关闭,1:打开
     */
    private String wechatPayAuthFlag;
    /**
     * 创建时间
     */
    private Date createdDate;

}