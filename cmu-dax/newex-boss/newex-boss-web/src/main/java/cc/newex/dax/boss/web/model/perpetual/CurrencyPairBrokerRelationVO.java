package cc.newex.dax.boss.web.model.perpetual;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author better
 * @date create in 2018/11/22 7:58 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyPairBrokerRelationVO {

    /**
     * id
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
