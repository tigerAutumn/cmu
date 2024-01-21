package cc.newex.dax.extra.enums.vlink;

/**
 * @author gi
 * @date 11/1/18
 */
public enum ContractStatusEnum {
    //未购买
    INIT(0),
    //转账成功
    TRANSFER_SUCCESS(1),
    //转账失败
    TRANSFER_FAIL(2),
    //购买成功
    ORDER_SUCCESS(3),
    //购买失败，资金已退回用户
    ORDER_FAIL(4),
    //退回资金失败，手动操作
    TRANSFER_BACK_FAIL(5);


    private final Integer code;

    ContractStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

}
