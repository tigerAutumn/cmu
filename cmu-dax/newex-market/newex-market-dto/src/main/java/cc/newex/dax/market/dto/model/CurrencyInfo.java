package cc.newex.dax.market.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author allen
 * @date 2018/03/18
 */
@Data
@Builder
@AllArgsConstructor
public class CurrencyInfo {
    /**
     * 币种id
     */
    private Integer id;
    /**
     * 币种代号 例:CNY BTC LTC
     */
    private String symbol;
    /**
     * 描述
     */
    private String title;
}