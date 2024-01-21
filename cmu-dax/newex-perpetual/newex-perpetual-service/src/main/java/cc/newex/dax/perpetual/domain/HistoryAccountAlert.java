package cc.newex.dax.perpetual.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 对账报警流水表
 *
 * @author newex-team
 * @date 2018-09-26 17:26:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryAccountAlert {
    /**
     * 对账报警流水
     */
    private Long id;

    /**
     * 报警类型
     */
    private Integer type;

    /**
     * 报警类型说明
     */
    private String typeInfo;

    /**
     * 是Base和quote之间的组合 P_BTC_USD
     */
    private String pairCode;

    /**
     * 报警信息
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}