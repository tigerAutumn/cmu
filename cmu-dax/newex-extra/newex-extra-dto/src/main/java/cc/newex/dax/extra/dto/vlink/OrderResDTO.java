package cc.newex.dax.extra.dto.vlink;

import lombok.*;

import java.util.Date;

/**
 * @author gi
 * @date 10/19/18
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResDTO {
    /**
     * order id
     */
    private Number id;
    /**
     * 总价
     */
    private Number total;
    /**
     * 数量
     */
    private Number quantity;
    /**
     * 合约Id
     */
    private Number contractId;
    /**
     * 交易Id
     */
    private String transactionId;
    /**
     * email
     */
    private String email;
    /**
     * VLink系统订单号
     */
    private String serialNumber;
    /**
     * 收益计算时间
     */
    private Date activateAt;
}
