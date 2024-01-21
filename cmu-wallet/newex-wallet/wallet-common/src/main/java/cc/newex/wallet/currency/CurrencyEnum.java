package cc.newex.wallet.currency;

import cc.newex.wallet.exception.UnsupportedCurrency;
import com.google.common.collect.Sets;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Set;

import static cc.newex.wallet.currency.CurrencyEnum.Decimal.EIGHT;
import static cc.newex.wallet.currency.CurrencyEnum.Decimal.EIGHTEEN;
import static cc.newex.wallet.currency.CurrencyEnum.Decimal.FIVE;
import static cc.newex.wallet.currency.CurrencyEnum.Decimal.FOUR;
import static cc.newex.wallet.currency.CurrencyEnum.Decimal.NINE;
import static cc.newex.wallet.currency.CurrencyEnum.Decimal.SEVEN;
import static cc.newex.wallet.currency.CurrencyEnum.Decimal.SIX;
import static cc.newex.wallet.currency.CurrencyEnum.Decimal.THREE;
import static cc.newex.wallet.currency.CurrencyEnum.Decimal.TWELVE;
import static cc.newex.wallet.currency.CurrencyEnum.Decimal.TWO;
import static cc.newex.wallet.currency.CurrencyEnum.Decimal.ZERO;

/**
 * @author newex-team
 * @data 27/03/2018
 */

public enum CurrencyEnum {

