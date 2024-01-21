package cc.newex.dax.extra.dto.wiki;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 币种项目信息DTO
 *
 * @author better
 * @date create in 2018/11/27 6:25 PM
 */
@Data
@Builder
public class RtCurrencyProjectInfoDTO implements RtCurrencyInfoDTO {

    /**
     * 分数
     */
    private ScoreInfoDTO score;

    /**
     * 介绍
     */
    private IntroductionDTO introduction;

    /**
     * 价格
     */
    private List<ExchangeChartsDTO> price;

    /**
     * 团队
     */
    private List<TeamDTO> teams;

    /**
     * github
     */
    private List<GithubChartsDTO> github;

    /**
     * 进展
     */
    private List<ProgressDTO> progress;

    /**
     * 币种评分
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScoreInfoDTO {

        /**
         * 技术评分
         */
        private Float techScore;

        /**
         * 团队得分
         */
        private Float teamScore;

        /**
         * 社交得分
         */
        private Float hypeScore;

        /**
         * 综合得分
         */
        private Float rating;

        /**
         * 评分等级
         */
        private String level;

        /**
         * 评分预期
         */
        private String anticipation;

        /**
         * 评分描述
         */
        private String description;
    }

    /**
     * 币种介绍
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntroductionDTO {

        /**
         * 介绍
         */
        private String introduction;
    }

    /**
     * 币对价格趋势
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExchangeChartsDTO {

        /**
         * 价格
         */
        private Float price;

        /**
         * 成交额-24小时
         */
        @JSONField(name = "volume_24h")
        private Integer volume;

        /**
         * 日期
         */
        private Date date;
    }

    /**
     * 项目团队信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamDTO {

        /**
         * 姓名
         */
        private String name;

        /**
         * 个人图片
         */
        private String photoUrl;

        /**
         * 职位
         */
        private String bio;
    }

    /**
     * 项目Github信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GithubChartsDTO {

        /**
         * 提交次数
         */
        private Integer gitCommit;

        /**
         * 收藏次数
         */
        private Integer gitStar;

        /**
         * 评分
         */
        private Float ranking;

        /**
         * 日期
         */
        private Date date;
    }

    /**
     * 项目进展信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProgressDTO {

        /**
         * 进展时间节点
         */
        private Date time;

        /**
         * 进展细节
         */
        private String detail;

        /**
         * 排序
         */
        private Integer sort;
    }
}
