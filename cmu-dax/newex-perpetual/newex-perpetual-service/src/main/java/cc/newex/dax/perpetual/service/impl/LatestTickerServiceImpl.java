package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.dictionary.consts.CacheKeys;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.converter.LatestTickerConverter;
import cc.newex.dax.perpetual.common.push.PushData;
import cc.newex.dax.perpetual.criteria.LatestTickerExample;
import cc.newex.dax.perpetual.data.LatestTickerRepository;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.LatestTicker;
import cc.newex.dax.perpetual.dto.LatestTickerDTO;
import cc.newex.dax.perpetual.service.LatestTickerService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import cc.newex.dax.perpetual.util.ObjectCopyUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 最新报价 服务实现
 *
 * @author newex-team
 * @date 2018-11-16 13:24:53
 */
@Slf4j
@Service
public class LatestTickerServiceImpl
        extends AbstractCrudService<LatestTickerRepository, LatestTicker, LatestTickerExample, Long>
        implements LatestTickerService {
    // push key
    private static final String TICKER_PUSH = "TICKER_PUSH_";

    @Autowired
    private CacheService cacheService;

    @Override
    protected LatestTickerExample getPageExample(final String fieldName, final String keyword) {
        final LatestTickerExample example = new LatestTickerExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public void putTickerRedis(final LatestTicker latestTicker) {
        this.cacheService.setCacheValue(getTickerPrefix(latestTicker.getContractCode()),
                JSON.toJSONString(latestTicker));
    }

    @Override
    public LatestTicker getTickerRedis(final Contract contract) {
        final String jsonStr =
                this.cacheService.getCacheValue(getTickerPrefix(contract.getContractCode()));

        final LatestTicker latestTicker = JSONObject.parseObject(jsonStr, LatestTicker.class);

        return latestTicker;
    }

    @Override
    public void putPushTickerRedis(final LatestTicker latestTicker) {
        final LatestTickerDTO tickerBean = ObjectCopyUtil.map(latestTicker, LatestTickerDTO.class);
        final String tickerJson = JSON.toJSONString(tickerBean);
        this.cacheService.setCacheValue(TICKER_PUSH + tickerBean.getContractCode(), tickerJson);
    }

    /**
     * 返回ticker
     */
    private static String getTickerPrefix(final String contractCode) {
        return new StringBuilder()
                .append(PerpetualConstants.PERPETUAL)
                .append(CacheKeys.DELIMITER)
                .append(CacheKeys.TICKER_PREFIX)
                .append(CacheKeys.DELIMITER)
                .append(contractCode)
                .toString()
                .toUpperCase();
    }

    @Override
    public void pushRedisTicker(final Contract contract, final LatestTicker latestTicker) {
        final PushData pushData = new PushData();
        pushData.setBiz(PerpetualConstants.PERPETUAL);
        //pushData.setContractCode(contract.getContractCode());
        pushData.setType("TICKERS");
        pushData.setZip(false);
        pushData.setData(LatestTickerConverter.convert(latestTicker, contract));
        this.cacheService.convertAndSend(
                (PerpetualConstants.PERPETUAL + "_" + contract.getPairCode()).toUpperCase(),
                JSONObject.toJSONString(pushData));
    }
}
