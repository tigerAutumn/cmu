package cc.newex.dax.extra.dto.cgm;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目基础信息表
 *
 * @author mbg.generated
 * @date 2018-08-17 09:56:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectTokenInfoDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 币种名称（币种全称）
     */
    private String token;

    /**
     * 币种代码（币种简称）
     */
    private String tokenSymbol;

    /**
     * 币种logo
     */
    private String imgName;

    /**
     * 币种类型，zhuwangbi-主网币，daibi-代币
     */
    private String type;

    /**
     * 发行总量
     */
    private String issue;

    /**
     * 流通总量
     */
    private String circulating;

    /**
     * coinmarketcap url
     */
    private String coinmarketcap;

    /**
     * 是否上线交易所 0未上线、1上线
     */
    private Byte online;

    /**
     * 交易所名称
     */
    private String exchangeName;

    /**
     * 24小时成交量
     */
    private String tradeVolume;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 拥有用户数
     */
    private Integer possessUsers;

    /**
     * 项目审核状态 0:待审核 1:初始审核 2:待上线 3:已上线  -1:拒绝
     */
    private Byte status;

    /**
     * 保证金数量
     */
    private BigDecimal deposit;

    /**
     * 代币糖果数量
     */
    private BigDecimal tokenNumber;

    /**
     * 代币糖果地址
     */
    private String tokenUrl;

    /**
     * 商务对接人
     */
    private String contacts;

    /**
     * 对接微信
     */
    private String wechat;

    /**
     * 上线时间
     */
    private Date onlineTime;

    /**
     * 发币时间
     */
    private Date startTime;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 保证金支付状态:0:未支付，1：支付
     */
    private Byte depositStatus;

    /**
     * 糖果支付状态:0:未支付，1：支付
     */
    private Byte sweetsStatus;

    /**
     * 券商ID
     */
    private Integer brokerId;
}