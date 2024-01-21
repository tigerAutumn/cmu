package cc.newex.dax.boss.web.model.c2c;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserVO {

    /**
     * 用户Id
     */
    @NotNull
    private Long userId;

    /**
     *状态
     */
    private Boolean status;
    /**
     * 标签
     */
    private Set<String> tags;

    /**
     * remark
     */

    private String remark;

    /**
     * 资质
     */
    private Boolean upgrade;

}
