package cc.newex.dax.users.domain.behavior.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 审查行为项类
 *
 * @author newex-team
 * @date 2018/04/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckBehaviorItem {
    /**
     * 审查行为项名称（google/mobile/email)
     */
    private String name;
    /**
     * 行为权重值
     */
    private Integer weight;
}

