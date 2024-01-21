package cc.newex.commons.dictionary.enums;


import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Set;

import static cc.newex.commons.dictionary.enums.CurrencyEnum.Decimal.*;


/**
 * @author newex-team
 * @data 27/03/2018
 */

public enum CurrencyEnum {

    BTC(1, "btc", 7, 1, 6, EIGHT),
    ETH(2, "eth", 121, 12, 120, EIGHTEEN),
    LTC(3, "ltc", 7, 1, 6, EIGHT),
    ETC(4, "etc", 121, 12, 120, EIGHTEEN),
    BCH(5, "bch", 7, 1, 6, EIGHT),
    USDT(6, "usdt", 7, 1, 6, EIGHT),
    ERC20(7, "erc20", 121, 12, 120, EIGHTEEN),
    MTX(8, "mtx", 121, 12, 120, EIGHTEEN, "0x0af44e2784637218dd1d32a322d44e603a8f0c6a"),
    CPG(9, "cpg", 121, 12, 120, EIGHTEEN, "0x6620749a9bd0ba09e9921ad77f040d297a861b49"),
    OMG(10, "omg", 121, 12, 120, EIGHTEEN, "0xd26114cd6EE289AccF82350c8d8487fedB8A0C07"),
    CHP(11, "chp", 121, 12, 120, EIGHTEEN, "0xf3db7560E820834658B590C96234c333Cd3D5E5e"),
    CEL(12, "cel", 121, 12, 120, FOUR, "0xaaAEBE6Fe48E54f431b0C390CfaF0b017d09D42d"),
    LYM(13, "lym", 121, 12, 120, EIGHTEEN, "0x57aD67aCf9bF015E4820Fbd66EA1A21BED8852eC"),
    ACT(14, "act", 61, 12, 60, FIVE),
    KCASH(15, "kcash", 61, 12, 60, FIVE, "COND41iays8576giHf6M6Yox1DiBrDmgVyzJ"),
    SSC(16, "ssc", 61, 12, 60, FIVE),
    LET(17, "let", 61, 12, 60, FIVE, "CONDuPQkPuKqD5NM2XwHJCjnZiiTs2GAJDLB"),
    CTB(18, "ctb", 121, 12, 120, EIGHTEEN, "0x43e7e2e3a893a7eaa5219ffc087c7497c350c8c2"),

    BMB(19, "bmb", 121, 12, 120, EIGHT, "0x9c1f0de4fec6ae8efb65f2e45a1c1b625e35e7bf"),
    XRP(20, "xrp", 11, 5, 10, SIX),
    IOTA(21, "iota", 11, 5, 10, SIX),
    BTM(22, "btm", 12, 2, 10, EIGHT, "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"),
    CT(25, "ct", 121, 12, 120, EIGHT),
    //点卡
    CMDK(26, "cmdk", 121, 12, 120, EIGHT),
    DDD(27, "ddd", 121, 12, 120, EIGHTEEN, "0x9f5f3cfd7a32700c93f971637407ff17b91c7342"),
    MVP(28, "mvp", 121, 12, 120, EIGHTEEN, "0x8a77e40936bbc27e80e9a3f526368c967869c86d"),
    BAIC(29, "baic", 121, 12, 120, EIGHTEEN, "0x3258FF5a650d2A4601c12bc3DA495557EF354066"),
    WIN(30, "win", 121, 12, 120, EIGHTEEN, "0xbfaa8cf522136c6fafc1d53fe4b85b4603c765b8"),
    EOS(31, "eos", 121, 12, 120, EIGHTEEN),
    ADA(32, "ada", 121, 12, 120, EIGHTEEN),
    GET(33, "get", 121, 12, 120, EIGHTEEN, "0x60c68a87be1e8a84144b543aacfa77199cd3d024"),
    OF(34, "of", 121, 12, 120, THREE),
    NEO(35, "neo", 4, 3, 3, ZERO, "0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b"),
    GAS(36, "gas", 4, 3, 3, EIGHT, "0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7"),
    MDS(38, "mds", 121, 12, 120, EIGHTEEN, "0x66186008C1050627F979d464eABb258860563dbE"),
    SDS(37, "sds", 4, 3, 3, EIGHT, "0x6fad54d8cc692fc808fd97a207836a846c217705"),
    GNX(39, "gnx", 121, 12, 120, NINE, "0x6ec8a24cabdc339a06a172f8223ea557055adaa5"),
    RRC(40, "rrc", 121, 12, 120, TWO, "0xB6259685685235c1eF4B8529e7105f00BD42b9f8"),
    DPY(41, "dpy", 121, 12, 120, EIGHTEEN, "0x6c2adc2073994fb2ccc5032cc2906fa221e9b391"),
    ONT(42, "ont", 4, 3, 3, ZERO, "0100000000000000000000000000000000000000"),
    ONG(43, "ong", 4, 3, 3, NINE, "0200000000000000000000000000000000000000"),
    FTI(44, "fti", 121, 12, 120, EIGHTEEN, "0x943ed852dadb5c3938ecdc6883718df8142de4c8"),
    YOU(45, "you", 121, 12, 120, EIGHTEEN, "0x34364bee11607b1963d66bca665fde93fca666a8"),
    HUR(46, "hur", 121, 12, 120, EIGHTEEN, "0xcdb7ecfd3403eef3882c65b761ef9b5054890a47"),
    NEP5(47, "nep5", 101, 10, 100, EIGHTEEN),
    ORC_TOKEN(48, "orcToken", 121, 12, 120, EIGHTEEN),

