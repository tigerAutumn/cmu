package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户名检查
 *
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckUsernameReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 手机区号（type=0时必填参数）
     */
    private String areaCode;
    /**
     * 用户名
     */
    @NotBlank
    @Length(min = 6, max = 50)
    private String username;

    /**
     * 用户类型 mobile手机 email邮箱
     */
    @NotBlank
    @Pattern(regexp = "email|mobile")
    private String type;

}
