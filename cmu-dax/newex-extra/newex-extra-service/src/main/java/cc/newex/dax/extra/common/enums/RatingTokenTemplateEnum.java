package cc.newex.dax.extra.common.enums;

import cc.newex.dax.extra.common.consts.RatingTokenConstant;
import cc.newex.dax.extra.common.util.EncryptUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static cc.newex.dax.extra.common.consts.RatingTokenConstant.ACCOUNT;
import static cc.newex.dax.extra.common.consts.RatingTokenConstant.AUTHORIZATION;

/**
 * @author better
 * @date create in 2018/11/29 10:39 AM
 */
@Getter
@AllArgsConstructor
public enum RatingTokenTemplateEnum {

    /**
     * 代币排行url
     */
    RT_RANK_LIST_URL("https://openapi.ratingtoken.io/token/ICORankList?lang=%s"),

    /**
     * 详情url模板
     */
    RT_INFO_URL("https://openapi.ratingtoken.io/token/info?cid=%s"),

    /**
     * 评分url模板
     */
    RT_SCORE_URL("https://openapi.ratingtoken.io/token/score?cid=%s"),

    /**
     * 价格url模板
     */
    RT_PRICE_URL("https://openapi.ratingtoken.io/token/exchangeCharts?cid=%s&category=%s"),

    /**
     * github url模板
     */
    RT_GITHUB_URL("https://openapi.ratingtoken.io/token/githubCharts?cid=%s"),

    /**
     * 团队url模板
     */
    RT_TEAM_URL("https://openapi.ratingtoken.io/token/teamList?cid=%s"),

    /**
     * 社交url模板
     */
    RT_SOCIAL_URL("https://openapi.ratingtoken.io/token/communityCharts?cid=%s&category=%s"),

    /**
     * 情感得分url模板
     */
    RT_SENTIMENT_SCORE_URL("https://openapi.ratingtoken.io/sentiment/score?cid=%s"),

    /**
     * 情感社交得分Url模板
     */
    RT_SENTIMENT_COMMUNITY_CHARTS_URL("https://openapi.ratingtoken.io/sentiment/communityCharts?cid=%s&category=%s");

    /**
     * 模板字符串
     */
    private final String template;

    /**
     * 获取时间
     *
     * @return
     */
    public static String getGmtDateStr() {
        final Calendar cd = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(cd.getTime());
    }

    /**
     * 加密签名
     *
     * @param dateStr
     * @param url
     * @return
     */
    public static String signature(final String dateStr, final String url) {

        final String str = String.format("x-date: %s\nurl: %s", dateStr, url);
        final String signature = EncryptUtils.apacheSha256AndBase64(RatingTokenConstant.SECRET, str);
        return String.format(AUTHORIZATION, ACCOUNT, signature);
    }
}
