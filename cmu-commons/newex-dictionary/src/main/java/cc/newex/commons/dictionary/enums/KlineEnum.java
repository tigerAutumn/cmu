package cc.newex.commons.dictionary.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Calendar;

/**
 * @author newex-team
 * @date 2017/12/09
 */

public enum KlineEnum {
    MIN1(0, "1min", 1, 0, Calendar.MINUTE),
    MIN3(7, "3min", 3, 0, Calendar.MINUTE),
    MIN5(1, "5min", 5, 0, Calendar.MINUTE),
    MIN15(2, "15min", 15, 1, Calendar.MINUTE),
    MIN30(9, "30min", 30, 2, Calendar.MINUTE),
    HUOR1(10, "1hour", 1, 9, Calendar.HOUR_OF_DAY),
    HUOR2(11, "2hour", 2, 10, Calendar.HOUR_OF_DAY),
    HUOR4(12, "4hour", 4, 11, Calendar.HOUR_OF_DAY),
    HUOR6(13, "6hour", 6, 11, Calendar.HOUR_OF_DAY),

    HUOR12(14, "12hour", 12, 13, Calendar.HOUR_OF_DAY),
    DAY(3, "day", 1, 14, Calendar.DAY_OF_MONTH),
    //DAY3(15, "3day", 3, 3, Calendar.DAY_OF_MONTH),
    WEEK(4, "week", 1, 3, Calendar.DAY_OF_WEEK);
    /**
     * 周期
     */
    private final Integer type;
    /***
     * 周期字符串
     */
    private final String typeStr;
    /***
     * 周期分钟数据
     */
    private final Integer period;
    /**
     * 基准周期
     */
    private final Integer bassType;
    /**
     * 计算周期类型
     */
    private final Integer timeType;

    /**
     * 类型对应数据库类型
     *
     * @param type       int类型
     * @param typeString 字符串类型
     * @param period     周期
     * @param bassType   基准类型
     * @param timeType   时间
     */
    KlineEnum(final Integer type, final String typeString, final int period, final int bassType, final int timeType) {
        this.type = type;
        this.typeStr = typeString;
        this.period = period;
        this.bassType = bassType;
        this.timeType = timeType;
    }

    public static Integer getTypeInteger(final String typeStr) {
        if (StringUtils.isEmpty(typeStr)) {
            return null;
        }
        final KlineEnum klineEnum = Arrays.stream(KlineEnum.values()).filter(
                klineEnumTmp -> klineEnumTmp.typeStr.equalsIgnoreCase
                        (typeStr))
                .findFirst
                        ().orElse(null);
        if (klineEnum == null) {
            return null;
        }
        return klineEnum.type;
    }

    public static String getTypeString(final Integer type) {
        if (type == null) {
            return null;
        }
        final KlineEnum klineEnum = Arrays.stream(KlineEnum.values()).filter(
                klineEnumTmp -> klineEnumTmp.type.intValue() == type.intValue())
                .findFirst
                        ().orElse(null);
        if (klineEnum == null) {
            return null;
        }
        return klineEnum.typeStr;
    }

    public Integer getType() {
        return this.type;
    }

    public String getTypeStr() {
        return this.typeStr;
    }

    public Integer getPeriodMinute() {
        switch (this.timeType) {
            case Calendar.HOUR_OF_DAY:
                return this.period * 60;
            case Calendar.DAY_OF_MONTH:
                return this.period * 60 * 24;
            case Calendar.DAY_OF_WEEK:
                return this.period * 60 * 24 * 7;
            default:
                return this.period;
        }
    }

    public Integer getPeriod() {
        return this.period;
    }

    public Integer getBassType() {
        return this.bassType;
    }

    public Integer getTimeType() {
        return this.timeType;
    }
}

