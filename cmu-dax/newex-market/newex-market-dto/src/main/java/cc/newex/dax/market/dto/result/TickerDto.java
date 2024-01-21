package cc.newex.dax.market.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author allen
 * @date 2018/5/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TickerDto implements Serializable {


    private static final long serialVersionUID = -7987364589088495316L;
    /**
     * 开盘价
     */
    private String open;

    /**
     * 最高价
     */
    private String high;

    /**
     * 最低价
     */
    private String low;

    /**
     * 最新价
     */
    private String last;

    /**
     * 涨跌幅
     */
    private String changeRate;

    /**
     * 币种
     */
    private String currencyCode;
}
