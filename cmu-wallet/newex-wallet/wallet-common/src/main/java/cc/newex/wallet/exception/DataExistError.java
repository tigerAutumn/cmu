package cc.newex.wallet.exception;

import cc.newex.commons.support.enums.ErrorCode;

/**
 * @author newex-team
 * @data 01/04/2018
 */

public class DataExistError extends RuntimeException implements ErrorCode {
    private final String msg;

    public DataExistError(final String msg) {
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 23004;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
