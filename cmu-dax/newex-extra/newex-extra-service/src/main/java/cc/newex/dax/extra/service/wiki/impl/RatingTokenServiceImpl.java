package cc.newex.dax.extra.service.wiki.impl;

import cc.newex.dax.extra.common.util.RatingTokenUtils;
import cc.newex.dax.extra.service.wiki.RatingTokenService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cc.newex.dax.extra.common.enums.RatingTokenTemplateEnum.*;

/**
 * The type Rating token service.
 *
 * @author better
 * @date create in 2018-12-03 11:08
 */
@Service
@Slf4j
public class RatingTokenServiceImpl implements RatingTokenService {

    private final HttpClientConnectionManager httpClientConnectionManager;

    /**
     * Instantiates a new Rating token service.
     *
     * @param httpClientConnectionManager the http client connection manager
     */
    @Autowired
    public RatingTokenServiceImpl(final HttpClientConnectionManager httpClientConnectionManager) {
        this.httpClientConnectionManager = httpClientConnectionManager;
    }

    @Override
    public JSONArray getRankList(final String lang) {
        final String gmtDateStr = getGmtDateStr();
        final String url = String.format(RT_RANK_LIST_URL.getTemplate(), lang);

        return RatingTokenUtils.checkResponseAndGetArrayData(this.executeRtClient(this.httpClientConnectionManager, url, gmtDateStr));
    }

    @Override
    public JSONObject getBaseInfo(final String cid) {
        final String gmtDateStr = getGmtDateStr();
        final String url = String.format(RT_INFO_URL.getTemplate(), cid);

        return RatingTokenUtils.checkResponseAndGetData(this.executeRtClient(this.httpClientConnectionManager, url, gmtDateStr));
    }

    @Override
    public JSONObject getScoreInfo(final String cid) {
        final String gmtDateStr = getGmtDateStr();
        final String url = String.format(RT_SCORE_URL.getTemplate(), cid);

        return RatingTokenUtils.checkResponseAndGetData(this.executeRtClient(this.httpClientConnectionManager, url, gmtDateStr));
    }

    @Override
    public JSONArray getTeamList(final String cid) {
        final String gmtDateStr = getGmtDateStr();
        final String url = String.format(RT_TEAM_URL.getTemplate(), cid);

        return RatingTokenUtils.checkResponseAndGetArrayData(this.executeRtClient(this.httpClientConnectionManager, url, gmtDateStr));
    }

    @Override
    public JSONArray getExchangeCharts(final String cid, final String category) {
        final String gmtDateStr = getGmtDateStr();
        final String url = String.format(RT_PRICE_URL.getTemplate(), cid, category);

        return RatingTokenUtils.checkResponseAndGetArrayData(this.executeRtClient(this.httpClientConnectionManager, url, gmtDateStr));
    }

    @Override
    public JSONArray getCommunityCharts(final String cid, final String category) {
        final String gmtDateStr = getGmtDateStr();
        final String url = String.format(RT_SOCIAL_URL.getTemplate(), cid, category);

        return RatingTokenUtils.checkResponseAndGetArrayData(this.executeRtClient(this.httpClientConnectionManager, url, gmtDateStr));
    }

    @Override
    public JSONArray getGithubCharts(final String cid) {
        final String gmtDateStr = getGmtDateStr();
        final String url = String.format(RT_GITHUB_URL.getTemplate(), cid);

        return RatingTokenUtils.checkResponseAndGetArrayData(this.executeRtClient(this.httpClientConnectionManager, url, gmtDateStr));
    }

    @Override
    public JSONObject getSentimentScore(final String cid) {
        final String gmtDateStr = getGmtDateStr();
        final String url = String.format(RT_SENTIMENT_SCORE_URL.getTemplate(), cid);

        return RatingTokenUtils.checkResponseAndGetData(this.executeRtClient(this.httpClientConnectionManager, url, gmtDateStr));
    }

    @Override
    public JSONArray getSentimentCommunityCharts(final String cid, final String category, final Integer timeDelta) {
        final String gmtDateStr = getGmtDateStr();
        final String url = String.format(RT_SENTIMENT_COMMUNITY_CHARTS_URL.getTemplate(), cid, category);

        return RatingTokenUtils.checkResponseAndGetArrayData(this.executeRtClient(this.httpClientConnectionManager, url, gmtDateStr));
    }

    private String executeRtClient(final HttpClientConnectionManager httpClientConnectionManager, final String url, final String gmtDateStr) {
        final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(httpClientConnectionManager).build();
        final String responseJson;
        try {
            responseJson = RatingTokenUtils.executeRtClient(httpClient, url, gmtDateStr);
        } catch (final Exception e) {
            log.error("executeRtClient is error, e=>{}, url=>{}", e, url);
            return null;
        }
        return responseJson;
    }
}
