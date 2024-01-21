package cc.newex.dax.boss.web.model.asset;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 按照固定时间进行解锁
 *
 * @author liutiejun
 * @date 2018-07-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UnlockPlanVO {

    /**
     * 解锁数量
     */
    private BigDecimal unlockAmount;

    /**
     * 释放时间
     */
    private Date releaseDate;

}
