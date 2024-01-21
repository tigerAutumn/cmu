package cc.newex.dax.boss.web.model.activity.redpacket;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author better
 * @date create in 2019-01-08 14:46
 */
@Data
public class RedPacketSendRecordVO {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 发红包的人
     */
    private Long sendUserId;

    /**
     * 红包唯一标识
     */
    private String redUid;

    /**
     * 币种ID
     */
    private Integer currencyId;

    /**
     * 币种名称
     */
    private String currencyCode;

    /**
     * 红包中币种的总数量
     */
    private BigDecimal amount;

    /**
     * 红包类型，1-普通红包，2-口令红包
     */
    private Integer redType;

    /**
     * 红包额度类型，1-固定数量，2-随机数量
     */
    private Integer amountType;

    /**
     * 券商Id
     */
    private Integer brokerId;
}
