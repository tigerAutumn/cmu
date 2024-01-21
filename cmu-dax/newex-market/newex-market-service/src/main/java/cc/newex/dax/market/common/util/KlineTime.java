package cc.newex.dax.market.common.util;

import java.util.HashMap;
import java.util.Map;

public class KlineTime {
    public static Map<String, Integer> map = null;
    public static Map<Integer, String> mapKey = null;

    static {
        KlineTime.map = new HashMap<>();
        KlineTime.map.put("1min", 0); // 0:1分钟
        KlineTime.map.put("3min", 7); // 7:3分钟
        KlineTime.map.put("5min", 1); // 1:5分钟
        KlineTime.map.put("15min", 2);// 2:15分钟
        KlineTime.map.put("30min", 9);// 9:30分钟
        KlineTime.map.put("1day", 3); // 3:日
        KlineTime.map.put("day", 3); // 3:日
        KlineTime.map.put("3day", 15);// 15：3日
        KlineTime.map.put("1week", 4);// 4:周
        KlineTime.map.put("week", 4);// 4:周
        //map.put("1month", 5); // 5:月
        //		map.put("1year", 6);// 6:年
        KlineTime.map.put("1hour", 10); // 10:1小时
        KlineTime.map.put("2hour", 11); // 11:2小时
        KlineTime.map.put("4hour", 12); // 12:4小时
        KlineTime.map.put("6hour", 13); // 13:6小时
        KlineTime.map.put("12hour", 14); // 14:12小时
    }

    static {
        KlineTime.mapKey = new HashMap<>();
        KlineTime.mapKey.put(0, "1min"); // 0:1分钟
        KlineTime.mapKey.put(7, "3min"); // 7:3分钟
        KlineTime.mapKey.put(1, "5min"); // 1:5分钟
        KlineTime.mapKey.put(2, "15min");// 2:15分钟
        KlineTime.mapKey.put(9, "30min");// 9:30分钟
        KlineTime.mapKey.put(3, "day"); // 3:日
        KlineTime.mapKey.put(15, "3day");// 15：3日
        KlineTime.mapKey.put(4, "week");// 4:周
        KlineTime.mapKey.put(10, "1hour"); // 10:1小时
        KlineTime.mapKey.put(11, "2hour"); // 11:2小时
        KlineTime.mapKey.put(12, "4hour"); // 12:4小时
        KlineTime.mapKey.put(13, "6hour"); // 13:6小时
        KlineTime.mapKey.put(14, "12hour"); // 14:12小时
    }
    //push

}
