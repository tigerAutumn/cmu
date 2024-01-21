package cc.newex.wallet.pojo;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018-04-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OmniTransaction implements Serializable {
    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String txId;
    /**
     *
     */
    private Long blockHeight;
    /**
     *
     */
    private String address;
    /**
     * omni数值
     */
    private BigDecimal omniBalance;
    /**
     * btc数值
     */
    private BigDecimal balance;
    /**
     * 确认数
     */
    private Long confirmNum;
    /**
     * 业务类型
     */
    private Integer biz;

    private Integer currency;
    /**
     * output序号
     */
    private Short seq;
    /**
     * 是否被花费
     */
    private Byte spent;
    /**
     * -1: 删除；0:提现中;1:签名中;2:已发送; 3:已确认
     */
    private Byte status;
    /**
     * 花费此输出的txid
     */
    private String spentTxId;
    /**
     *
     */
    private Date createDate;
    /**
     *
     */
    private Date updateDate;
}