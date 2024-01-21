package cc.newex.dax.market.dto.result;

import cc.newex.dax.market.dto.model.IndexPortfolioConfigDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by fang on 2018/7/26.
 */
@Data
@Builder
@AllArgsConstructor
public class PortfolioResultDto {
    private List<IndexPortfolioConfigDTO> portfolioConfigList;
    private Double pts;
    private Double usdt;
}
