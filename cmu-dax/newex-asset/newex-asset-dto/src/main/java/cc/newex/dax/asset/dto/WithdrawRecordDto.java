package cc.newex.dax.asset.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author newex-team
 * @data 07/04/2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WithdrawRecordDto implements Serializable {

    private Long userId;
    /**
     *
     */
    private String txId;


    private String explorerUrl;
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

    private String tradeNo;
    /**
     *
     */
    private Integer currency;


    /**
     *
     */
    private Integer biz;

    private Date updateDate;
}
