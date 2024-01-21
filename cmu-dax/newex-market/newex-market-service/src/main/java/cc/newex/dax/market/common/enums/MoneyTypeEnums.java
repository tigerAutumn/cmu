package cc.newex.dax.market.common.enums;

import cc.newex.commons.support.enums.ErrorCode;

/**
 * 法币
 *
 * @author newex-team
 * @date 2018/03/18
 **/
public enum MoneyTypeEnums implements ErrorCode {
    CNY(0),
    USD(1);


    private final int code;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return null;
    }

    MoneyTypeEnums(final int code) {
        this.code = code;
    }
}
