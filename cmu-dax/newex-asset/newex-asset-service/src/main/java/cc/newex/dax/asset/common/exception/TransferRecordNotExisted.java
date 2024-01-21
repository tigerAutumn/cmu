package cc.newex.dax.asset.common.exception;

import cc.newex.commons.support.enums.ErrorCode;

/**
 * @author newex-team
 * @data 06/04/2018
 */

public class TransferRecordNotExisted extends RuntimeException implements ErrorCode {
    private String tradeNo;

    public TransferRecordNotExisted(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public int getCode() {
        return 20002;
    }

    @Override
    public String getMessage() {
        return this.tradeNo + " does not exited";
    }
}
