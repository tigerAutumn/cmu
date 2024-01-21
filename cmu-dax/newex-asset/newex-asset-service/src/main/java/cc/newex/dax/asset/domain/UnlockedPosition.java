package cc.newex.dax.asset.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 解锁计划表
 *
 * @author newex-team
 * @date 2018-09-17 19:56:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UnlockedPosition {
    /**
     */
    private Long id;

    /**
     * 锁仓记录id
     */
    private Long lockPositionId;

    /**
     * 解锁数量
     */
    private BigDecimal unlockAmount;

    /**
     * 释放时间
     */
    private Date releaseDate;

    /**
     * 状态 0未执行 1已执行
     */
    private Integer status;

    /**
     */
    private Date createDate;

    /**
     */
    private Date updateDate;

    /**
     * 券商id
     */
    private Integer brokerId;
}