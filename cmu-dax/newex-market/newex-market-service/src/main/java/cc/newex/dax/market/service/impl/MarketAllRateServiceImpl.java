package cc.newex.dax.market.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.consts.MarketAllRateConst;
import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import cc.newex.dax.market.common.enums.RedisKeyEnum;
import cc.newex.dax.market.criteria.MarketAllRateExample;
import cc.newex.dax.market.data.MarketAllRateRepository;
import cc.newex.dax.market.domain.MarketAllRate;
import cc.newex.dax.market.service.MarketAllRateService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class MarketAllRateServiceImpl
        extends AbstractCrudService<MarketAllRateRepository, MarketAllRate, MarketAllRateExample, Long>
        implements MarketAllRateService {

    @Autowired
    private MarketAllRateRepository marketAllRateRepos;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void putRateRedis(final String name) {
        final List<MarketAllRate> allMarket = this.marketAllRateRepos.getMarketAllRateTwoWeekList(name);
        this.stringRedisTemplate.opsForValue().set(RedisKeyEnum.REDIS_KEY_NEWRATEALL.getKey() + name,
                JSONObject.toJSONString(allMarket));
        MarketAllRateServiceImpl.log.info("msg:汇率存到redis");

    }

    @Override
    public BigDecimal selectPairAcg2Week(final String name) {

        final Date date = new Date();
        final Date date1 = DateUtils.addDays(date, -14);
        return this.marketAllRateRepos.selectPairAvg(name, date1);
    }

    @Override
    protected MarketAllRateExample getPageExample(final String fieldName, final String keyword) {
        final MarketAllRateExample example = new MarketAllRateExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public ResponseResult updateMarketRate(final MarketAllRate marketRate) {
        final MarketAllRateExample example = new MarketAllRateExample();
        example.createCriteria().andRateNameEqualTo(marketRate.getRateName());

        final MarketAllRate oldMarketRate = this.getOneByExample(example);
        if (oldMarketRate == null || oldMarketRate.getStatus() == MarketAllRateConst.STATUS_NO) {
            MarketAllRateServiceImpl.log.error("updateMarketRate data with rate name not exist or status is 0! marketRate to update: {}",
                    JSONObject.toJSONString(marketRate));
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_DATA_NOT_FOUND);
        }

        marketRate.setId(oldMarketRate.getId());
        marketRate.setStatus(oldMarketRate.getStatus());
        marketRate.setModifyTime(new Date());

        if (this.editById(marketRate) <= 0) {
            MarketAllRateServiceImpl.log.error("updateMarketRate failure! marketAllRate: {} ", JSONObject.toJSONString(marketRate));
            return ResultUtils.failure(BizErrorCodeEnum.SYSTEM_ERROR);
        }

        return ResultUtils.success();
    }

    @Override
    public MarketAllRate getRateByName(final String name) {

        return this.marketAllRateRepos.getRateByName(name);
    }

    @Override
    public MarketAllRate getRateByNameOrderBy(final String name) {
        return this.marketAllRateRepos.getRateByNameOrderBy(name);
    }

    @Override
    public List<MarketAllRate> getMarketAllRateTwoWeekList(final String name) {
        final List<MarketAllRate> result;
        //从缓存查询数据，
        final String marketAllRats = this.stringRedisTemplate.opsForValue().get(RedisKeyEnum.REDIS_KEY_NEWRATEALL.getKey() + name);
        if (StringUtils.isBlank(marketAllRats)) {
            result = this.marketAllRateRepos.getMarketAllRateTwoWeekList(name);
        } else {
            result = JSONObject.parseArray(marketAllRats, MarketAllRate.class);
        }
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        if (result.size() < 9) {
            return result;
        }

        return result.subList(0, 9);
    }

    @Override
    public List<MarketAllRate> getMarketAllRateTwoWeekListUSD_CNY(final String name) {
        return this.marketAllRateRepos.getMarketAllRateTwoWeekListUSD_CNY(name);
    }

}