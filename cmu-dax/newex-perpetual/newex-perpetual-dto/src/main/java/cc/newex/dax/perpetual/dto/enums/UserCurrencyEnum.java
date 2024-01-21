package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/5/30 17:03
 * @Description:
 */
public enum UserCurrencyEnum {
    USD(0, "USD", "美元", "$"),
    CNY(1, "CNY", "人民币", "¥");

    private int code;
    private String name;
    private String nameCn;

    /**
     * 法币符号
     */
    private String nativeCurrencyMark;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNativeCurrencyMark() {
        return nativeCurrencyMark;
    }

    public void setNativeCurrencyMark(String nativeCurrencyMark) {
        this.nativeCurrencyMark = nativeCurrencyMark;
    }

    UserCurrencyEnum(int code, String name, String nameCn, String nativeCurrencyMark) {
        this.code = code;
        this.name = name;
        this.nameCn = nameCn;
        this.nativeCurrencyMark = nativeCurrencyMark;
    }


    public static String getNativeCurrencyMark(int code) {
        for (UserCurrencyEnum ele : values()) {
            if (ele.getCode() == code) {
                return ele.getNativeCurrencyMark();
            }
        }
        return USD.getNativeCurrencyMark();
    }
//    public static int getCodeByName(String name) {
//        for (CurrencyEnum ele : values()) {
//            if (StringUtils.equals(ele.name, name.toLowerCase())) {
//                return ele.getCode();
//            }
//        }
//        return -1;
//    }

    public static String getNameByCode(int code) {
        for (UserCurrencyEnum ele : values()) {
            if (ele.code == code) {
                return ele.getName();
            }
        }
        return "";
    }
}

