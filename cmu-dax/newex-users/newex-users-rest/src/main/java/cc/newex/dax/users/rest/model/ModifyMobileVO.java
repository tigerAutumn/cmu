package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author newex-team
 * @date 2018/4/10 下午4:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyMobileVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 区域码
     */
    @NotNull
    private Integer areaCode;
    /**
     * 新手机
     */
    @NotBlank
    private String mobile;

    /**
     * 新验证码
     */
    @NotBlank
    @Length(min = 6, max = 6)
    private String newVerificationCode;

    /**
     * 老验证码
     */
    @NotBlank
    @Length(min = 6, max = 6)
    private String oldVerificationCode;
}