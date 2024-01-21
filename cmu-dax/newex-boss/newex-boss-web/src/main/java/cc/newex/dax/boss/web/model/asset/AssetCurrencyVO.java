package cc.newex.dax.boss.web.model.asset;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AssetCurrencyVO {

    /**
     * 产品线，1-spot-币币，2-c2c-法币，3-contract-合约，4-portfolio-组合
     * 业务线[ all ]
     */
    private String biz;

    /**
     * 币种id
     * 业务线[ all ]
     */
    private Integer id;

    /**
     * 币种代号 例:CNY BTC LTC
     * 业务线[ all ]
     */
    @NotBlank
    private String symbol;

    /**
     * 币种符号
     * 业务线[ all ]
     */
    private String sign;

    /**
     * 全称
     * 业务线[ all ]
     */
    @NotBlank
    private String fullName;

    /**
     * 币种图片
     * 业务线[ all ]
     */
    private String currencyPictureUrl;

    /**
     * 排序系数  越小越靠前
     * 业务线[ all ]
     */
    private Integer sort;

    /**
     * 上线状态  0下线 1上线 2预发
     * 业务线[ all ]
     */
    @NotNull
    private Integer online;

    /**
     * 是否支持划转  0:不能划转 1:可划转
     * 业务线[ all ]
     */
    private Integer transfer;

    /**
     * 价格小数位数
     * 业务线[ c2c ]
     */
    private Integer decimalPlaces;

    /**
     * 是否可提现 0 不可充值 1可充值
     * 业务线[ asset spot ]
     */
    private Integer withdrawable;

    /**
     * 是否可充值 1:可提现，0:不可提现
     * 业务线[ asset spot ]
     */
    private Integer rechargeable;

    /**
     * 一个币兑换另一个币 0:不能兑换 1:可兑换
     * 业务线[ asset spot ]
     */
    private Integer exchange;

    /**
     * 是否支持领取糖果 0:不能领取 1:可领取
     * 业务线[ asset spot ]
     */
    private Integer receive;

    /**
     * 杠杆利息配置
     * 业务线[margin]
     */
    private Double marginInterestRate;

    /**
     * 提现手续费
     * 业务线[ asset spot ]
     */
    private BigDecimal withdrawFee;

    /**
     * 充值确认数  参考 {CurrencyEnum}
     * 业务线[ asset spot]
     */
    private Integer depositConfirm;

    /**
     * 提现确认数 参考 {CurrencyEnum}
     * 业务线[ asset spot ]
     */
    private Integer withdrawConfirm;

    /**
     * 最小充值数目
     * 业务线[ asset spot ]
     */
    private BigDecimal minDepositAmount;

    /**
     * 最小提现数目
     * 业务线[ asset spot ]
     */
    private BigDecimal minWithdrawAmount;

    /**
     * 最大提现数目
     * 业务线[ asset spot]
     */
    private BigDecimal maxWithdrawAmount;

    /**
     * tx查询url
     * 业务线[asset spot]
     */
    private String txExplorerUrl;

    /**
     * 是否需要标签 -- 应该所有业务线都加这个字段
     * 业务线[ asset spot ]
     */
    private Integer needTag;

    /**
     * 如果需要标签 则设置是哪个 -- 应该所有业务线都加这个字段
     * 业务线[  asset spot ]
     */
    private String tagField;

    /**
     * 是否需要弹层  - 弹出内容在message里面配置
     * 业务线[ asset spot ]
     */
    private Integer needAlert;

    /**
     * 币种简介
     * 业务线[ asset spot ]
     */
    private String currencySummary;

    /**
     * 币种官网地址
     * 业务线[ asset spot ]
     */
    private String currencyUrl;

    /**
     * 交易分区，1-主板块，2-创新板，3-币创板
     * 业务线[ asset spot ]
     */
    private Integer zone;

    /**
     * 中文wiki url
     * 业务线[ asset spot ]
     */
    private String cnWikiUrl;

    /**
     * 繁体wiki url
     * 业务线[ asset spot ]
     */
    private String twWikiUrl;

    /**
     * 英文wiki url
     * 业务线[ asset spot ]
     */
    private String usWikiUrl;

    /**
     * 是否开启未来下线，0-否，1-是
     */
    private Integer futureDown;

    /**
     * 币创区币种下线时间
     * 业务线[ asset spot ]
     */
    private String expireDate;

    /**
     * 区块浏览器
     * 业务线[ asset spot ]
     */
    private String blockBrowser;

    /**
     * 创建时间
     * 业务线[ c2c ]
     */
    private String createdDate;

    /**
     * c2c  0=数字货币,1=法币
     * 业务线[ c2c ]
     */
    private Integer type;

    /**
     * 资金列表是否展示 0不展示 1展示
     * 业务线[ c2c ]
     */
    private Integer isAssetVisible;

    /**
     * 商家佣金费率
     * 业务线[ c2c ]
     */
    private BigDecimal platformCommissionRate;

    /**
     * 单笔订单额度最小值
     * 业务线[ c2c ]
     */
    private BigDecimal minOrderTotalPerOrder;

    /**
     * 单笔订单额度最大值
     * 业务线[ c2c ]
     */
    private BigDecimal maxOrderTotalPerOrder;

    /**
     * 所有未完成订单总额度最大值。只有法币能用于支付订单
     * 业务线[ c2c ]
     */
    private BigDecimal maxIncompleteOrderTotalPerUser;

    /**
     * 广告单买单保证金
     * 业务线[ c2c ]
     */
    private BigDecimal deposit;

    /**
     * 广告单最小金额
     * 业务线[ c2c ]
     */
    private BigDecimal minOrderTotalPerTradingOrder;

    /**
     * 广告单最大金额
     * 业务线[ c2c ]
     */
    private BigDecimal maxOrderTotalPerTradingOrder;

    /**
     * 关联dapps表的id
     * 业务线[ c2c ]
     */
    private String dapps;

    /**
     * 发行价
     * 业务线[ c2c ]
     */
    private BigDecimal issuePrice;

    /**
     * 券商ID
     */
    private Integer brokerId;

}
