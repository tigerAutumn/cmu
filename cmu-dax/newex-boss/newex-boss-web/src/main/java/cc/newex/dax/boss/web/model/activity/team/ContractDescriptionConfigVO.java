package cc.newex.dax.boss.web.model.activity.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 合约盈亏描述区间配置
 *
 * @author mbg.generated
 * @date 2018-12-21 15:09:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDescriptionConfigVO {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 开始区间
     */
    private BigDecimal startInterval;

    /**
     * 结束区间
     */
    private BigDecimal endInterval;

    /**
     * 描述
     */
    private String description;
}