package cc.newex.dax.extra.dto.vlink;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author gi
 * @date 10/22/18
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderReqDTO implements Serializable {
    /**
     * email
     */
    private String email;

    private String currency;
    /**
     * 总价
     */
    private BigDecimal total;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 合约Id
     */
    private Long contractId;
}
