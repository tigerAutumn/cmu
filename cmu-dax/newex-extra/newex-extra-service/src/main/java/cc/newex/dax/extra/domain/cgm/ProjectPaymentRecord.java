package cc.newex.dax.extra.domain.cgm;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目支付记录，记录支付CT、糖果活动币的记录
 *
 * @author liutiejun
 * @date 2018-08-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectPaymentRecord {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 项目id
     */
    private Long tokenInfoId;

    /**
     * 币种类型：1-CT保证金，2-糖果活动币
     */
    private Byte currencyType;

    /**
     * 支付数量
     */
    private BigDecimal amount;

    /**
     * tradeNo
     */
    private String tradeNo;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

    /**
     * 券商ID
     */
    private Integer brokerId;
}