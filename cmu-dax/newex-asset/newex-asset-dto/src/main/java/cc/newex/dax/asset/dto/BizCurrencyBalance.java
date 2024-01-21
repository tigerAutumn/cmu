package cc.newex.dax.asset.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 2018/6/28
 */
@Data
@NoArgsConstructor
public class BizCurrencyBalance {
    private Integer currency;
    private BigDecimal userBalance;
    private Integer biz;

    public BizCurrencyBalance self() {
        return this;
    }
}
