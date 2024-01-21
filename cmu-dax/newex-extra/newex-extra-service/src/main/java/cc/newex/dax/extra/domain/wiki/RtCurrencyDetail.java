package cc.newex.dax.extra.domain.wiki;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * rt代币详情信息
 *
 * @author mbg.generated
 * @date 2018-12-19 17:00:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RtCurrencyDetail {
    /**
     * 主键
     */
    private Long id;

    /**
     * 代币标识
     */
    private String cid;

    /**
     * 流通数量
     */
    private Long circulatingSupply;

    /**
     * 发行时间
     */
    private String issueTime;

    /**
     * 上交易所数量
     */
    private Short exchangeNum;

    /**
     * 总发行量
     */
    private String totalCirculation;

    /**
     * 币种简称
     */
    private String symbol;

    /**
     * 发行价格
     */
    @JSONField(name = "crowdfunding_avg_price")
    private String issuePrice;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date modifyDate;
}