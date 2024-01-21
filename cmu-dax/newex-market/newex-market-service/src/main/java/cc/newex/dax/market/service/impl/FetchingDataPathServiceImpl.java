package cc.newex.dax.market.service.impl;

import cc.newex.commons.dictionary.consts.CacheKeys;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.market.criteria.FetchingDataPathExample;
import cc.newex.dax.market.data.FetchingDataPathRepository;
import cc.newex.dax.market.domain.FetchingDataPath;
import cc.newex.dax.market.service.FetchingDataPathService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class FetchingDataPathServiceImpl
    extends AbstractCrudService<FetchingDataPathRepository, FetchingDataPath, FetchingDataPathExample, Long>
    implements FetchingDataPathService {

    @Autowired
    private FetchingDataPathRepository fetchingDataPathRepos;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected FetchingDataPathExample getPageExample(final String fieldName, final String keyword) {
        final FetchingDataPathExample example = new FetchingDataPathExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<FetchingDataPath> selectDataPath() {
        return this.fetchingDataPathRepos.selectDataPath();
    }

    @Override
    public void putAllPathRedis() {
        final List<FetchingDataPath> fetchingDataPaths = this.fetchingDataPathRepos.selectDataPath();
        this.stringRedisTemplate.opsForValue().set(CacheKeys.getFechingDataALLPath(),
            JSONObject.toJSONString(fetchingDataPaths));
        FetchingDataPathServiceImpl.log.info("msg:ticker存到redis");
    }

    @Override
    public void putPathRedis() {
        final List<FetchingDataPath> fetchingDataPaths = this.fetchingDataPathRepos.selectDataPath();
        fetchingDataPaths.stream().forEach(fetchingDataPath -> {
            this.stringRedisTemplate.opsForValue().set(
                CacheKeys.getFechingDataPath(fetchingDataPath.getMarketFrom().toString()),
                JSONObject.toJSONString(fetchingDataPath));
        });
        FetchingDataPathServiceImpl.log.info("msg:ticker存到redis");
    }

    @Override
    public FetchingDataPath getPathRedis(final Integer marketFrom) {

        final FetchingDataPath fetchingDataPath = JSON.parseObject(
                this.stringRedisTemplate
                .opsForValue()
                .get(CacheKeys.getFechingDataPath(marketFrom.toString())),
            FetchingDataPath.class);
        if (fetchingDataPath == null) {
            final FetchingDataPathExample latestTickerExample = new FetchingDataPathExample();
            latestTickerExample.createCriteria().andMarketFromEqualTo(marketFrom);
            return this.getOneByExample(latestTickerExample);
        }
        return fetchingDataPath;
    }

    @Override
    public List<FetchingDataPath> getAllPathRedis() {
        List<FetchingDataPath> latestTickers = JSON.parseArray(this.stringRedisTemplate
            .opsForValue()
            .get(CacheKeys.getFechingDataALLPath()), FetchingDataPath.class);
        if (CollectionUtils.isEmpty(latestTickers)) {
            latestTickers = new ArrayList<>();
        }
        return latestTickers;
    }

}