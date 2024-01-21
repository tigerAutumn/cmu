package cc.newex.dax.market.service.impl;

import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.dax.market.criteria.MarketIndexRecordExample;
import cc.newex.dax.market.data.MarketIndexRecordRepository;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.MarketIndexRecord;
import cc.newex.dax.market.service.MarketIndexRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Service("marketIndexRecordService")
public class MarketIndexRecordServiceImpl implements MarketIndexRecordService {
    final static String MARKET_DATA = "market_index_record_";

    @Autowired
    MarketIndexRecordRepository marketIndexRecordRepository;


    @Override
    public long insertMarketIndexRecord(final MarketIndexRecord marketIndexRecord, final CoinConfig coinConfig) {
        return this.marketIndexRecordRepository.insert(marketIndexRecord, this.getShardTable(coinConfig));
    }

    @Override
    public int countMarketIndexRecord(final int marketFrom, final int invalid, final CoinConfig coinConfig) {
        final MarketIndexRecordExample marketIndexRecordExample = new MarketIndexRecordExample();
        final MarketIndexRecordExample.Criteria criteria = marketIndexRecordExample.createCriteria();
        criteria.andMarketFromEqualTo(marketFrom);
        criteria.andCreatedDateGreaterThan(this.getAddHoursTime(-10));
        //1 查无效、 0 查全部
        if (invalid == 1) {
            criteria.andInvalidEqualTo(invalid);
        }
        return this.marketIndexRecordRepository.countByExample(marketIndexRecordExample, this.getShardTable(coinConfig));
    }

    @Override
    public void deleteMarketIndexRecordBefore24Hour(final CoinConfig coinConfig) {
        final MarketIndexRecordExample marketIndexRecordExample = new MarketIndexRecordExample();
        final MarketIndexRecordExample.Criteria criteria = marketIndexRecordExample.createCriteria();
        criteria.andCreatedDateLessThan(DateUtil.addDays(new Date(), -2));
        this.marketIndexRecordRepository.deleteByExample(marketIndexRecordExample, this.getShardTable(coinConfig));
    }

    @Override
    public long batchInsertMarketIndexRecord(List<MarketIndexRecord> records, CoinConfig coinConfig) {
        return this.marketIndexRecordRepository.batchInsert(records, this.getShardTable(coinConfig));
    }

    private ShardTable getShardTable(final CoinConfig coinConfig) {
        final ShardTable shardTable = new ShardTable();
        shardTable.setName(MarketIndexRecordServiceImpl.MARKET_DATA + coinConfig.getIndexMarketFrom());
        return shardTable;
    }

    private Date getAddHoursTime(final int hours) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, Calendar.HOUR + hours);
        return cal.getTime();
    }
}
