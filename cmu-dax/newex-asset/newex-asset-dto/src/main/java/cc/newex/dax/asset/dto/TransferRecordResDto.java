package cc.newex.dax.asset.dto;

import lombok.*;

import java.util.Date;

/**
 * @author newex-team
 * @date 2018-04-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferRecordResDto {
    /**
     *
     */
    private Long userId;
    /**
     *
     */
    private String txId;
    /**
     *
     */
    private String address;
    /**
     *
     */
    private String amount;
    /**
     *
     */
    private String fee;
    /**
     *
     */
    private Integer confirmation;
    /**
     *
     */
    private Integer currency;
    /**
     *
     */
    private Integer biz;
    /**
     * 系统间交互的唯一标识，防止发送重复交易
     */
    private String traderNo;
    /**
     *
     */
    private Integer transferType;
    /**
     * 0:提现中;1:已发送;
     */
    private Byte status;
    /**
     *
     */
    private Date createDate;
    /**
     *
     */
    private Date updateDate;
    private Integer brokerId;
}