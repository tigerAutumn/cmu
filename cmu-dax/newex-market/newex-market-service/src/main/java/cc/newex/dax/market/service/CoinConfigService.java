package cc.newex.dax.market.service;

import cc.newex.dax.market.domain.CoinConfig;

import java.util.List;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface CoinConfigService {

    List<CoinConfig> getAllCoinConfigList();

    List<CoinConfig> getAllCoinConfigListFromCache();

    List<CoinConfig> getAllPortfolioCoinConfigList();

    List<CoinConfig> getAllPortfolioCoinConfigListFromCache();

    List<CoinConfig> getCompletedPortfolioListFromCache();

    CoinConfig getAllCoinConfigBySymbol(Integer symbol);

    CoinConfig getAllCoinConfigById(Long id);

    int deleteCoinConfig(Long id);

    int addCoinConfig(CoinConfig model);

    int updateCoinConfig(CoinConfig model);

    CoinConfig getCoinConfigBySymbolCode(Integer symbol);

    CoinConfig getCoinConfigBySymbolName(String symbolName);

    void putCoinConfigToRedis();
}
