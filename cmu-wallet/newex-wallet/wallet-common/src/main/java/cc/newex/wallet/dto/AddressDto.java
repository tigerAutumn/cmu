package cc.newex.wallet.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 05/04/2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressDto implements Serializable {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 钱包地址
     */
    private String address;
    /**
     * 业务类型
     */
    private Integer biz;
    /**
     * 充值确认数
     */
    private Integer depositConfirm;
    /**
     * 最小充值金额
     */
    private BigDecimal minDepositAmount;
}
