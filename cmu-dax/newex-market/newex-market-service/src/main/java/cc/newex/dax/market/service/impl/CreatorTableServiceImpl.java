package cc.newex.dax.market.service.impl;

import cc.newex.dax.market.data.CreatorTableRepository;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.service.CreatorTableService;
import cc.newex.dax.market.data.CreatorTableRepository;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.service.CreatorTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Service("creatorTableService")
public class CreatorTableServiceImpl implements CreatorTableService {
    @Autowired
    CreatorTableRepository creatorTableRepository;

    @Override
    public void checkMarketDataOrCreate(CoinConfig coinConfig) {
        int count = creatorTableRepository.checkTableName(coinConfig.getIndexMarketFrom());
        if (count == 0) {
            creatorTableRepository.createMarketData(coinConfig.getIndexMarketFrom());
        }
    }

    @Override
    public void checkMarketIndexRecordOrCreate(CoinConfig coinConfig) {
        int count = creatorTableRepository.checkTableName(coinConfig.getIndexMarketFrom());
        if (count == 0) {
            creatorTableRepository.createMarketIndexRecord(coinConfig.getIndexMarketFrom());
        }
    }
}
