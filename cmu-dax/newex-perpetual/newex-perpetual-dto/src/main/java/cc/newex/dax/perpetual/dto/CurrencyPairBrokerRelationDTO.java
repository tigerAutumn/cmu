package cc.newex.dax.perpetual.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author better
 * @date create in 2018/11/22 5:41 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyPairBrokerRelationDTO {

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
