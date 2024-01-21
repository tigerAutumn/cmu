package cc.newex.dax.asset.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author newex-team
 * @date 2018-09-17 19:42:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferRecord {
    /**
     */
    private Long id;

    /**
     */
    private Long userId;

    /**
     */
    private String from;

    /**
     */
    private String to;

    /**
     */
    private BigDecimal amount;

    /**
     */
    private BigDecimal fee;

    /**
     */
    private Integer confirmation;

    /**
     */
    private Integer currency;

    /**
     */
    private Integer biz;

    /**
     * 系统间交互的唯一标识，防止发送重复交易
     */
    private String traderNo;

    /**
     */
    private Integer transferType;

    /**
     * 0:提现中;1:已发送;
     */
    private Byte status;

    /**
     */
    private String remark;

    /**
     */
    private Date createDate;

    /**
     */
    private Date updateDate;

    /**
     * 券商id
     */
    private Integer brokerId;


    //重试次数
    private Integer retryTimes;
}