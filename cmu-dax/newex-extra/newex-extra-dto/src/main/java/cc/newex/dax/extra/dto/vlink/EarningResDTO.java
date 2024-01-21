package cc.newex.dax.extra.dto.vlink;

import lombok.*;

/**
 * @author gi
 * @date 10/19/18
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EarningResDTO {
    /**
     * 总挖矿收益
     */
    private Number grossIncome;
    /**
     * 用户收益
     */
    private Number customerIncome;
    /**
     * 代理商收益
     */
    private Number agencyIncome;
    /**
     * 平台收益
     */
    private Number platformIncome;
    /**
     * 合约Id
     */
    private Number contractId;
    /**
     * 电费
     */
    private Number electricityCost;
    /**
     * email
     */
    private String email;
}
