package cc.newex.dax.asset.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author newex-team
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferRecordAuditReqDto {
    /**
     * 系统间交互的唯一标识，防止发送重复交易
     */
    @NotNull
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
    @NotNull
    private Integer status;

    /**
     * 审核者
     */
    @NotNull
    private Long auditUserId;

}