package cc.newex.dax.perpetual.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 撮合缓存表
 *
 * @author newex-team
 * @date 2018-12-26 15:31:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchingCache {
    /**
     */
    private Long id;

    /**
     * 是Base和Quote之间的组合 FBTCUSD，RBTCUSD1109
     */
    private String contractCode;

    /**
     * contract的json
     */
    private String contractInfo;

    /**
     * pending的json
     */
    private String pendingInfo;

    /**
     * order_all修改的json
     */
    private String orderAllUpdateInfo;

    /**
     * order_all删除的json
     */
    private String orderAllDeleteInfo;

    /**
     * order_finish的json
     */
    private String orderFinishInfo;

    /**
     * user_bill的json
     */
    private String userBillInfo;

    /**
     * system_bill的json
     */
    private String systemBillInfo;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date modifyDate;
}