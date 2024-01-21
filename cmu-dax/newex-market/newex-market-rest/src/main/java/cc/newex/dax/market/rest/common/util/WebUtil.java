package cc.newex.dax.market.rest.common.util;

import cc.newex.dax.market.domain.MarketFrom;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * 获取用户信息相关的工具方法。
 */
public class WebUtil {

    private static final BinaryOperator<List<MarketFrom>> NEVER_MERGE_FUNCTION = (v1, v2) -> {
        throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
    };
    public static String[] SITES = {"okcoin", "gdax", "bitfinex", "kraken", "bitstamp", "huobi"};

    public static String getClientAddress(final HttpServletRequest request) {
        String address = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(address)) {
            address = request.getHeader("X-Real-IP");
        }

        if (!StringUtils.isEmpty(address)) {
            final String[] ips = address.split(",");
            if (ips != null && ips.length > 0) {
                address = ips[0];
            }

            return address;
        } else {
            return request.getRemoteAddr();
        }
    }

    public static Map<String, Integer> getMapSite() {
        final Map<String, Integer> mapSites = new HashMap<>();
        mapSites.put("okcoin", 1);
        mapSites.put("huobi", 2);
        mapSites.put("gdax", 3);
        mapSites.put("bitfinex", 4);
        mapSites.put("kraken", 5);
        mapSites.put("bitstamp", 6);
        return mapSites;
    }

    /**
     * 判断  安卓，ios，pc
     *
     * @param isClient
     * @return
     */
    public static boolean isFromOk(final Integer isClient) {
        if (isClient == null) {
            return false;
        }
        if (isClient != 3 && isClient != 4 && isClient != 5) {
            return false;
        }
        return true;
    }

    public static Map<String, List<MarketFrom>> sortMapByKey(final Map<String, List<MarketFrom>> oriMap,
                                                                   final Map<String, Integer> sortKeys,
                                                                   final Map<String, Integer> sortValues) {
        return oriMap.entrySet().stream()
                .sorted(Comparator.comparing(e -> sortKeys.getOrDefault(e.getKey(), 0)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .filter(v -> ArrayUtils.contains(SITES, v.getName().toLowerCase()))
                                .sorted(Comparator.comparing(x -> sortValues.getOrDefault(x.getName().toLowerCase(), 0)))
                                .collect(Collectors.toList()),
                        NEVER_MERGE_FUNCTION,
                        LinkedHashMap::new
                ));
    }

}
