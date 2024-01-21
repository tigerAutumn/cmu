package cc.newex.dax.market.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author allen
 * @date 2018/5/21
 * @des 汇率
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateDto implements Serializable {
    private static final long serialVersionUID = 4380009314861143673L;

    /**
     * 汇率类型
     */
    private String rateType;

    /**
     * 汇率
     */
    private Double rate;
}
