package cc.newex.dax.perpetual.common.constant;

public class CommonPropKeys {
    /**
     * 仓位保证金等级配置
     */
    private static final String POSITION_LEVEL = "position_level_%s";

    public static String getPositionLevel(final String currencyCode) {
        return String.format(CommonPropKeys.POSITION_LEVEL, currencyCode.toLowerCase());
    }

}
