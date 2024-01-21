package cc.newex.dax.boss.web.model.c2c;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author liutiejun
 * @date 2018-09-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class C2cTransferVO {

    @NotBlank
    private String fromBiz;

    @NotBlank
    private String toBiz;

    @NotBlank
    private String currency;

    @NotNull
    private Long userId;

    @NotNull
    @DecimalMin("0")
    private BigDecimal amount;

}
