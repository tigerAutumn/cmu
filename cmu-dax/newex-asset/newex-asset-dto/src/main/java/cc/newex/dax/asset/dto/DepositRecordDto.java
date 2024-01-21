package cc.newex.dax.asset.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author newex-team
 * @data 06/04/2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepositRecordDto implements Serializable {
    /**
     *
     */
    private Long userId;


    private String explorerUrl;
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
    private Integer confirmation;
    /**
     *
     */
    private Integer currency;
    /**
     *
     */
    private Integer biz;

    private Date updateDate;

    private int status;
}
