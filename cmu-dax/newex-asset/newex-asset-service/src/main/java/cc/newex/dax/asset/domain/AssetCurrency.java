package cc.newex.dax.asset.domain;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 币种表
 *
 * @author newex-team
 * @date 2018-05-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AssetCurrency implements Serializable {
    /**
     * 币种id  --条件查询
     */
    private Integer id;
    /**
     * 币种代号 例:CNY BTC LTC  --条件查询
     */
    private String symbol;
    /**
     * 业务线 英文名称
     */
    private String biz;
    /**
     * 币种符号 单位符号
     */
    private String sign;
    /**
     * 全称
     */
    private String fullName;
    /**
     * 是否可提现 --条件查询 1.可提现 0不可提现
     */
    private Integer withdrawable;
    /**
     * 是否可充值 1:可提现，0:不可提现  --条件查询
     */
    private Integer rechargeable;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 上线  --条件查询   0下线 1上线 2预发
     */
    private Integer online;
    /**
     * 图片地址
     */
    private String currencyPictureUrl;
    /**
     * 一个币兑换成另一个币开关 0:不能兑换 1:可兑换
     */
    private Integer exchange;
    /**
     * 糖果是否可领取 0:不能领取 1:可领取
     */
    private Integer receive;
    /**
     * 划转开关 同时控制转入转出 0:不能划转 1:可划转
     */
    private Integer transfer;
    /**
     * 杠杆利息配置
     */
    private Double marginInterestRate;
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
     * 最大提现数量
     */
    private BigDecimal maxWithdrawAmount;
    /**
     * 区块浏览器
     */
    private String txExplorerUrl;
    /**
     * 是否需要tag
     */
    private Integer needTag;
    /**
     * tag 字段
     */
    private String tagField;
    /**
     * 是否需要弹层
     */
    private Integer needAlert;
    /**
     *
     */
    private String currencySummary;
    /**
     * 币种url
     */
    private String currencyUrl;
    /**
     * 更新日期
     */
    private Date updateDate;

    /**
     * 交易分区，1-主板块，2-创新板
     */
    private Integer zone;
    /**
     * 中文url
     */
    private String cnWikiUrl;
    /**
     * 繁体url
     */
    private String twWikiUrl;
    /**
     * 英文url
     */
    private String usWikiUrl;
    /**
     * 下线时间
     */
    private Long expireDate;
    /**
     * 区块浏览器
     */
    private String blockBrowser;

    /**
     * c2c 创建时间
     */
    private Date createdDate;
    /**
     * c2c  0=数字货币,1=法币
     */
    private Integer type;
    /**
     * decimal_places 小数点后几位
     */
    private Integer decimalPlaces;
    /**
     * 佣金费率，抽取溢价部分做为平台的佣金
     */
    private BigDecimal platformCommissionRate;
    /**
     * 单笔订单额度最小值
     */
    private BigDecimal minOrderTotalPerOrder;
    /**
     * 单笔订单额度最小值
     */
    private BigDecimal maxOrderTotalPerOrder;

    /**
     * 所有未完成订单总额度最大值。只有法币能用于支付订单
     */
    private BigDecimal maxIncompleteOrderTotalPerUser;
    /**
     * 保证金
     */
    private BigDecimal deposit;
    /**
     * 委托单最小金额
     */
    private BigDecimal minOrderTotalPerTradingOrder;
    /**
     * 委托单最大金额
     */
    private BigDecimal maxOrderTotalPerTradingOrder;
    /**
     * 资金管理页面可见  0 不可见 1可见
     */
    private Integer isAssetVisible;
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
     * 券商id
     * 业务线[ all ]
     */
    private Integer brokerId;
}