package cc.newex.dax.extra.domain.vlink;

import lombok.*;

import java.util.Date;

/**
 * vlink合约购买记录
 *
 * @author mbg.generated
 * @date 2018-10-27 22:42:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ContractInfo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 转账记录
     */
    private String transactionId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * vlink账号email
     */
    private String email;

    /**
     * 合约Id
     */
    private Long contractId;

    /**
     * 币种id
     */
    private Integer currencyId;

    /**
     * 合约型号
     */
    private String contractType;

    /**
     * 购买数量
     */
    private Long quantity;

    /**
     * 总价
     */
    private Long total;

    /**
     * vlink系统订单号
     */
    private String serialId;

    /**
     * 合约计算收益时间
     */
    private Date activateDate;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date modifyDate;
}