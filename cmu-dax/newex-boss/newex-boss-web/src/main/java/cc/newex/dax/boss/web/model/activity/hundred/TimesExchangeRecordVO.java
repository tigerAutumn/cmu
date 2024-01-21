package cc.newex.dax.boss.web.model.activity.hundred;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liutiejun
 * @date 2019-01-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimesExchangeRecordVO {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 兑换的抽奖次数
     */
    private Integer totalTimes;

    /**
     * 获取方式，1-持有CT获得，2-充值获得，3-交易获得，4-消耗CT获得
     */
    private Integer type;

}
