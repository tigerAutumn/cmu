package cc.newex.dax.extra.service.wiki;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * ratingToken的业务接口
 *
 * @author better
 * @date create in 2018-12-03 11:08
 */
public interface RatingTokenService {


    /**
     * 获取代币列表,包含cid等信息
     *
     * @param lang 语言
     * @return the rt rank list, back data => <p>https://openapi.ratingtoken.io/api/token/ICORankList.html</p>
     */
    JSONArray getRankList(String lang);


    /**
     * 获取代币的基础信息
     *
     * @param cid 代币ID
     * @return the rt base info, back data => <p>https://openapi.ratingtoken.io/api/token/info.html</p>
     */
    JSONObject getBaseInfo(String cid);


    /**
     * 获取代币的份数信息
     *
     * @param cid 代币ID
     * @return the rt score, back data => <p>https://openapi.ratingtoken.io/api/token/score.html</p>
     */
    JSONObject getScoreInfo(String cid);

    /**
     * 获取代币团队信息
     *
     * @param cid 代币ID
     * @return the rt team, back data => <p>https://openapi.ratingtoken.io/api/token/teamList.html</p>
     */
    JSONArray getTeamList(String cid);


    /**
     * 获取代币价格信息
     *
     * @param cid      代币id
     * @param category 数据类型, 支持:day,week,month 粒化分别为10分钟,1小时,6小时
     * @return the rt price, back data => <p>https://openapi.ratingtoken.io/api/token/exchange.html</p>
     */
    JSONArray getExchangeCharts(String cid, String category);

    /**
     * 获取社交热度信息
     *
     * @param cid      代币ID
     * @param category 数据来源, 支持: facebook twitter telegram
     * @return rt social heat, back data => <p>https://openapi.ratingtoken.io/api/token/community.html</p>
     */
    JSONArray getCommunityCharts(String cid, String category);

    /**
     * 获取Github信息
     *
     * @param cid 代币ID
     * @return rt rt github, back data => <p>https://openapi.ratingtoken.io/api/token/github.html</p>
     */
    JSONArray getGithubCharts(String cid);


    /**
     * 获取情感评分
     *
     * @param cid 代币ID
     * @return rt rt github, back data => <p>https://openapi.ratingtoken.io/api/sentiment/score.html</p>
     */
    JSONObject getSentimentScore(String cid);

    /**
     * 获取情感得分社交数据
     *
     * @param cid       代币ID
     * @param category  数据类型, 支持: all facebook twitter telegram
     * @param timeDelta 时间维度, 可选值(7,14,30), 默认每7天一个数据点
     * @return rt rt github, back data => <p>https://openapi.ratingtoken.io/api/sentiment/community.html</p>
     */
    JSONArray getSentimentCommunityCharts(String cid, String category, Integer timeDelta);
}