    PAG(49, "pag", 121, 12, 120, FOUR, "0x000028009c219f9abe02d3fbf4edc43def7e7ddb7224257384"),

    FBS001(50, "FBS001", 100, 100, 100, ZERO),
    CMX06(51, "cmx06", 100, 100, 100, ZERO);


    public static Set<CurrencyEnum> ERC20_SET = Sets.immutableEnumSet(MTX, CPG, OMG, CHP, CEL, LYM, CTB, BMB, DDD,
            MVP, BAIC, WIN, GET, MDS, GNX, RRC, DPY, FTI, YOU, HUR);

    public static Set<CurrencyEnum> ATP_SET = Sets.immutableEnumSet(KCASH, LET);

    public static Set<CurrencyEnum> ONT_ASSET = Sets.immutableEnumSet(ONT,ONG);

    public static Set<CurrencyEnum> ORC_TOKEN_SET = Sets.immutableEnumSet(PAG);
    public static Set<CurrencyEnum> NEP5_SET = Sets.immutableEnumSet(SDS);
    public static Set<CurrencyEnum> NEO_ASSET = Sets.immutableEnumSet(GAS, NEO);
    private final int index;
    private final String name;
    private final String remark;
    private final long confirmNum;
    private final long depositConfirmNum;
    private final long withdrawConfirmNum;
    private final BigDecimal decimal;

    /**
     * @param index
     * @param name
     * @param confirmNum         : 更新到多少个确认数
     * @param depositConfirmNum  ： 充值确认数
     * @param withdrawConfirmNum ： 提现确认数
     */
    CurrencyEnum(final int index, final String name, final int confirmNum, final int depositConfirmNum,
                 final int withdrawConfirmNum, final Decimal decimal) {
        this(index, name, confirmNum, depositConfirmNum, withdrawConfirmNum, decimal, "");
    }

    CurrencyEnum(final int index, final String name, final int confirmNum, final int depositConfirmNum,
                 final int withdrawConfirmNum, final Decimal decimal, final String remark) {
        this.index = index;
        this.name = name;
        this.confirmNum = confirmNum;
        this.depositConfirmNum = depositConfirmNum;
        this.withdrawConfirmNum = withdrawConfirmNum;
        this.decimal = BigDecimal.valueOf(decimal.getDecimal());
        this.remark = remark;
    }

    public static CurrencyEnum parseValue(final int index) {
        for (final CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            if (currencyEnum.getIndex() == index) {
                return currencyEnum;
            }
        }
        return null;
    }

    public static CurrencyEnum parseName(final String name) {
        for (final CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            if (currencyEnum.getName().equalsIgnoreCase(name.toLowerCase())) {
                return currencyEnum;
            }
        }
        return null;
    }

    public static CurrencyEnum parseContract(final String contract) {
        if (!StringUtils.isEmpty(contract)) {
            for (final CurrencyEnum currencyEnum : CurrencyEnum.values()) {
                if (currencyEnum.getContractAddress().equalsIgnoreCase(contract.toLowerCase())) {
                    return currencyEnum;
                }
            }
        }
        return null;
    }

    public static boolean isErc20(final CurrencyEnum currency) {
        return CurrencyEnum.ERC20_SET.contains(currency);
    }

    public static boolean isATP(final CurrencyEnum currency) {
        return CurrencyEnum.ATP_SET.contains(currency);
    }

    //获得代币对应的主链币种
    public static CurrencyEnum toMainCurrency(CurrencyEnum currencyEnum) {
        if (CurrencyEnum.isErc20(currencyEnum)) {
            return CurrencyEnum.ETH;
        } else if (CurrencyEnum.isATP(currencyEnum)) {
            return CurrencyEnum.ACT;
        } else if (CurrencyEnum.NEO_ASSET.contains(currencyEnum) || CurrencyEnum.NEP5_SET.contains(currencyEnum)) {
            return CurrencyEnum.NEO;
        } else if (CurrencyEnum.ORC_TOKEN_SET.contains(currencyEnum)) {
            return CurrencyEnum.OF;
        } else if (CurrencyEnum.ONT_ASSET.contains(currencyEnum)) {
            return CurrencyEnum.ONT;
        }
        return currencyEnum;
    }

    public long getConfirmNum() {
        return this.confirmNum;
    }

    public long getDepositConfirmNum() {

        return this.depositConfirmNum;
    }

    public long getWithdrawConfirmNum() {

        return this.withdrawConfirmNum;
    }

    public BigDecimal getDecimal() {
        return this.decimal;
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public String getContractAddress() {
        return this.remark;
    }

    enum Decimal {
        ZERO(1),
        ONE(10),
        TWO(100),
        THREE(1_000),
        FOUR(10_000),
        FIVE(100_000),
        SIX(1000_000),
        EIGHT(100_000_000L),
        NINE(1_000_000_000L),
        EIGHTEEN(1000_000_000_000_000_000L);
        private final long decimal;

        Decimal(final long dec) {
            this.decimal = dec;
        }

        public long getDecimal() {
            return this.decimal;
        }
    }
}
