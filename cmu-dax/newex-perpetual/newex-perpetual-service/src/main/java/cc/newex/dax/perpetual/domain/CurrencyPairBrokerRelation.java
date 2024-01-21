package cc.newex.dax.perpetual.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 币对 券商 对应关系表
 *
 * @author newex-team
 * @date 2018-11-22 17:21:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyPairBrokerRelation {
    /**
     */
    private Long id;

    /**
     * 币对
     */
    private String pairCode;

    /**
     * 券商 id
     */
    private Integer brokerId;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date modifyDate;
}