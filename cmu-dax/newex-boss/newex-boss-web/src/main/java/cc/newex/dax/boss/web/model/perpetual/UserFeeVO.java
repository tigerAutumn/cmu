package cc.newex.dax.boss.web.model.perpetual;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author better
 * @date create in 2018/11/22 4:27 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFeeVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * pair_code的base走手续费
     */
    private String pairCode;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0:both 1:maker 2:taker
     */
    private Integer side;

    /**
     * 手续费
     */
    private BigDecimal rate;

    /**
     * 业务方ID
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
