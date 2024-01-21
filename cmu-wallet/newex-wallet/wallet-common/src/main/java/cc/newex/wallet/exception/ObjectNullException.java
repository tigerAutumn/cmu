package cc.newex.wallet.exception;

import cc.newex.commons.support.enums.ErrorCode;

/**
 * @author newex-team
 * @data 31/03/2018
 */

public class ObjectNullException extends RuntimeException implements ErrorCode {

    private final String msg;

    public ObjectNullException(final String msg) {
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 23003;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
