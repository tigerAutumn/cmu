package cc.newex.dax.asset.common.exception;

import cc.newex.commons.support.enums.ErrorCode;

/**
 * @author newex-team
 * @data 06/04/2018
 */

public class AddressNotExisted implements ErrorCode {
    private String address;

    public AddressNotExisted(String address) {
        this.address = address;
    }

    @Override
    public int getCode() {
        return 20002;
    }

    @Override
    public String getMessage() {
        return this.address + " does not exist";
    }
}
