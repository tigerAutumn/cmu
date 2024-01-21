package cc.newex.dax.perpetual.matching.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import lombok.Data;

/**
 * 深度
 *
 * @author xionghui
 * @date 2018/10/25
 */
@Data
public class DepthCache {
  private final List<Depth> asks = new ArrayList<>();
  private final List<Depth> bids = new ArrayList<>();

  @Data
  public static class Depth {
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private final BigDecimal price;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private final BigDecimal totalAmount;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    private final BigDecimal sumTotalAmount;

    public Depth(BigDecimal price, BigDecimal totalAmount, BigDecimal sumTotalAmount) {
      this.price = price;
      this.totalAmount = totalAmount;
      this.sumTotalAmount = sumTotalAmount;
    }
  }
}
