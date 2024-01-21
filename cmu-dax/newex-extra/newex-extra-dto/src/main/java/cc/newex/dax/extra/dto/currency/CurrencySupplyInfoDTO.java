package cc.newex.dax.extra.dto.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 币种的流通量、流通市值信息
 *
 * @author liutiejun
 * @date 2018-07-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencySupplyInfoDTO {

    /**
     * 币种的唯一标识
     */
    private String tokeninsightID;

    /**
     * 币种名称
     */
    private String name;

    /**
     * 币种简称
     */
    private String abbreviate;

    /**
     * 流通量
     */
    private String supply;

    /**
     * 流通率
     */
    private String supplyRate;

    /**
     * 流通市值（单位是美元，USD）
     */
    private String marketCap;

    /**
     * 全网占比
     */
    private String rate;
    
}
