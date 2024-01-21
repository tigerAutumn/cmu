package cc.newex.dax.users.domain.behavior.model;

import lombok.*;

import java.util.List;

/**
 * 行为操作结果类
 *
 * @author newex-team
 * @date 2018/04/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBehaviorResult {
    /**
     * 用户行为名称
     * {@link cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum}
     */
    private String name;

    /**
     * 当前行为需要审查的行为项
     */
    List<CheckBehaviorItem> checkItems;

    /**
     * 当前行为需要审查的全部行为项
     */
    List<CheckBehaviorItem> totalCheckItems;
}
