package cc.newex.dax.market.service;

import cc.newex.dax.market.domain.MarketProperties;
import cc.newex.dax.market.dto.model.PortfolioProperties;

import java.util.List;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface MarketPropertiesService {


    MarketProperties getMarketProperties(String key);

    MarketProperties getMarketPropertiesById(Long id);

    List<MarketProperties> selectAll();


    int addMarketProperties(MarketProperties model);

    int updateMarketProperties(MarketProperties model);

    int deleteMarketProperties(Long id);

    PortfolioProperties getPortfolioProperties(Integer symbol);
}
