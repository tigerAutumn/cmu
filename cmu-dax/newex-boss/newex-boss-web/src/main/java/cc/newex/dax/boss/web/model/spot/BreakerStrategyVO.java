package cc.newex.dax.boss.web.model.spot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 熔断策略
 *
 * @author liutiejun
 * @date 2018-08-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BreakerStrategyVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 监控方向
     */
    private Integer breakerDirection;

    /**
     * 是否预置
     */
    private Byte breakerPreset;

    /**
     * 监控周期，以秒计
     */
    private Long monitorTime;

    /**
     * 触发熔断比例
     */
    private Integer triggerRatio;

    /**
     * 暂停时长，以秒计
     */
    private Long pauseTime;

    /**
     * 任务间隔时长，以秒计
     */
    private Long intervalTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

}
