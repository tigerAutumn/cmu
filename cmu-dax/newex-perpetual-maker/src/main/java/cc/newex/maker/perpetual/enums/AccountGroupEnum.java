package cc.newex.maker.perpetual.enums;

/**
 * @author cmx-sdk-team
 * @date 2019-04-08
 */
public enum AccountGroupEnum {

    GROUP_1(AccountEnum.BITMEX_1, AccountEnum.CMX_DEPTH_1, AccountEnum.CMX_TRADE_1),;

    private AccountEnum bitMexAccount;

    private AccountEnum cmxDepthAccount;

    private AccountEnum cmxTradeAccount;

    AccountGroupEnum(final AccountEnum bitMexAccount, final AccountEnum cmxDepthAccount, final AccountEnum cmxTradeAccount) {
        this.bitMexAccount = bitMexAccount;
        this.cmxDepthAccount = cmxDepthAccount;
        this.cmxTradeAccount = cmxTradeAccount;
    }

    public AccountEnum getBitMexAccount() {
        return this.bitMexAccount;
    }

    public AccountEnum getCmxDepthAccount() {
        return this.cmxDepthAccount;
    }

    public AccountEnum getCmxTradeAccount() {
        return this.cmxTradeAccount;
    }

}
