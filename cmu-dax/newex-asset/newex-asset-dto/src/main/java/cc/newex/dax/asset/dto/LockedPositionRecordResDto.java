package cc.newex.dax.asset.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * //@author newex-team
 * //@data 07/04/2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LockedPositionRecordResDto implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 数量
     */
    private String amount;
    /**
     * 币种
     */
    private String currency;
    /**
     * 交易类型
     */
    private Integer transferType;
    /**
     * 锁仓类型
     */
    private String lockPosition;
    /**
     * 时间
     */
    private Date createTime;
    /**
     * 操作人
     */
    private String operator;

}

