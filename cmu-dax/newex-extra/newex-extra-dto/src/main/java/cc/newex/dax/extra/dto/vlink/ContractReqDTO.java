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
public class ContractReqDTO {

    /**
     * email
     */
    private String email;
    /**
     * 合约Id
     */
    private Number contractId;
    /**
     * 购买数量
     */
    private Number quantity;
    /**
     * 总价
     */
    private Number total;
    /**
     * 转账记录id
     */
    private String transactionId;
    /**
     * 系统订单号
     */
    private Number serialNumber;
    /**
     * 订单生效时间
     */
    private Date activateAt;
}
