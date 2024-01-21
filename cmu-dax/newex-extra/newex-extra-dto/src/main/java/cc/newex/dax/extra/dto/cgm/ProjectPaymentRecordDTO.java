package cc.newex.dax.extra.dto.cgm;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2018-08-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectPaymentRecordDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    @NotNull
    private Long userId;

    /**
     * 项目id
     */
    @NotNull
    private Long tokenInfoId;

    /**
     * 币种类型：1-CT保证金，2-糖果活动币
     */
    @NotNull
    private Byte currencyType;

    /**
     * 支付数量
     */
    @NotNull
    private BigDecimal amount;

    /**
     * tradeNo
     */
    @NotBlank
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
