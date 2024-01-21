package cc.newex.dax.extra.domain.wiki;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * rt代币评分信息
 *
 * @author mbg.generated
 * @date 2018-12-13 16:34:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RtCurrencyScore {
    /**
     * 主键
     */
    private Long id;

    /**
     * 代币标识
     */
    private String cid;

    /**
     * 币种简称
     */
    private String symbol;

    /**
     * 技术评分
     */
    private Float techScore;

    /**
     * 团队评分
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
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date modifyDate;
}