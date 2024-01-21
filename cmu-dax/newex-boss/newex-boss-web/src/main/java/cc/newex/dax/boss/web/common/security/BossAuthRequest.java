package cc.newex.dax.boss.web.common.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BossAuthRequest implements Serializable {
    private static final long serialVersionUID = -8445943548965154778L;
    @NotBlank
    @Length(min = 5, max = 50)
    private String account;
    @NotBlank
    @Length(min = 6, max = 20)
    private String password;
    @NotBlank
    @Length(min = 6, max = 6)
    private String captcha;
}
