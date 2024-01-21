package cc.newex.dax.extra.dto.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 币种的评级信息
 *
 * @author liutiejun
 * @date 2018-07-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyLevelInfoDTO {

    private String tokeninsightID;

    /**
     * 用来区分中英文，cn/en
     */
    private String language;

    /**
     * 币种名称
     */
    private String name;

    /**
     * 币种简称
     */
    private String abbreviate;

    /**
     * 评级结果，AA、BB等
     */
    private String levelResult;

    /**
     * 评级结果说明，分中英文
     */
    private String description;

    /**
     * 观察、展望结果，分中英文
     */
    private String anticipation;

    /**
     * 风险提示，分中英文
     */
    private String warning;

    /**
     * 评级报告PDF地址，分中英文
     */
    private String pdfUrl;

    /**
     * 团队分数
     */
    private Integer teamScore;

    /**
     * 项目分数
     */
    private Integer subjectScore;

    /**
     * 生态分数
     */
    private Integer ecologyScore;

}
