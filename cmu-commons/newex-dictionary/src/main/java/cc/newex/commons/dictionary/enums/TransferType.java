package cc.newex.commons.dictionary.enums;

/**
 * 资金划转类型
 */
public enum TransferType {
    // 充值
    DEPOSIT(1,"deposit"),

    //提现
    WITHDRAW(2,"withdraw"),

    //划转
    TRANSFER(3,"transfer"),

    //转入
    TRANSFER_IN(4,"transfer_in"),

    //转出
    TRANSFER_OUT(5,"transfer_out"),

    //锁仓
    LOCKED_POSITION(6,"locked_position"),

    //解锁
    UNLOCKED_POSITION(7,"unlocked_position"),

    //加币
    ADD_TOKEN(8,"add_token"),
    //缴纳保证金
    PAY_TOKEN(9,"pay_token");

    private final int code;
    private final String name;

    TransferType(final int code,String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static TransferType convert(int code) {

        for(TransferType type:TransferType.values()){
            if(type.getCode() == code){
                return type;
            }
        }
        return null;
    }

    public static TransferType convert(String name) {

        for(TransferType type:TransferType.values()){
            if(type.getName().equals(name)){
                return type;
            }
        }
        return null;
    }
}
