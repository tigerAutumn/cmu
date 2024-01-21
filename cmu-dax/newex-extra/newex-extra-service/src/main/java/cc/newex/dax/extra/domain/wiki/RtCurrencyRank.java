package cc.newex.dax.extra.domain.wiki;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * rt代币排名信息
 *
 * @author mbg.generated
 * @date 2018-12-13 16:51:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RtCurrencyRank {
    /**
     * 主键
     */
    private Long id;

    /**
     * 代币标识
     */
    private String cid;

    /**
     * 币种简称
     */
    private String symbol;

    /**
     * 币种全称
     */
    private String currency;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * logo图标
     */
    private String logoUrl;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date modifyDate;
}