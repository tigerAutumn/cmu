package cc.newex.dax.perpetual.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetsTransferDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 业务类型
     */
    private Integer target;

    /**
     * 转账类型 1:转入 2转出
     */
    private Integer type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 数量
     */
    private BigDecimal amount;

    /**
     * 流水号
     */
    private String tradeNo;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 修改时间
     */
    private Date modifyDate;
}
