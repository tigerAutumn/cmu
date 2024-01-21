package cc.newex.dax.asset.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author newex-team
 * @date 2019-04-23 18:58:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferRecordAudit {
    /**
     */
    private Long id;

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
    private Byte status;

    /**
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
}