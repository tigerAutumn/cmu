package cc.newex.dax.market.job.task;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.dax.market.criteria.LatestTickerExample;
import cc.newex.dax.market.criteria.MarketAllRateExample;
import cc.newex.dax.market.criteria.MarketDataExample;
import cc.newex.dax.market.data.LatestTickerRepository;
import cc.newex.dax.market.data.MarketAllRateRepository;
import cc.newex.dax.market.data.MarketDataRepository;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.LatestTicker;
import cc.newex.dax.market.domain.MarketAllRate;
import cc.newex.dax.market.domain.MarketData;
import cc.newex.dax.market.dto.model.IndexPortfolioConfigDTO;
import cc.newex.dax.market.dto.model.PortfolioInfo;
import cc.newex.dax.market.job.service.CoinService;
import cc.newex.dax.market.job.service.MsgSendService;
import cc.newex.dax.market.service.CoinConfigService;
import cc.newex.dax.market.service.MarketAllRateService;
import cc.newex.dax.market.service.MarketIndexRecordService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/7/23
 */
@Slf4j
@JobHandler(value = "IndexAlarmXxlJob")
@Component
public class IndexAlarmXxlJob extends IJobHandler {

    @Autowired
    private MarketAllRateService marketAllRateService;
    @Autowired
    private MsgSendService msgSendService;

    @Autowired
    private LatestTickerRepository latestTickerRepos;
    @Autowired
    private MarketDataRepository marketDataRepository;
    @Autowired
    private CoinConfigService coinConfigService;

    private long lastTime;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            log.error("params is null");
            return IJobHandler.SUCCESS;
        }


        MarketAllRate marketAllRate = marketAllRateService.getRateByNameOrderBy("usd_cny");

        StringBuffer stringBuffer = new StringBuffer();
        if (isNotSameDay(marketAllRate.getCreateTime())) {
            //说明汇率未更新
            stringBuffer.append("汇率,");

        }

        coinConfigService.getAllCoinConfigList().forEach(coinConfig -> {
            //检查单一指数内部成份数据
            int[] marketFrom = coinConfig.getMarketFromArray();
            if (marketFrom == null) {
                stringBuffer.append(coinConfig.getSymbol() + "指数marketFrom为空,");
                return;
            }
            for (int mf : marketFrom) {
                LatestTickerExample tickerExample = new LatestTickerExample();
                tickerExample.createCriteria()
                        .andMarketFromEqualTo(mf);
                LatestTicker latestTicker = latestTickerRepos.selectOneByExample(tickerExample);
                if (!isSameMinute(latestTicker.getCreatedDate())) {
                    stringBuffer.append(coinConfig.getSymbolName() + "成份"+latestTicker.getName()+",");
                }
            }
            //检查单一指数K线
            checkKline(stringBuffer, coinConfig, coinConfig.getSymbol(), "指数K线,");

        });


        coinConfigService.getAllPortfolioCoinConfigListFromCache().forEach(coinConfig -> {
            //检查组合指数成份数据
            PortfolioInfo portfolioInfo = coinConfig.getPortfolioInfo();
            List<IndexPortfolioConfigDTO> portfolioInfos = portfolioInfo.getPortfolioConfigList();
            if (CollectionUtils.isEmpty(portfolioInfos)) {
                stringBuffer.append(coinConfig.getSymbol() + "组合MarketFrom为空,");
            }
            portfolioInfos.forEach(indexPortfolioConfigDTO -> {
                checkKline(stringBuffer, coinConfig, indexPortfolioConfigDTO.getSymbol(), "成份K线,");
            });
            //检查组合指数K线
            checkKline(stringBuffer, coinConfig, coinConfig.getSymbol(), "组合K线,");

        });

        long currentTime1 = System.currentTimeMillis();
        if ((currentTime1 - lastTime) >= 1000 * 60) {
            System.out.println(currentTime1 - lastTime);
            lastTime = currentTime1;
        }

        if (stringBuffer.length() > 0) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastTime) >= 1000 * 60) {
                stringBuffer.append("未更新");
                msgSendService.sendSMS("[指数服务]" + stringBuffer.toString(), s);
                lastTime = currentTime;
            }
        }

        return IJobHandler.SUCCESS;
    }

    private void checkKline(StringBuffer stringBuffer, CoinConfig coinConfig, int mf, String s) {
        MarketDataExample marketDataExample = new MarketDataExample();
        marketDataExample.setOrderByClause("id desc limit 1");
        List<MarketData> marketDataList = marketDataRepository.selectByExample(marketDataExample, ShardTable.builder().name("market_data_" + mf).build());
        if (CollectionUtils.isEmpty(marketDataList)) {
            stringBuffer.append(coinConfig.getSymbolName() + s);
        } else {
            MarketData marketData = marketDataList.get(0);
            if (!isSameMinute(marketData.getCreatedDate())) {
                stringBuffer.append(coinConfig.getSymbolName() + s);
            }
        }
    }

    private boolean isNotSameDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DATE);
        Calendar current = Calendar.getInstance();
        current.setTime(date);
        int lastDay = current.get(Calendar.DATE);
        return today != lastDay;
    }

    private boolean isSameMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        int currentMinute = calendar.get(Calendar.MINUTE);
        Calendar current = Calendar.getInstance();
        current.setTime(date);
        int lastMinute = current.get(Calendar.MINUTE);
        return currentMinute < lastMinute + 3;
    }

}