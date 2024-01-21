package cc.newex.dax.users.rest.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户协议设置
 *
 * @author newex-team
 * @date 2018/9/4
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProtocolSettingVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    @NotBlank
    private String type;
    /**
     * 是否同意
     */
    @NotNull
    private Boolean agree;
}
