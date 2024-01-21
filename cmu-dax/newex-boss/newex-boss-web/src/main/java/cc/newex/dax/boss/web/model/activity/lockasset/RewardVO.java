package cc.newex.dax.boss.web.model.activity.lockasset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @date 2018/11/27 15:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardVO {

    private String periodName;

    private Integer periodValue;

    private String rewardName;

    private BigDecimal rewardValue;
}
