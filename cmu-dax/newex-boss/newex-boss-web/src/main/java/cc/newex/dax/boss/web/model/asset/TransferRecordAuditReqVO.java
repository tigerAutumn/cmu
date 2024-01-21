package cc.newex.dax.boss.web.model.asset;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author liutiejun
 * @date 2019-04-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferRecordAuditReqVO {

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

}
