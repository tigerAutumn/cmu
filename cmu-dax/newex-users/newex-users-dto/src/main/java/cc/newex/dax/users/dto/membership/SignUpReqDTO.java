package cc.newex.dax.users.dto.membership;

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
 * @date 2018-08-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 区域码
     */
    @NotNull
    @Builder.Default
    private Integer areaCode = 86;
    /**
     * 手机/邮箱
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String loginName;
    /**
     * 密码
     */
    @NotBlank
    @Length(min = 6, max = 20)
    private String password;
    /**
     * 注册渠道
     */
    @NotBlank
    @Length(min = 6, max = 6)
    private String channel;
    /**
     * 注册来源
     */
    private Integer regFrom;
    /**
     * 注册IP
     */
    private String ipAddress;
    /**
     * 券商ID,默认1
     */
    private Integer brokerId = 1;
}
