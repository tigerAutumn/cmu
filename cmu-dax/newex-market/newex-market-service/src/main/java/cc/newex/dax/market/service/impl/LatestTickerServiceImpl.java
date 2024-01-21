package cc.newex.dax.market.service.impl;

import cc.newex.commons.dictionary.consts.CacheKeys;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.consts.LatestTickerConst;
import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import cc.newex.dax.market.criteria.LatestTickerExample;
import cc.newex.dax.market.data.LatestTickerRepository;
import cc.newex.dax.market.domain.LatestTicker;
import cc.newex.dax.market.service.LatestTickerService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class LatestTickerServiceImpl
        extends AbstractCrudService<LatestTickerRepository, LatestTicker, LatestTickerExample, Long>
        implements LatestTickerService {
    @Autowired
    private LatestTickerRepository latestTickerRepos;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    protected LatestTickerExample getPageExample(final String fieldName, final String keyword) {
        final LatestTickerExample example = new LatestTickerExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public ResponseResult updateTickerByMarketFrom(final LatestTicker ticker, final int marketFrom) {
        final LatestTickerExample example = new LatestTickerExample();
        example.createCriteria().andMarketFromEqualTo(marketFrom);

        final LatestTicker oldTicker = this.latestTickerRepos.selectOneByExample(example);
        if (oldTicker == null) {
            log.error("updateLatestTicker data not exist! marketFrom: {}", Integer.toString(marketFrom));
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_DATA_NOT_FOUND);
        }

        final long tickerId = oldTicker.getId();
        ticker.setId(tickerId);
        if (oldTicker.getAuto() != null && oldTicker.getAuto() == LatestTickerConst.MODE_MANUAL) {
            ticker.setBuy(oldTicker.getBuy());
            ticker.setSell(oldTicker.getSell());
        }
        // 避免1份数据需要更新两条记录的的情况。
        ticker.setMarketFrom(marketFrom);
        ticker.setCreatedDate(new Date());

        if (this.latestTickerRepos.updateById(ticker) <= 0) {
            log.error("updateLatestTicker failure! ticker: {}, marketFrom: {}", JSONObject.toJSONString(ticker),
                    Integer.toString(marketFrom));
            return ResultUtils.failure(BizErrorCodeEnum.SYSTEM_ERROR);
        }

        // 重新获取最新值并存入到缓存中。
        final String cacheKey = CacheKeys.getKey4LatestTickerMarketfrom(marketFrom);
        final LatestTicker ticker2Cache = this.latestTickerRepos.selectById(tickerId);
        this.redisTemplate.opsForValue().set(cacheKey, JSONObject.toJSONString(ticker2Cache));

        return ResultUtils.success();
    }

    @Override
    public List<LatestTicker> getAllTickerToRedis() {
        return LatestTicker.parseListFromJSON(
                this.redisTemplate.opsForValue().get(CacheKeys.getKey4LatestTickerAll()));
    }

    @Override
    public void putTickerRedis() {
        final List<LatestTicker> fetchingDataPaths = this.latestTickerRepos.selectFiatTickers();
        this.redisTemplate.opsForValue().set(CacheKeys.getKey4LatestTickerAll(),
                JSONObject.toJSONString(fetchingDataPaths));
    }


    @Override
    public List<LatestTicker> getLatestTickerFromMarketFromArray(final int[] marketFrom) {
        if (marketFrom == null || marketFrom.length == 0) {
            return null;
        }
        final ArrayList<Integer> marketFromList = new ArrayList<>();
        for (final int i : marketFrom) {
            marketFromList.add(i);
        }

        final LatestTickerExample example = new LatestTickerExample();
        example.createCriteria().andMarketFromIn(marketFromList);
        final List<LatestTicker> tickers = this.latestTickerRepos.selectByExample(example);
        if (CollectionUtils.isEmpty(tickers)) {
            return null;
        }
        return tickers;
    }


    @Override
    public List<LatestTicker> getValidTickerLessThanTime(final Date dateInMinuteAgo) {
        final LatestTickerExample example = new LatestTickerExample();
        example.createCriteria().andCreatedDateLessThan(dateInMinuteAgo).andValidEqualTo(1);
        example.setOrderByClause("created_date desc");
        return this.latestTickerRepos.selectByExample(example);
    }


}