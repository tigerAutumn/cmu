package cc.newex.dax.perpetual.matching.bean;

import java.util.Iterator;
import java.util.LinkedList;

import cc.newex.dax.perpetual.domain.bean.ShortOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortOrders implements Iterable<ShortOrder> {
  private ShortOrder takerOrCancelledOrder;
  private ShortOrder makerOrder;

  @Override
  public Iterator<ShortOrder> iterator() {
    return new OrderIterator();
  }

  /**
   * 迭代order
   *
   * @author xionghui
   * @date 2018/10/22
   */
  private class OrderIterator implements Iterator<ShortOrder> {
    LinkedList<ShortOrder> shortOrderList = new LinkedList<>();

    OrderIterator() {
      if (ShortOrders.this.takerOrCancelledOrder != null) {
        this.shortOrderList.add(ShortOrders.this.takerOrCancelledOrder);
      }
      if (ShortOrders.this.makerOrder != null) {
        this.shortOrderList.add(ShortOrders.this.makerOrder);
      }
    }

    @Override
    public boolean hasNext() {
      return this.shortOrderList.size() > 0;
    }

    @Override
    public ShortOrder next() {
      return this.shortOrderList.removeFirst();
    }
  }
}
