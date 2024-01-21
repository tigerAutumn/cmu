package cc.newex.dax.users.dto.subaccount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 资金划转请求信息
 *
 * @author liutiejun
 * @date 2018-11-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferRequestDTO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 操作类型，1-从母账户划转到子账户(转出)，2-从子账户划转到母账户（转入）
     */
    private Integer optType;

    /**
     * 币种ID
     */
    private Integer currencyId;

    /**
     * 币种数量
     */
    private BigDecimal currencyAmount;

}
