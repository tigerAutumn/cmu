package cc.newex.dax.perpetual.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author harry
 * @Date: 2018/8/23 21:40
 * @Description:
 */
@Data
public class ContractDTO {
    /**
     * 合约美元价值
     */
    BigDecimal value;
    /**
     * 币种pairCode
     */
    String pairCode;
    /**
     * 合约最大杠杆
     */
    BigDecimal maxLeverage;
    /**
     * 维持保证金率
     * maintenanceMarginRate
     */
    BigDecimal maintenanceMarginRate = BigDecimal.ZERO;
    /**
     *
     */
    BigDecimal maintMarginWithFunding = BigDecimal.ZERO;
}