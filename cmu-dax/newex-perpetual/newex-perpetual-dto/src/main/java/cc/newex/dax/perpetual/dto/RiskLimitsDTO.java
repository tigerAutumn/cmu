package cc.newex.dax.perpetual.dto;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 风险限额
 * [{"minPositionQty":0,"level":1,"maintenanceMargin":0.0050,"initMargin":0.0100,"maxPositionQty":200.0000},{"minPositionQty":200.0000,"level":2,"maintenanceMargin":0.0100,"initMargin":0.0150,"maxPositionQty":300.0000},{"minPositionQty":300.0000,"level":3,"maintenanceMargin":0.0150,"initMargin":0.0200,"maxPositionQty":400.0000},{"minPositionQty":400.0000,"level":4,"maintenanceMargin":0.0200,"initMargin":0.0250,"maxPositionQty":500.0000}]
 *
 * @author newex-team
 * @date 2018/9/28
 */
@Data
public class RiskLimitsDTO {
  /**
   * 档位
   */
  private Integer level;
  /**
   * 最小仓位大小
   */
  private BigDecimal minPositionQty;
  /**
   * 最大仓位大小
   */
  private BigDecimal maxPositionQty;
  /**
   * 维持保证金
   */
  private BigDecimal maintenanceMargin;
  /**
   * 新委托的起始保证金
   */
  private BigDecimal initMargin;


  public static void main(final String[] args) {
    final List<RiskLimitsDTO> list = Lists.newArrayList();

    for (int i = 1; i < 5; i++) {
      final RiskLimitsDTO dto = new RiskLimitsDTO();
      dto.setLevel(i);
      dto.setMinPositionQty(new BigDecimal(100 * (i)).setScale(4, BigDecimal.ROUND_HALF_UP));
      dto.setMaxPositionQty(new BigDecimal(100 * (i + 1)).setScale(4, BigDecimal.ROUND_HALF_UP));
      dto.setMaintenanceMargin(new BigDecimal(0.005 * i).setScale(4, BigDecimal.ROUND_HALF_UP));
      dto.setInitMargin(dto.getMaintenanceMargin().add(new BigDecimal(0.005)).setScale(4,
          BigDecimal.ROUND_HALF_UP));
      list.add(dto);
    }
    System.out.println(JSON.toJSON(list));
  }
}
