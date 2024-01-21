package cc.newex.dax.extra.dto.wiki;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 币种社交信息DTO
 *
 * @author better
 * @date create in 2018/11/27 6:25 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RtCurrencySocialInfoDTO implements RtCurrencyInfoDTO {

    /**
     * 社交热度
     */
    private List<CommunityChartsDTO> communityCharts;

    /**
     * 情感得分
     */
    private SentimentScoreDTO emotionScore;

    /**
     * 情感得分社交热度
     */
    private List<SentimentCommunityChartsDTO> sentimentCommunityCharts;

    /**
     * 社交热度DTO
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommunityChartsDTO {

        /**
         * 粉丝数
         */
        private Integer fans;

        /**
         * 活跃度
         */
        @JSONField(name = "liveness")
        private Float active;

        /**
         * 得分
         */
        private Float ranking;

        /**
         * 日期
         */
        private Date date;
    }


    /**
     * 情感得分
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SentimentScoreDTO {

        /**
         * 得分
         */
        private Float score;

        /**
         * 好评数
         */
        private Integer goodComment;

        /**
         * 差评数
         */
        private Integer badComment;

        /**
         * 日期
         */
        private Date date;
    }

    /**
     * 情感得分社交热度
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SentimentCommunityChartsDTO {

        /**
         * 分数
         */
        public Float score;

        /**
         * 好评度
         */
        public Integer goodComment;

        /**
         * 差评度
         */
        public Integer badComment;

        /**
         * 时间
         */
        private Date date;
    }
}
