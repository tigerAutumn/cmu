package cc.newex.dax.boss.web.model.activity.hundred;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liutiejun
 * @date 2018-12-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RankConfigVO {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 期数
     */
    private Integer term;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 状态，1-未开始，2-火热进行中，3-已结束
     */
    private Integer status;

    /**
     * 券商Id
     */
    private Integer brokerId;

}