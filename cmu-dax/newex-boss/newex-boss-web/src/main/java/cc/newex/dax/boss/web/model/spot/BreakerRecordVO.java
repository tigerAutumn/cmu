package cc.newex.dax.boss.web.model.spot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 熔断任务
 *
 * @author liutiejun
 * @date 2018-08-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BreakerRecordVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 币对id
     */
    private Long productId;

    /**
     * 策略id
     */
    private Long strategyId;

    /**
     * 下次开始时间
     */
    private String startDate;

    /**
     * 熔断时间
     */
    private String breakerDate;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

}
