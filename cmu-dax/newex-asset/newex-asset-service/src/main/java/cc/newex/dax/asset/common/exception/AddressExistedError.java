package cc.newex.dax.asset.common.exception;

import cc.newex.commons.support.enums.ErrorCode;

/**
 * @author newex-team
 * @data 06/04/2018
 */

public class AddressExistedError implements ErrorCode {

    private String address;

    public AddressExistedError(String address) {
        this.address = address;
    }

    @Override
    public int getCode() {
        return 20001;
    }

    @Override
    public String getMessage() {
        return this.address + " has existed";
    }
}
