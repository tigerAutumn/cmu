package cc.newex.dax.boss.web.model.activity.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 合约模拟赛信息
 *
 * @author newex-team
 * @date 2018-12-18 12:17:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractSimulationGameVO {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 期数
     */
    private Integer term;

    /**
     * 模拟赛名称
     */
    private String name;

    /**
     * 合约编码，每次模拟赛只针对一个币对
     */
    private String contractCode;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 状态，1-上线，2-下线
     */
    private Integer status;

    /**
     * 券商Id
     */
    private Integer brokerId;
}