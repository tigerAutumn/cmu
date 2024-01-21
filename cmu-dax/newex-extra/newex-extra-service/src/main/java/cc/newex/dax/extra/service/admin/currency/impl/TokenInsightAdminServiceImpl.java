package cc.newex.dax.extra.service.admin.currency.impl;

import cc.newex.dax.extra.common.util.StringFormatter;
import cc.newex.dax.extra.domain.tokenin.CurrencyLevelInfo;
import cc.newex.dax.extra.domain.tokenin.CurrencySupplyInfo;
import cc.newex.dax.extra.domain.tokenin.TokeninLevelResponse;
import cc.newex.dax.extra.domain.tokenin.TokeninSupplyResponse;
import cc.newex.dax.extra.service.admin.currency.TokenInsightAdminService;
import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author liutiejun
 * @date 2018-07-18
 */
@Slf4j
@Service
public class TokenInsightAdminServiceImpl implements TokenInsightAdminService {

    private static final String coinLevelUrl = "https://tokeninsight.com/api/user/web/getCoinLevel?" +
            "tokeninsightId={}&language={}&sourceId={}";

    private static final String supplyUrl = "https://tokeninsight.com/api/user/web/getSupply?tokeninsightId={}&sourceId={}";

    /**
     * 合作方ID，由TokenInsight提供
     */
    private static final String sourceId = "9862";

    static {
        Unirest.setTimeouts(2000, 5000);
    }

    @Override
    public CurrencyLevelInfo getLevelInfo(final String shortName, final String locale) {
        if (StringUtils.isBlank(shortName)) {
            return null;
        }

        // 可选参数为cn和en，分别返回中英文数据
        String language = "en";
        if ("zh-cn".equalsIgnoreCase(locale)) {
            language = "cn";
        }

        final String requestUrl = StringFormatter.format(coinLevelUrl, shortName, language, sourceId);

        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = Unirest.get(requestUrl).asString();
        } catch (final UnirestException e) {
            log.warn(requestUrl + " : " + e.getMessage(), e);
        }

        if (httpResponse == null) {
            return null;
        }

        final TokeninLevelResponse tokeninLevelResponse = JSON.parseObject(httpResponse.getBody(), TokeninLevelResponse.class);
        if (tokeninLevelResponse == null) {
            return null;
        }

        final Integer code = tokeninLevelResponse.getCode();
        if (code != 30) {
            return null;
        }

        final CurrencyLevelInfo currencyLevelInfo = tokeninLevelResponse.getData();
        if (currencyLevelInfo == null) {
            return null;
        }

        String levelResult = currencyLevelInfo.getLevelResult();
        if (StringUtils.isBlank(levelResult)) {
            return null;
        }

        levelResult = levelResult.toUpperCase();

        // 只显示A、B级别的评级信息
        if (StringUtils.startsWithAny(levelResult, "A", "B")) {
            return currencyLevelInfo;
        }

        return null;
    }

    @Override
    public CurrencySupplyInfo getSupplyInfo(final String shortName) {
        if (StringUtils.isBlank(shortName)) {
            return null;
        }

        final String requestUrl = StringFormatter.format(supplyUrl, shortName, sourceId);

        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = Unirest.get(requestUrl).asString();
        } catch (final UnirestException e) {
            log.warn(requestUrl + " : " + e.getMessage(), e);
        }

        if (httpResponse == null) {
            return null;
        }

        final TokeninSupplyResponse tokeninSupplyResponse = JSON.parseObject(httpResponse.getBody(), TokeninSupplyResponse.class);
        if (tokeninSupplyResponse == null) {
            return null;
        }

        final Integer code = tokeninSupplyResponse.getCode();
        if (code != 30) {
            return null;
        }

        return tokeninSupplyResponse.getData();
    }

}
