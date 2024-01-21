//package cc.newex.dax.boss.common.util;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
///**
// * JSON数据处理工具类
// *
// * @author liutiejun
// * @date 2018-07-09
// */
//public class GsonUtil {
//
//    public static final Gson _gson = initGson();
//
//    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
//
//    private static Gson initGson() {
//        final Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_PATTERN)
//                // 禁此序列化内部类
//                .disableInnerClassSerialization()
//                // 禁止转义html标签
//                .disableHtmlEscaping()
//                .create();
//
//        return gson;
//    }
//
//}
