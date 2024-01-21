package cc.newex.dax.boss.web.model.activity.redpacket;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author better
 * @date create in 2019-01-08 14:34
 */
@Data
public class RedPacketVO {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 红包唯一标识
     */
    private String uid;

    /**
     * 发红包的人
     */
    private Long userId;

    /**
     * 红包类型，1-普通红包，2-口令红包
     */
    private Integer redType;

    /**
     * 红包口令
     */
    private String code;

    /**
     * 口令md5值
     */
    private String codeMd5;

    /**
     * 红包额度类型，1-固定数量，2-随机数量
     */
    private Integer amountType;

    /**
     * 币种ID
     */
    private Integer currencyId;

    /**
     * 单个数量，每个人领到的数量固定
     */
    private BigDecimal singleAmount;

    /**
     * 随机数量，每个人领到的数量随机
     */
    private BigDecimal randomAmount;

    /**
     * 红包中币种的总数量
     */
    private BigDecimal amount;

    /**
     * 红包个数
     */
    private Integer number;

    /**
     * 红包主题
     */
    private Long themeId;

    /**
     * 祝福语
     */
    private String greeting;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 介绍
     */
    private String intro;

    /**
     * 红包有效期，1-24小时，2-3天，3-7天
     */
    private Integer validityPeriod;

    /**
     * 红包过期时间
     */
    private Date expiredDate;

    /**
     * 红包生成url
     */
    private String redUrl;

    /**
     * 红包状态，1-初始化，2-领取中，3-领完，4-过期，5-没领完的红包已退回
     */
    private Integer status;

    /**
     * 券商Id
     */
    private Integer brokerId;
}
