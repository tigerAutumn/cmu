package cc.newex.dax.perpetual.common.converter;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.dax.perpetual.domain.MarketData;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * k线转换类
 *
 * @author newex-team
 * @date 2018-11-28
 */
public class MarketDataConverter {

  public static List<Object[]> toObjectArray(final List<MarketData> marketDataList,
      final Integer size) {
    if (CollectionUtils.isEmpty(marketDataList)) {
      return Lists.newArrayListWithCapacity(0);
    }

    final List<Object[]> klineData = marketDataList.stream()
        .map(marketData -> toObjectArray(marketData)).collect(Collectors.toList());

    if (size == null) {
      return klineData;
    }

    final int klineSize = klineData.size();
    if (size > klineSize) {
      return klineData;
    }

    return klineData.subList(klineSize - size, klineSize);
  }

  public static Object[] toObjectArray(final MarketData marketData) {
    return new Object[] {marketData.getCreatedDate().getTime(), // 时间
        NumberUtil.regularBigDecimalFromBigDecimal(marketData.getLow()), // 最低
        NumberUtil.regularBigDecimalFromBigDecimal(marketData.getHigh()), // 最高
        NumberUtil.regularBigDecimalFromBigDecimal(marketData.getOpen()), // 开盘价
        NumberUtil.regularBigDecimalFromBigDecimal(marketData.getClose()), // 收盘价
            NumberUtil.regularBigDecimalFromBigDecimal(marketData.getAmount() == null ? BigDecimal.ZERO : marketData.getAmount()), // 成交张数
            NumberUtil.regularBigDecimalFromBigDecimal(marketData.getSize() == null ? BigDecimal.ZERO : marketData.getSize())// 成交价值
    };
  }

}
