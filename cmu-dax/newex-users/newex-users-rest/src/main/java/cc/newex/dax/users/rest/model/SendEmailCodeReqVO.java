package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailCodeReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 相关行为业务
     */
    @NotNull
    private Integer behavior;
    /**
     * 邮箱
     */
    @Email
    @Length(min = 6, max = 50)
    private String email;

}
