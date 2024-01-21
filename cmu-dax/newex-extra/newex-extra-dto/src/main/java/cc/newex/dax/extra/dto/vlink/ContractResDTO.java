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
public class ContractResDTO {

    /**
     * 合约Id
     */
    private Number id;
    /**
     * 合约型号
     */
    private String type;
    /**
     * 合约算力
     */
    private Number power;
    /**
     * 合约算力单位
     */
    private Number unit;
    /**
     * 挖矿收益vlink平台分成比例
     */
    private Number platformFeeRate;
    /**
     * 代理收益分成比例
     */
    private Number agencyFeeRate;
    /**
     * 合约销售分成比例
     */
    private Number salesCommissionRate;
    /**
     * 库存
     */
    private Number inventory;
    /**
     * 总量
     */
    private Number capacity;
    /**
     * 电费
     */
    private Number electricity;
    /**
     * BTC/BCH合约产出的虚拟货币币种
     */
    private Number cryptoCurrency;
}
