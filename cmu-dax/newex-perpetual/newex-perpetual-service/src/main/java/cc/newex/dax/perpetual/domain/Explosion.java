package cc.newex.dax.perpetual.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 爆仓
 *
 * @author newex-team
 * @date 2018-11-20 18:26:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Explosion {
    /**
     * 破产表主键，与用户持仓表主键相同
     */
    private Long id;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 业务方ID
     */
    private Integer brokerId;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 取消订单id
     */
    private String cancelOrderId;

    /**
     * 平仓订单id
     */
    private Long closeOrderId;

    /**
     * 当前流水 id
     */
    private Long historyExplosionId;

    /**
     * 创建时间戳
     */
    private Date createTime;

    /**
     * 修改时间戳
     */
    private Date modifyTime;
}