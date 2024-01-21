package cc.newex.dax.users.dto.membership;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author newex-team
 * @date 2018-08-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @NotNull
    private Long userId;
    /**
     * 手机/邮箱
     */
    @NotBlank
    private String loginName;
}
