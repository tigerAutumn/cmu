package cc.newex.dax.market.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by wj on 2018/7/27.
 */
@Data
@Builder
@AllArgsConstructor
public class PortfolioProperties {
    private Double pts;
    private Double usd;
}
