package cc.newex.dax.asset.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 币种表 属性归并到extendAttrs
 *
 * @author lilaizhen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AssetCurrencyCompress {
    /**
     * 币种id  --条件查询
     */
    private Integer id;
    /**
     * 币种简称 例:CNY BTC LTC
     */
    private String symbol;
    /**
     * 拓展配置
     */
    private String extendAttrs;
    /**
     * 提现
     * 0:不可提现 1:可提现
     */
    private Integer withdrawable;
    /**
     * 充值
     * 0:不可充值 1:可充值
     */
    private Integer rechargeable;
    /**
     * 排序系数
     */
    private Integer sort;
    /**
     * 上线
     */
    private Integer online;
    /**
     * 兑换
     * 0:不能兑换 1:可兑换
     */
    private Integer exchange;
    /**
     * 领取
     * 0:不能领取 1:可领取
     */
    private Integer receive;
    /**
     * 划转
     * 0:不能划转 1:可划转
     */
    private Integer transfer;
    /**
     * 提现手续费
     */
    private BigDecimal withdrawFee;
    /**
     * 充值确认数
     */
    private Integer depositConfirm;
    /**
     * 提现确认数
     */
    private Integer withdrawConfirm;
    /**
     * 最小充值数目
     */
    private BigDecimal minDepositAmount;
    /**
     * 最小提现数目
     */
    private BigDecimal minWithdrawAmount;
    /**
     * 最大提现数目
     */
    private BigDecimal maxWithdrawAmount;
    /**
     */
    private Date updateDate;
    /**
     * 券商id
     */
    private Integer brokerId;
}
