package cc.newex.dax.push.service.impl.function.impl.perpetual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import cc.newex.commons.lang.json.BigDecimalSerializer;
import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.cache.PushRedisCache;
import cc.newex.dax.push.service.impl.function.FunctionService;
import cc.newex.dax.push.utils.BigDecimalSerializerUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PerpetualDepthFunctionServiceImpl implements FunctionService {
  private final Map<String, Depth> cacheMap = new ConcurrentHashMap<>();
  private final PushRedisCache pushRedisCache;

  public PerpetualDepthFunctionServiceImpl(final PushRedisCache pushRedisCache) {
    this.pushRedisCache = pushRedisCache;
  }

  @Override
  public boolean hasInit() {
    return true;
  }

  @Override
  public Object initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    final String key = askBean.getContractCode().toUpperCase();
    Depth depth = this.cacheMap.get(key);
    if (depth == null) {
      synchronized (this) {
        depth = this.cacheMap.get(key);
        if (depth == null) {
          depth = this.convert(this.pushRedisCache.getPerpetualDepth(key));
          depth.setInit(true);
          this.cacheMap.put(key, depth);
        }
      }
    }
    return depth == null ? null : this.regularResult(askBean, depth);
  }

  private Depth convert(final JSONObject jsonObject) {
    Depth Depth = jsonObject == null ? new Depth() : jsonObject.toJavaObject(Depth.class);
    if (Depth.getAsks() == null) {
      Depth.setAsks(new ArrayList<>());
    }
    if (Depth.getBids() == null) {
      Depth.setBids(new ArrayList<>());
    }
    return Depth;
  }

  private Object regularResult(final AskBean askBean, final Depth depth) {
    final List<String[]> bids = depth.getBids().stream()
        .map(b -> new String[] {BigDecimalSerializerUtil.serializer(b.getPrice()),
            BigDecimalSerializerUtil.serializer(b.getTotalAmount()),
            BigDecimalSerializerUtil.serializer(b.getSumTotalAmount())})
        .collect(Collectors.toList());

    final List<String[]> asks = depth.getAsks().stream()
        .map(b -> new String[] {BigDecimalSerializerUtil.serializer(b.getPrice()),
            BigDecimalSerializerUtil.serializer(b.getTotalAmount()),
            BigDecimalSerializerUtil.serializer(b.getSumTotalAmount())})
        .collect(Collectors.toList());

    if (bids.size() == 0 && asks.size() == 0) {
      return null;
    }

    return new HashMap<String, List<String[]>>() {
      private static final long serialVersionUID = 1989948770357110604L;

      {
        this.put("bids", bids);
        this.put("asks", asks);
      }
    };
  }

  @Override
  public Object replyMessage(final AskBean askBean, final Object data) {
    final String key = askBean.getContractCode().toUpperCase();
    final Depth oldDepth = this.cacheMap.get(key);
    if (oldDepth == null) {
      // 需要init
      return null;
    }
    final Depth newDepth = this.convert((JSONObject) data);

    final Depth depth = this.mergeDepthData(oldDepth, newDepth);
    this.cacheMap.put(key, newDepth);
    return this.regularResult(askBean, depth);
  }

  private Depth mergeDepthData(final Depth oldDepth, final Depth newDepth) {
    final List<DepthNode> bidList = this.mergeBidDepth(oldDepth.getBids(), newDepth.getBids());
    final List<DepthNode> askList = this.mergeAskDepth(oldDepth.getAsks(), newDepth.getAsks());
    final Depth depthData = new Depth();
    depthData.setBids(bidList);
    depthData.setAsks(askList);
    return depthData;
  }

  private List<DepthNode> mergeBidDepth(List<DepthNode> oldBidList, List<DepthNode> newBidList) {
    final List<DepthNode> bidList = new ArrayList<>();
    int o = 0, n = 0;
    while (n < newBidList.size() && o < oldBidList.size()) {
      final DepthNode depthInfo = new DepthNode();
      final DepthNode oldDepthInfo = oldBidList.get(o);
      final BigDecimal oldPrice = oldDepthInfo.getPrice();
      final DepthNode newDepthInfo = newBidList.get(n);
      final BigDecimal newPrice = newDepthInfo.getPrice();
      final int comparePriceRes = newPrice.compareTo(oldPrice);
      switch (comparePriceRes) {
        case -1:
          o++;
          depthInfo.setPrice(oldPrice);
          depthInfo.setTotalAmount(BigDecimal.ZERO);
          depthInfo.setSumTotalAmount(BigDecimal.ZERO);
          bidList.add(depthInfo);
          break;
        case 0:
          o++;
          n++;
          final BigDecimal oldAmount = oldDepthInfo.getTotalAmount();
          final BigDecimal newAmount = newDepthInfo.getTotalAmount();
          final BigDecimal oldSumAmount = oldDepthInfo.getSumTotalAmount();
          final BigDecimal newSumAmount = newDepthInfo.getSumTotalAmount();
          if (oldAmount.compareTo(newAmount) != 0 || oldSumAmount.compareTo(newSumAmount) != 0) {
            depthInfo.setPrice(newDepthInfo.getPrice());
            depthInfo.setTotalAmount(newDepthInfo.getTotalAmount());
            depthInfo.setSumTotalAmount(newDepthInfo.getSumTotalAmount());
            bidList.add(depthInfo);
          }
          break;
        case 1:
          n++;
          depthInfo.setPrice(newDepthInfo.getPrice());
          depthInfo.setTotalAmount(newDepthInfo.getTotalAmount());
          depthInfo.setSumTotalAmount(newDepthInfo.getSumTotalAmount());
          bidList.add(depthInfo);
          break;
        default:
          break;
      }
    }
    while (n < newBidList.size()) {
      bidList.add(newBidList.get(n++));
    }
    while (o < oldBidList.size()) {
      final DepthNode depthInfo = oldBidList.get(o++);
      depthInfo.setTotalAmount(BigDecimal.ZERO);
      depthInfo.setSumTotalAmount(BigDecimal.ZERO);
      bidList.add(depthInfo);
    }
    return bidList;
  }

  private List<DepthNode> mergeAskDepth(List<DepthNode> oldAskList, List<DepthNode> newAskList) {
    final List<DepthNode> askList = new ArrayList<>();
    if (CollectionUtils.isEmpty(oldAskList)) {
      oldAskList = new ArrayList<>();
    }
    if (CollectionUtils.isEmpty(newAskList)) {
      newAskList = new ArrayList<>();
    }
    int o = oldAskList.size() - 1;
    int n = newAskList.size() - 1;
    while (n >= 0 && o >= 0) {
      final DepthNode depthInfo = new DepthNode();
      final DepthNode oldDepthInfo = oldAskList.get(o);
      final BigDecimal oldPrice = oldDepthInfo.getPrice();
      final DepthNode newDepthInfo = newAskList.get(n);
      final BigDecimal newPrice = newDepthInfo.getPrice();

      final int comparePriceRes = newPrice.compareTo(oldPrice);
      switch (comparePriceRes) {
        case -1:
          o--;
          depthInfo.setPrice(oldDepthInfo.getPrice());
          depthInfo.setTotalAmount(BigDecimal.ZERO);
          depthInfo.setSumTotalAmount(BigDecimal.ZERO);
          askList.add(depthInfo);
          break;
        case 0:
          n--;
          o--;
          final BigDecimal oldAmount = oldDepthInfo.getTotalAmount();
          final BigDecimal newAmount = newDepthInfo.getTotalAmount();
          final BigDecimal oldSumAmount = oldDepthInfo.getSumTotalAmount();
          final BigDecimal newSumAmount = newDepthInfo.getSumTotalAmount();
          if (oldAmount.compareTo(newAmount) != 0 || oldSumAmount.compareTo(newSumAmount) != 0) {
            depthInfo.setPrice(newDepthInfo.getPrice());
            depthInfo.setTotalAmount(newDepthInfo.getTotalAmount());
            depthInfo.setSumTotalAmount(newDepthInfo.getSumTotalAmount());
            askList.add(depthInfo);
          }
          break;
        case 1:
          n--;
          depthInfo.setPrice(newDepthInfo.getPrice());
          depthInfo.setTotalAmount(newDepthInfo.getTotalAmount());
          depthInfo.setSumTotalAmount(newDepthInfo.getSumTotalAmount());
          askList.add(depthInfo);
          break;
        default:
          break;
      }
    }
    while (n >= 0) {
      askList.add(newAskList.get(n--));
    }
    while (o >= 0) {
      final DepthNode depthInfo = oldAskList.get(o--);
      depthInfo.setTotalAmount(BigDecimal.ZERO);
      depthInfo.setSumTotalAmount(BigDecimal.ZERO);
      askList.add(depthInfo);
    }
    return askList;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class Depth {
    transient Boolean init;
    transient List<DepthNode> bids;
    transient List<DepthNode> asks;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class DepthNode {
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    BigDecimal price;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    BigDecimal totalAmount;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    BigDecimal sumTotalAmount;
  }
}
