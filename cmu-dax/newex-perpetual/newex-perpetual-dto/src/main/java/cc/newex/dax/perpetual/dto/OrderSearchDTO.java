package cc.newex.dax.perpetual.dto;

import cc.newex.dax.perpetual.dto.enums.OrderStatusEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 合约委托查询
 *
 * @author newex-team
 * @date 2019-01-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderSearchDTO implements Serializable {
    /**
     * 合约
     */
    private String contractCode;
    /**
     * 仓位方向 1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short
     */
    private String detailSide;
    /**
     * 状态
     *
     * @see OrderStatusEnum
     */
    private Integer status;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
}
