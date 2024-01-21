package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动币领取表
 *
 * @author newex-team
 * @date 2018-12-20 20:14:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserActivityReward {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 转账类型 1:转入 2转出
     */
    private Integer type;

    /**
     * 数量
     */
    private BigDecimal amount;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 券商 id
     */
    private Integer brokerId;
}