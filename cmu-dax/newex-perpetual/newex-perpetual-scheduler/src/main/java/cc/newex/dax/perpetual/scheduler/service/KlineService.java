package cc.newex.dax.perpetual.scheduler.service;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.dax.perpetual.domain.Contract;

public interface KlineService {

  /**
   * 生成一分钟K线数据
   */
  void bulidOneMinuteKline(Contract contract, KlineEnum klineEnum);

  /**
   * 生成k线
   */
  void bulidKline(Contract contract, KlineEnum klineEnum);

  /**
   * 生成周k线
   */
  void bulidWeekKline(Contract contract, KlineEnum klineEnum);
}