    BTC(1, "btc", 7, 1, 6, EIGHT),
    ETH(2, "eth", 121, 12, 120, EIGHTEEN),
    LTC(3, "ltc", 7, 1, 6, EIGHT, CurrencyOnlineEnum.OFFLINE.getCode()),
    ETC(4, "etc", 201, 60, 200, EIGHTEEN, CurrencyOnlineEnum.OFFLINE.getCode()),
    BCH(5, "bch", 7, 1, 6, EIGHT, CurrencyOnlineEnum.OFFLINE.getCode()),
    USDT(6, "usdt", 7, 1, 6, EIGHT),
    ERC20(7, "erc20", 121, 12, 120, EIGHTEEN, CurrencyOnlineEnum.OFFLINE.getCode()),
    MTX(8, "mtx", 121, 12, 120, EIGHTEEN, "0x0af44e2784637218dd1d32a322d44e603a8f0c6a", CurrencyOnlineEnum.OFFLINE.getCode()),
    CPG(9, "cpg", 121, 12, 120, EIGHTEEN, "0x6620749a9bd0ba09e9921ad77f040d297a861b49", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    OMG(10, "omg", 121, 12, 120, EIGHTEEN, "0xd26114cd6EE289AccF82350c8d8487fedB8A0C07", CurrencyOnlineEnum.OFFLINE.getCode()),

    CHP(11, "chp", 121, 12, 120, EIGHTEEN, "0xf3db7560E820834658B590C96234c333Cd3D5E5e", CurrencyOnlineEnum.OFFLINE.getCode()),
    CEL(12, "cel", 121, 12, 120, FOUR, "0xaaAEBE6Fe48E54f431b0C390CfaF0b017d09D42d", CurrencyOnlineEnum.OFFLINE.getCode()),
    LYM(13, "lym", 121, 12, 120, EIGHTEEN, "0x57aD67aCf9bF015E4820Fbd66EA1A21BED8852eC", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    ACT(14, "act", 61, 12, 60, FIVE, CurrencyOnlineEnum.OFFLINE.getCode()),

    KCASH(15, "kcash", 61, 12, 60, FIVE, "COND41iays8576giHf6M6Yox1DiBrDmgVyzJ", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    SSC(16, "ssc", 61, 12, 60, FIVE, CurrencyOnlineEnum.OFFLINE.getCode()),

    LET(17, "let", 61, 12, 60, FIVE, "CONDuPQkPuKqD5NM2XwHJCjnZiiTs2GAJDLB", CurrencyOnlineEnum.OFFLINE.getCode()),
    CTB(18, "ctb", 121, 12, 120, EIGHTEEN, "0x43e7e2e3a893a7eaa5219ffc087c7497c350c8c2", CurrencyOnlineEnum.OFFLINE.getCode()),

    BMB(19, "bmb", 121, 12, 120, EIGHT, "0x9c1f0de4fec6ae8efb65f2e45a1c1b625e35e7bf", CurrencyOnlineEnum.OFFLINE.getCode()),
    XRP(20, "xrp", 11, 5, 10, SIX, CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    IOTA(21, "iota", 11, 5, 10, SIX, CurrencyOnlineEnum.OFFLINE.getCode()),

    BTM(22, "btm", 12, 2, 10, EIGHT, "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", CurrencyOnlineEnum.OFFLINE.getCode()),
    TRX(23, "trx", 121, 12, 120, SIX, CurrencyOnlineEnum.OFFLINE.getCode()),
    LUCKYWIN(24, "luckywin", 121, 12, 120, EIGHTEEN, CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * ERC20，18位精度
     */
    CT(25, "ct", 121, 12, 120, EIGHTEEN, "0x0657e8eF31Ab026133EA6358bBe2bf6cE611e451", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 点卡
     */
    CMDK(26, "cmdk", 121, 12, 120, EIGHT, CurrencyOnlineEnum.OFFLINE.getCode()),

    DDD(27, "ddd", 121, 12, 120, EIGHTEEN, "0x9f5f3cfd7a32700c93f971637407ff17b91c7342", CurrencyOnlineEnum.OFFLINE.getCode()),
    MVP(28, "mvp", 121, 12, 120, EIGHTEEN, "0x8a77e40936bbc27e80e9a3f526368c967869c86d", CurrencyOnlineEnum.OFFLINE.getCode()),
    BAIC(29, "baic", 1, 2, 1, EIGHTEEN, "baic-baic-BAIC", CurrencyOnlineEnum.OFFLINE.getCode()),
    WIN(30, "win", 121, 12, 120, EIGHTEEN, CurrencyOnlineEnum.OFFLINE.getCode()),
    EOS(31, "eos", 1, 2, 1, FOUR, "eos-eosio.token-EOS", CurrencyOnlineEnum.OFFLINE.getCode()),
    ADA(32, "ada", 121, 12, 120, EIGHTEEN, CurrencyOnlineEnum.OFFLINE.getCode()),
    GET(33, "get", 121, 12, 120, EIGHTEEN, "0x60c68a87be1e8a84144b543aacfa77199cd3d024", CurrencyOnlineEnum.OFFLINE.getCode()),
    OF(34, "of", 121, 12, 120, THREE, CurrencyOnlineEnum.OFFLINE.getCode()),
    NEO(35, "neo", 4, 3, 3, ZERO, "0xc56f33fc6ecfcd0c225c4ab356fee59390af8560be0e930faebe74a6daff7c9b", CurrencyOnlineEnum.OFFLINE.getCode()),
    GAS(36, "gas", 4, 3, 3, EIGHT, "0x602c79718b16e442de58778e148d0b1084e3b2dffd5de6b7b16cee7969282de7", CurrencyOnlineEnum.OFFLINE.getCode()),
    MDS(38, "mds", 121, 12, 120, EIGHTEEN, "0x66186008C1050627F979d464eABb258860563dbE", CurrencyOnlineEnum.OFFLINE.getCode()),
    SDS(37, "sds", 4, 3, 3, EIGHT, "0x6fad54d8cc692fc808fd97a207836a846c217705", CurrencyOnlineEnum.OFFLINE.getCode()),
    GNX(39, "gnx", 121, 12, 120, NINE, "0x6ec8a24cabdc339a06a172f8223ea557055adaa5", CurrencyOnlineEnum.OFFLINE.getCode()),
    RRC(40, "rrc", 121, 12, 120, TWO, "0xB6259685685235c1eF4B8529e7105f00BD42b9f8", CurrencyOnlineEnum.OFFLINE.getCode()),
    DPY(41, "dpy", 121, 12, 120, EIGHTEEN, "0x6c2adc2073994fb2ccc5032cc2906fa221e9b391", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    ONT(42, "ont", 4, 3, 3, ZERO, "0100000000000000000000000000000000000000", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    ONG(43, "ong", 4, 3, 3, NINE, "0200000000000000000000000000000000000000", CurrencyOnlineEnum.OFFLINE.getCode()),

    FTI(44, "fti", 121, 12, 120, EIGHTEEN, "0x943ed852dadb5c3938ecdc6883718df8142de4c8", CurrencyOnlineEnum.OFFLINE.getCode()),
    YOU(45, "you", 121, 12, 120, EIGHTEEN, "0x34364bee11607b1963d66bca665fde93fca666a8", CurrencyOnlineEnum.OFFLINE.getCode()),
    HUR(46, "hur", 121, 12, 120, EIGHTEEN, "0xcdb7ecfd3403eef3882c65b761ef9b5054890a47", CurrencyOnlineEnum.OFFLINE.getCode()),
    NEP5(47, "nep5", 101, 10, 100, EIGHTEEN, CurrencyOnlineEnum.OFFLINE.getCode()),
    ORC_TOKEN(48, "orcToken", 121, 12, 120, EIGHTEEN, CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    PAG(49, "pag", 121, 12, 120, FOUR, "0x000028009c219f9abe02d3fbf4edc43def7e7ddb7224257384", CurrencyOnlineEnum.OFFLINE.getCode()),

    FBS001(50, "FBS001", 100, 100, 100, ZERO, CurrencyOnlineEnum.OFFLINE.getCode()),
    CMX06(51, "cmx06", 100, 100, 100, ZERO, CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    PST(52, "pst", 121, 12, 120, EIGHTEEN, "0x5d4abc77b8405ad177d8ac6682d584ecbfd46cec", CurrencyOnlineEnum.OFFLINE.getCode()),

    BKBT(53, "bkbt", 121, 12, 120, EIGHTEEN, "0x6a27348483d59150ae76ef4c0f3622a78b0ca698", CurrencyOnlineEnum.OFFLINE.getCode()),
    PVB(54, "pvb", 121, 12, 120, EIGHTEEN, "0xcb324e4c8c1561d547c38bd1d4a3b12a405b8019", CurrencyOnlineEnum.OFFLINE.getCode()),
    LRT(55, "lrt", 121, 12, 120, EIGHT, "0xe0f0abce99ba75e0a369514cf4359cc698824efc", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    EOSC(56, "eosc", 61, 30, 60, FOUR, "eosc-eosio-EOS", CurrencyOnlineEnum.OFFLINE.getCode()),

    ZXT(57, "zxt", 121, 12, 120, EIGHTEEN, "0x8ed5afcb8877624802a0cbfb942c95c2b7c87146", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    ADD(58, "add", 61, 30, 60, FOUR, "eos-eosadddddddd-ADD", CurrencyOnlineEnum.OFFLINE.getCode()),

    MEETONE(59, "meetone", 61, 30, 60, FOUR, "eos-eosiomeetone-MEETONE", CurrencyOnlineEnum.OFFLINE.getCode()),
    EETH(60, "eeth", 61, 30, 60, FOUR, "eos-ethsidechain-EETH", CurrencyOnlineEnum.OFFLINE.getCode()),
    PANDA(61, "panda", 121, 12, 120, EIGHTEEN, "0x0a5dc2204dfc6082ef3bbcfc3a468f16318c4168", CurrencyOnlineEnum.OFFLINE.getCode()),
    GUSD(62, "gusd", 121, 12, 120, TWO, "0x056fd409e1d7a124bd7017459dfea2f387b6d5cd", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    EOSDAC(63, "eosdac", 61, 30, 60, FOUR, "eos-eosdactokens-EOSDAC", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    XLM(64, "xlm", 31, 15, 30, SEVEN, CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    ZRX(65, "zrx", 121, 12, 120, EIGHTEEN, "0xe41d2489571d322189246dafa5ebde1f4699f498", CurrencyOnlineEnum.OFFLINE.getCode()),

    MKR(66, "mkr", 121, 12, 120, EIGHTEEN, "0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    AE(67, "ae", 121, 12, 120, EIGHTEEN, "0x5ca9a71b1d01849c0a95490cc00559717fcf0d1d", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线，ERC20
     */
    BAT(68, "bat", 121, 12, 120, EIGHTEEN, "0x0d8775f648430679a709e98d2b0cb6250d2887ef", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线，ERC20
     */
    REP(69, "rep", 121, 12, 120, EIGHTEEN, "0x1985365e9f78359a9B6AD760e32412f4a445E862", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    WTC(70, "wtc", 121, 12, 120, EIGHTEEN, "0xb7cb1c96db6b22b0d3d9536e0108d062bd488f74", CurrencyOnlineEnum.OFFLINE.getCode()),

    GNT(71, "gnt", 121, 12, 120, EIGHTEEN, "0xa74476443119A942dE498590Fe1f2454d7D4aC0d", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线，ERC20
     */
    PPT(72, "ppt", 121, 12, 120, EIGHT, "0xd4fa1460f537bb9085d22c7bccb5dd450ef28e3a", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线，ERC20
     */
    SNT(73, "snt", 121, 12, 120, EIGHTEEN, "0x744d70fdbe2ba4cf95131626614a1763df805b9e", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    DOGE(74, "doge", 31, 6, 30, EIGHT, CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    QTB(75, "qtb", 121, 12, 120, EIGHTEEN, "0x0317ada015cf35244b9f9c7d1f8f05c3651833ff", CurrencyOnlineEnum.OFFLINE.getCode()),

    FC(76, "fc", 121, 12, 120, EIGHT, "0x659c6a81AFa57A75a3E512587E920b97304D330a", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    TUSD(77, "tusd", 121, 12, 120, EIGHTEEN, "0x0000000000085d4780B73119b644AE5ecd22b376", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    PAX(78, "pax", 121, 12, 120, EIGHTEEN, "0x8e870d67f660d95d5be530380d0ec0bd388289e1", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    USDC(79, "usdc", 121, 12, 120, SIX, "0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    DASH(80, "dash", 61, 20, 60, EIGHT, CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    XEM(81, "xem", 11, 10, 10, SIX, CurrencyOnlineEnum.OFFLINE.getCode()),

    QBT(82, "qbt", 121, 12, 120, EIGHTEEN, "0x4b4fB2F2DC64a2cf2AFBc8119ca2Eabbd99a575b", CurrencyOnlineEnum.OFFLINE.getCode()),
    KBCC(83, "kbcc", 121, 12, 120, SIX, "0x6ac544849414fbafdc0adba7c77bbd73a7d36ac1", CurrencyOnlineEnum.OFFLINE.getCode()),
    ELET(84, "elet", 121, 12, 120, EIGHT, "0x0568025c55c21bda4bc488f3107ebfc8b3d3ef2d", CurrencyOnlineEnum.OFFLINE.getCode()),
    LRC(85, "lrc", 121, 12, 120, EIGHTEEN, "0xBBbbCA6A901c926F240b89EacB641d8Aec7AEafD", CurrencyOnlineEnum.OFFLINE.getCode()),
    OLT(86, "olt", 121, 12, 120, EIGHTEEN, "0x64a60493d888728cf42616e034a0dfeae38efcf0", CurrencyOnlineEnum.OFFLINE.getCode()),
    LRN(87, "lrn", 4, 3, 3, EIGHT, "0x06fa8be9b6609d963e8fc63977b9f8dc5f10895f", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    BSV(88, "bsv", 7, 1, 6, EIGHT, CurrencyOnlineEnum.OFFLINE.getCode()),

    GARD(89, "gard", 121, 12, 120, EIGHTEEN, "0x5c64031c62061865e5fd0f53d3cdaef80f72e99d", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * 下线
     */
    RBTC(90, "rbtc", 121, 12, 120, EIGHTEEN, CurrencyOnlineEnum.OFFLINE.getCode()),

    ZEC(91, "zec", 13, 2, 12, EIGHT, CurrencyOnlineEnum.OFFLINE.getCode()),
    RIF(92, "rif", 121, 12, 120, EIGHTEEN, "0x2acc95758f8b5f583470ba265eb685a8f45fc9d5", CurrencyOnlineEnum.OFFLINE.getCode()),
    RSK_TOKEN(93, "rskToken", 121, 12, 120, EIGHTEEN, CurrencyOnlineEnum.OFFLINE.getCode()),
    FBTC(94, "fbtc", 1, 1, 1, EIGHT, "contract-fake", CurrencyOnlineEnum.OFFLINE.getCode()),
    XMR(95, "xmr", 18, 16, 17, TWELVE, CurrencyOnlineEnum.OFFLINE.getCode()),
    LGA(96, "lga", 7, 2, 6, EIGHT, CurrencyOnlineEnum.OFFLINE.getCode()),
    DUSD(97, "dusd", 1, 2, 1, NINE, "dusd-baic.token-DUSD", CurrencyOnlineEnum.OFFLINE.getCode()),
    SWAG(98, "swag", 121, 12, 120, EIGHTEEN, "0x7040a8245eea5dc0af3c43264167a02d939303eb", CurrencyOnlineEnum.OFFLINE.getCode()),
    ARN(99, "arn", 121, 12, 120, EIGHT, "0xBA5F11b16B155792Cf3B2E6880E8706859A8AEB6", CurrencyOnlineEnum.OFFLINE.getCode()),
    MRS(100, "mrs", 121, 12, 120, EIGHTEEN, "0x1254e59712e6e727dc71e0e3121ae952b2c4c3b6", CurrencyOnlineEnum.OFFLINE.getCode()),
    SDUSD(101, "sdusd", 4, 3, 3, EIGHT, "0x7146278a76c33fc6bb870fcaa428e3cdb16809ac", CurrencyOnlineEnum.OFFLINE.getCode()),
    WBBT(102, "wbbt", 121, 12, 120, EIGHTEEN, "0xf5894cfe8d86a38bb189b6ed60008033440766b5", CurrencyOnlineEnum.OFFLINE.getCode()),
    VOLLAR(103, "vollar", 21, 15, 20, EIGHT, CurrencyOnlineEnum.OFFLINE.getCode()),
    GOD(104, "god", 11, 10, 10, EIGHT, CurrencyOnlineEnum.OFFLINE.getCode()),
    RC(105, "rc", 61, 20, 60, EIGHT, CurrencyOnlineEnum.OFFLINE.getCode()),
    BOSC(106, "bosc", 121, 12, 120, EIGHTEEN, "0x761341ca3B8186323E55c7e801ecbE9B5DF41aD0", CurrencyOnlineEnum.OFFLINE.getCode()),
    AUA(107, "aua", 61, 30, 60, FOUR, "eos-auaglobalcom-AUA", CurrencyOnlineEnum.OFFLINE.getCode()),

    BTL(108, "btl", 121, 12, 120, EIGHTEEN, "0xC45a9d385E284F30E07B6888ca4233D7bcd2F372", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * Ladder Network Token, ERC20
     */
    LAD(109, "lad", 121, 12, 120, EIGHTEEN, "0x4129d3b7a6a2c5c997774077ac02bdafd1af1d6a", CurrencyOnlineEnum.OFFLINE.getCode()),

    /**
     * Ace crescent network (ACN), ERC20
     */
    ACN(110, "acn", 121, 12, 120, EIGHTEEN, "0x3b98c8f8f028060d0e0127843ee13f4b995a7960", CurrencyOnlineEnum.OFFLINE.getCode()),

    CNY(10000, "cny", 121, 12, 120, EIGHT),
    USD(10001, "usd", 121, 12, 120, EIGHT);

    public static Set<CurrencyEnum> ERC20_SET = Sets.immutableEnumSet(MTX, CPG, CHP, CEL, LYM, CTB, BMB, DDD,
            MVP, GET, MDS, GNX, RRC, DPY, FTI, YOU, HUR, BKBT, PVB, LRT, ZXT, PANDA, GUSD, MKR,
            GNT, FC, QBT, KBCC, ELET, LRC, OLT, GARD, SWAG, ARN,
            MRS, WBBT, BOSC, BTL, LAD, CT);

    public static Set<CurrencyEnum> ATP_SET = Sets.immutableEnumSet(KCASH, LET);
    public static Set<CurrencyEnum> FAKE_SET = Sets.immutableEnumSet(FBTC);

    public static Set<CurrencyEnum> ONT_ASSET = Sets.immutableEnumSet(ONT, ONG);

    public static Set<CurrencyEnum> ORC_TOKEN_SET = Sets.immutableEnumSet(PAG);
    public static Set<CurrencyEnum> NEP5_SET = Sets.immutableEnumSet(SDS, LRN, SDUSD);
    public static Set<CurrencyEnum> NEO_ASSET = Sets.immutableEnumSet(GAS, NEO);
    public static Set<CurrencyEnum> EOS_ASSET = Sets.immutableEnumSet(MEETONE, EETH, AUA);
    //    public static Set<CurrencyEnum> DUSD_ASSET = Sets.immutableEnumSet();
    public static Set<CurrencyEnum> RSK_TOKEN_ASSET = Sets.immutableEnumSet(RIF);

    private final int index;
    private final String name;
    private final String remark;
    private final long confirmNum;
    private final long depositConfirmNum;
    private final long withdrawConfirmNum;
    private final BigDecimal decimal;

    /**
     * 0-下线，1-上线
     */
    private final int online;

    CurrencyEnum(final int index, final String name, final int confirmNum, final int depositConfirmNum,
                 final int withdrawConfirmNum, final Decimal decimal) {
        this(index, name, confirmNum, depositConfirmNum, withdrawConfirmNum, decimal, "", CurrencyOnlineEnum.ONLINE.getCode());
    }

    /**
     * @param index
     * @param name
     * @param confirmNum         : 更新到多少个确认数
     * @param depositConfirmNum  ： 充值确认数
     * @param withdrawConfirmNum ： 提现确认数
     */
    CurrencyEnum(final int index, final String name, final int confirmNum, final int depositConfirmNum,
                 final int withdrawConfirmNum, final Decimal decimal, final int online) {
        this(index, name, confirmNum, depositConfirmNum, withdrawConfirmNum, decimal, "", online);
    }

    CurrencyEnum(final int index, final String name, final int confirmNum, final int depositConfirmNum,
                 final int withdrawConfirmNum, final Decimal decimal, final String remark) {
        this(index, name, confirmNum, depositConfirmNum, withdrawConfirmNum, decimal, remark, CurrencyOnlineEnum.ONLINE.getCode());
    }

    CurrencyEnum(final int index, final String name, final int confirmNum, final int depositConfirmNum,
                 final int withdrawConfirmNum, final Decimal decimal, final String remark, final int online) {
        this.index = index;
        this.name = name;
        this.confirmNum = confirmNum;
        this.depositConfirmNum = depositConfirmNum;
        this.withdrawConfirmNum = withdrawConfirmNum;
        this.decimal = BigDecimal.valueOf(decimal.getDecimal());
        this.remark = remark;
        this.online = online;
    }

    public static CurrencyEnum parseValue(final int index) {
        for (final CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            if (currencyEnum.getIndex() == index) {
                return currencyEnum;
            }
        }
        throw new UnsupportedCurrency(String.valueOf(index));
    }

    public static CurrencyEnum parseName(final String name) {
        for (final CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            if (currencyEnum.getName().equalsIgnoreCase(name.toLowerCase())) {
                return currencyEnum;
            }
        }
        throw new UnsupportedCurrency(name);
    }

    public static CurrencyEnum parseContract(final String contract) {
        if (StringUtils.hasText(contract)) {
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
    public static CurrencyEnum toMainCurrency(final CurrencyEnum currencyEnum) {
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
        } else if (CurrencyEnum.EOS_ASSET.contains(currencyEnum)) {
            return CurrencyEnum.EOS;
        } else if (CurrencyEnum.RSK_TOKEN_ASSET.contains(currencyEnum)) {
            return CurrencyEnum.RBTC;
        } else if (CurrencyEnum.FAKE_SET.contains(currencyEnum)) {
            //用来为模拟盘的币生成地址
            return CurrencyEnum.BTC;
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

    public int getOnline() {
        return this.online;
    }

    public boolean isContractFakeCurrency() {
        return "contract-fake".equals(this.remark);
    }

    public static enum Decimal {
        ZERO(1),
        ONE(10),
        TWO(100),
        THREE(1_000),
        FOUR(10_000),
        FIVE(100_000),
        SIX(1000_000),
        SEVEN(10_000_000L),

        /**
         * 8位
         */
        EIGHT(100_000_000L),

        /**
         * 9位
         */
        NINE(1_000_000_000L),

        /**
         * 12位
         */
        TWELVE(1_000_000_000_000L),

        /**
         * 18位
         */
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
