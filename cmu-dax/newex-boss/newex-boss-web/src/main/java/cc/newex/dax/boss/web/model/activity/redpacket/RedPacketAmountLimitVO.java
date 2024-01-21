package cc.newex.dax.boss.web.model.activity.redpacket;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author better
 * @date create in 2019-01-08 14:42
 */
@Data
public class RedPacketAmountLimitVO {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 币种ID
     */
    private Integer currencyId;

    /**
     * 币种名称
     */
    private String currencyCode;

    /**
     * 币种最小限额
     */
    private BigDecimal minAmount;

    /**
     * 币种最大限额
     */
    private BigDecimal maxAmount;

    /**
     * 红包个数限制
     */
    private Integer maxNumber;

    /**
     * 币种数量小数位数限制
     */
    private Integer maxDigit;

    /**
     * 券商Id
     */
    private Integer brokerId;
}
