package cc.newex.dax.market.service.impl;

import cc.newex.dax.market.common.enums.RedisKeyEnum;
import cc.newex.dax.market.criteria.MarketPropertiesExample;
import cc.newex.dax.market.data.MarketPropertiesRepository;
import cc.newex.dax.market.domain.MarketProperties;
import cc.newex.dax.market.dto.model.PortfolioProperties;
import cc.newex.dax.market.service.MarketPropertiesService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 配置信息
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Service("marketPropertiesService")
@Slf4j
public class MarketPropertiesServiceImpl implements MarketPropertiesService {

    @Autowired
    MarketPropertiesRepository marketPropertiesRepository;
    @Autowired
    private RedisService redisService;

    @Override
    public MarketProperties getMarketProperties(final String key) {
        final MarketPropertiesExample marketPropertiesExample = new MarketPropertiesExample();
        final MarketPropertiesExample.Criteria criteria = marketPropertiesExample.createCriteria();
        criteria.andPropKeyEqualTo(key);
        return this.marketPropertiesRepository.selectOneByExample(marketPropertiesExample);
    }



    private String[] convertVal(final JSONArray jsonArray) {
        if (jsonArray != null && jsonArray.size() > 0) {
            final String[] returnString = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                returnString[i] = jsonArray.get(i).toString();
            }
            return returnString;
        }
        return null;
    }

    @Override
    public int addMarketProperties(final MarketProperties model) {

        return this.marketPropertiesRepository.insert(model);
    }

    @Override
    public int updateMarketProperties(final MarketProperties model) {

        return this.marketPropertiesRepository.updateById(model);
    }

    @Override
    public List<MarketProperties> selectAll() {

        return this.marketPropertiesRepository.selectAll();
    }

    @Override
    public MarketProperties getMarketPropertiesById(final Long id) {

        return this.marketPropertiesRepository.selectById(id);
    }

    @Override
    public int deleteMarketProperties(final Long id) {

        return this.marketPropertiesRepository.deleteById(id);
    }

    @Override
    public PortfolioProperties getPortfolioProperties(Integer symbol) {
        String cacheKey = String.format("portfolio_properties_%s", symbol);

        Object o = redisService.getInfo(cacheKey);
        if (o != null) {
            return JSON.parseObject((String) o, PortfolioProperties.class);
        }

        //初始化默认配置
        //没有配置走默认的配置
        PortfolioProperties portfolioProperties = PortfolioProperties.builder().pts(1000D).usd(1D).build();

        MarketProperties marketProperties = this.getMarketProperties(cacheKey);
        if (marketProperties != null) {
            portfolioProperties = JSON.parseObject(marketProperties.getPropValue(), PortfolioProperties.class);
        }

        redisService.setCacheValueExpireTime(cacheKey, JSON.toJSONString(portfolioProperties), 1, TimeUnit.MINUTES);
        return portfolioProperties;
    }

}