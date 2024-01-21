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
public class LockedPositionRecordReqDto implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * email
     */
    private String email;
    /**
     * 币种
     */
    private Integer currency;
    /**
     * 交易类型
     */
    private Integer transferType;
    /**
     * 起始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    private Integer brokerId;
}
