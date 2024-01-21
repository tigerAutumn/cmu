package cc.newex.dax.boss.web.model.workorder;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkUserRecordVO {

    /**
     * 内容
     */
    private String content;

    /***
     * 用户ID
     */

    private Long userId;
    /**
     *
     */
    private String txId;
    /**
     * 地址
     */
    private String address;
    /**
     * 金额
     */
    private String amount;
    /**
     * 手续费
     */
    private String fee;
    /**
     * 确认数
     */
    private Integer confirmation;
    /**
     * 币种
     */
    private Integer currency;
    /**
     * 业务线
     */
    private Integer biz;
    /**
     * 系统间交互的唯一标识，防止发送重复交易
     */
    private String traderNo;
    /**
     * 交易类型
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
}
