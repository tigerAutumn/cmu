package cc.newex.dax.asset.dto;

import lombok.*;

import java.util.Date;

/**
 * @author newex-team
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferRecordAuditResDto {
    /**
     * 系统间交互的唯一标识，防止发送重复交易
     */
    private String traderNo;
    /**
     * 审核消息
     */
    private String msg;

    /**
     * 0 待审核
     * 1 审核通过
     * 2 审核失败
     * 3 提现进行中
     */
    private Integer status;

    /**
     * 审核者
     */
    private Long auditUserId;

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

    private WithdrawRecordDto withdrawRecordDto;

}