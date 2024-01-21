package cc.newex.dax.boss.web.model.spot;


import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SnapshotTaskDetailsVO {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * btc资产
     */
    private BigDecimal bitcoinAssets;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 币种 id
     */
    private Integer currencyId;
    /**
     * 可用余额
     */
    private BigDecimal available;
    /**
     * 冻结余额
     */
    private BigDecimal hold;

}
