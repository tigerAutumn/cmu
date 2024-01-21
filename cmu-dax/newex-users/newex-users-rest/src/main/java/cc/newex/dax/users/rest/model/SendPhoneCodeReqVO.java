package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendPhoneCodeReqVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 相关行为业务
     */
    @NotNull
    private Integer behavior;
    /**
     * 手机号
     */
    @NotBlank
    @Length(min = 5, max = 20)
    private String mobile;
    /**
     * 手机区号
     */
    @NotBlank
    @Length(min = 2, max = 10)
    private String areaCode;
}
