package cc.newex.dax.market.service.impl;

import cc.newex.commons.dictionary.consts.CacheKeys;
import cc.newex.dax.market.domain.Currency;
import cc.newex.dax.market.service.CurrencyService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 币种表 服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<Currency> selectCurrencyToRedis() {
        return JSONObject.parseArray(
                this.redisTemplate.opsForValue().
                        get(CacheKeys.getCurrencyKey()), Currency.class);
    }

    @Override
    public Optional<Currency> getCurrencyById(final byte id) {
        return this.selectCurrencyToRedis().stream()
                .filter(x -> x.getId() == id)
                .findAny();
    }

}