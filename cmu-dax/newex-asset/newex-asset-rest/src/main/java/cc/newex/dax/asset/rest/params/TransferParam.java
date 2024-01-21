package cc.newex.dax.asset.rest.params;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferParam {

    String fromBiz;

    String toBiz;

    Long fromUserId;

    Long toUserId;
    /**
     * 提现金额
     */
    @NotNull
    @Range(min = 0)
    BigDecimal amount;

}
