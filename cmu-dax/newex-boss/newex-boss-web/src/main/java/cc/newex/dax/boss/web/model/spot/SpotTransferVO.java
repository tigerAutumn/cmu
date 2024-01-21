package cc.newex.dax.boss.web.model.spot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 资金划转
 *
 * @author liutiejun
 * @date 2018-08-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotTransferVO {

    @NotNull
    private Long fromUserId;

    @NotNull
    private Long toUserId;

    /**
     * 币种ID
     */
    @NotNull
    private Integer currencyId;

    /**
     * 划转数量
     */
    @NotNull
    @DecimalMin("0")
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 券商Id
     */
    private Integer brokerId;
}
