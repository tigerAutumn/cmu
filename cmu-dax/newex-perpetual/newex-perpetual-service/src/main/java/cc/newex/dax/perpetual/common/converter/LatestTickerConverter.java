package cc.newex.dax.perpetual.common.converter;

import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.LatestTicker;
import cc.newex.dax.perpetual.dto.LatestTickerDTO;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author newex-team
 * @date 2018/11/01
 */
public class LatestTickerConverter {

  public static LatestTickerDTO convert(final LatestTicker latestTicker, final Contract contract) {
    if (latestTicker == null) {
      return null;
    }
    final LatestTickerDTO tickerDTO = LatestTickerDTO.builder()
            .contractCode(contract.getContractCode()).high(latestTicker.getHigh().stripTrailingZeros().toPlainString())
            .low(latestTicker.getLow().stripTrailingZeros().toPlainString())
            .amount24(latestTicker.getAmount24().stripTrailingZeros().toPlainString())
            .size24(latestTicker.getSize24().stripTrailingZeros().toPlainString())
            .first(latestTicker.getFirst().stripTrailingZeros().toPlainString())
            .last(latestTicker.getLast().stripTrailingZeros().toPlainString())
            .change24(latestTicker.getChange24().stripTrailingZeros().toPlainString())
            .buy(latestTicker.getBuy() == null ? "0" : latestTicker.getBuy().stripTrailingZeros().toPlainString())
            .sell(latestTicker.getSell() == null ? "0" : latestTicker.getSell().stripTrailingZeros().toPlainString())
        .changePercentage(getPercentage(latestTicker)).createdDate(latestTicker.getCreatedDate())
        .build();

    return tickerDTO;
  }

  /**
   * PM说保留2位
   *
   * @param ticker
   * @return
   */
  public static String getPercentage(final LatestTicker ticker) {
    if (ticker == null || ticker.getChange24() == null || ticker.getFirst() == null
        || BigDecimal.ZERO.compareTo(ticker.getFirst()) == 0) {
      return "0.00";
    }
    final BigDecimal percentage = ticker.getChange24()
        .divide(ticker.getFirst(), 4, RoundingMode.CEILING).multiply(BigDecimal.valueOf(100));

    return percentage.stripTrailingZeros().toPlainString();
  }

  // /**
  // * 最新成交数据列表
  // *
  // * @param latestTickerList
  // * @return
  // */
  // public static List<Object[]> toObjectArray(final List<LatestTickerDTO> latestTickerList) {
  // if (CollectionUtils.isEmpty(latestTickerList)) {
  // return Lists.newArrayListWithCapacity(0);
  // }
  //
  // return latestTickerList.stream().map(latestTicker -> toObjectArray(latestTicker))
  // .collect(Collectors.toList());
  // }

  /**
   * 最新成交价数据
   *
   * @param latestTicker
   */
  public static Object[] toObjectArray(final LatestTickerDTO latestTicker,
      final BigDecimal positionAmount, final MarkIndexPriceDTO markIndexPriceDTO) {
    return new Object[] {latestTicker.getCreatedDate().getTime(), // 时间
        latestTicker.getHigh(), // 最高价
        latestTicker.getLow(), // 最低价
        latestTicker.getAmount24(), // 成交张数
        latestTicker.getSize24(), // 成交价值
            positionAmount == null ? BigDecimal.ZERO : positionAmount, // 持仓量
        markIndexPriceDTO.getFeeRate(), // 资金费率
        markIndexPriceDTO.getMarkPrice(), // 标记价格
        latestTicker.getChangePercentage(), // 24小时价格涨跌幅比例
            latestTicker.getContractCode(),// 币对
    };
  }


  /**
   * tickers最新成交价数据，和push一致
   *
   * @param latestTicker
   */
  public static Object[] tickersToObjectArray(final LatestTickerDTO latestTicker,
                                              final BigDecimal positionAmount, final MarkIndexPriceDTO markIndexPriceDTO) {
    return new Object[]{latestTicker.getCreatedDate().getTime(), // 时间
            latestTicker.getHigh(), // 最高价
            latestTicker.getLow(), // 最低价
            latestTicker.getAmount24(), // 成交张数
            latestTicker.getSize24(), // 成交价值
            latestTicker.getFirst(), // 原始成交价
            latestTicker.getLast(), // 最新成交价
            latestTicker.getChange24(), // 24小时价格涨跌幅
            latestTicker.getChangePercentage(), // 24小时价格涨跌幅比例
            latestTicker.getBuy(), // 盘口买一
            latestTicker.getSell(), // 盘口卖一
            latestTicker.getContractCode(),// 币对
    };
  }
}
