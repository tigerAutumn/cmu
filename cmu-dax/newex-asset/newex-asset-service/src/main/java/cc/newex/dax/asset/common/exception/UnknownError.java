package cc.newex.dax.asset.common.exception;

import cc.newex.commons.support.enums.ErrorCode;

/**
 * @author newex-team
 * @data 06/04/2018
 */

public class UnknownError implements ErrorCode {

    public UnknownError() {
    }

    @Override
    public int getCode() {
        return -1;
    }

    @Override
    public String getMessage() {
        return "Unknown error";
    }
}
