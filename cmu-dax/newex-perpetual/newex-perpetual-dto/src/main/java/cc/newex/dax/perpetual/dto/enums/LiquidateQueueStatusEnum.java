package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/6/22 09:29
 * @Description:
 */
public enum LiquidateQueueStatusEnum {
    //预下单数据处理状态 0 未处理、1 处理中、2 处理结束
    NO_ORDER(0, "新进队列未下爆仓单"),
    ALREADY_ORDER(1, "已经下单，完成状态未知"),
    REVOKED(2, "已经撤单"),
    FINISH(3, "已经完成"),
    ;

    private final Integer code;

    private final String name;

    LiquidateQueueStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
