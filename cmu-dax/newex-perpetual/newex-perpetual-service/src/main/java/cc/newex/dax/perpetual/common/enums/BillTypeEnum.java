package cc.newex.dax.perpetual.common.enums;

import cc.newex.commons.support.i18n.LocaleUtils;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * billType枚举
 *
 * @author xionghui
 * @date 2018/10/24
 */
@AllArgsConstructor
public enum BillTypeEnum {
    /**
     * 充值
     */
    RECHARGE(11),
    /**
     * 提现
     */
    WITHDRAW(12),
    /**
     * 转入
     */
    TRANSFER_IN(13),
    /**
     * 转出
     */
    TRANSFER_OUT(14),
    /**
     * 多/买
     */
    LONG(15),
    /**
     * 空/卖
     */
    SHORT(16),
    /**
     * 系统收取手续费
     */
    SYSTEM_FEE(17),
    /**
     * 保险金
     */
    INSURANCE(18),
    /**
     * 结算/资金费用
     */
    SETTLEMENT(19),
    /**
     * 穿仓对敲
     */
    CONTRA_TRADE(20),

    /**
     * 领币
     */
    REWARD(21),
    /**
     * 强平
     */
    LIQUIDATE(22),
    /**
     * 爆仓
     */
    EXPLOSION(23),

    ;

    public final static String BILL_TYPE_PREFIX = "bill.type.code.";
    private final int billType;

    /**
     * 返回可用的账单类型
     *
     * @return
     */
    public static List<Integer> getList() {
        return Arrays
                .asList(BillTypeEnum.RECHARGE.getBillType(),
                        BillTypeEnum.WITHDRAW.getBillType(),
                        BillTypeEnum.TRANSFER_IN.getBillType(),
                        BillTypeEnum.TRANSFER_OUT.getBillType(),
                        BillTypeEnum.LONG.getBillType(),
                        BillTypeEnum.SHORT.getBillType(),
                        BillTypeEnum.TRANSFER_OUT.getBillType(),
                        BillTypeEnum.SETTLEMENT.getBillType(),
                        BillTypeEnum.LIQUIDATE.getBillType(),
                        BillTypeEnum.EXPLOSION.getBillType()
                );
    }

    public static String getDesc(final Integer code) {
        return LocaleUtils.getMessage(BillTypeEnum.BILL_TYPE_PREFIX + code);
    }

    public int getBillType() {
        return this.billType;
    }
}
