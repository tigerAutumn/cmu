package cc.newex.dax.push.service.impl.function.impl.spot;

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

public class SpotDepthFunctionServiceImpl implements FunctionService {
  private final Map<String, SpotDepth> cacheMap = new ConcurrentHashMap<>();
  private final PushRedisCache pushRedisCache;

  public SpotDepthFunctionServiceImpl(final PushRedisCache pushRedisCache) {
    this.pushRedisCache = pushRedisCache;
  }

  @Override
  public boolean hasInit() {
    return true;
  }

  @Override
  public Object initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    final String key = this.formatKey(askBean);
    SpotDepth spotDepth = this.cacheMap.get(key);
    if (spotDepth == null) {
      synchronized (this) {
        spotDepth = this.cacheMap.get(key);
        if (spotDepth == null) {
          final JSONObject depth =
              this.pushRedisCache.getSpotDepth(askBean.getBase(), askBean.getQuote());
          spotDepth = this.convert(depth);
          spotDepth.setInit(true);
          this.cacheMap.put(key, spotDepth);
        }
      }
    }
    return spotDepth == null ? null : this.regularResult(askBean, spotDepth);
  }

  private String formatKey(final AskBean askBean) {
    return String.format("%s_%s", askBean.getBase(), askBean.getQuote());
  }

  private SpotDepth convert(final JSONObject jsonObject) {
    SpotDepth spotDepth =
        jsonObject == null ? new SpotDepth() : jsonObject.toJavaObject(SpotDepth.class);
    if (spotDepth.getAsks() == null) {
      spotDepth.setAsks(new ArrayList<>());
    }
    if (spotDepth.getBids() == null) {
      spotDepth.setBids(new ArrayList<>());
    }
    return spotDepth;
  }

  private Object regularResult(final AskBean askBean, final SpotDepth spotDepth) {
    final List<String[]> bids =
        spotDepth.getBids().stream()
            .map(b -> new String[] {BigDecimalSerializerUtil.serializer(b.getPrice()),
                BigDecimalSerializerUtil.serializer(b.getTotalSize())})
            .collect(Collectors.toList());

    final List<String[]> asks =
        spotDepth.getAsks().stream()
            .map(b -> new String[] {BigDecimalSerializerUtil.serializer(b.getPrice()),
                BigDecimalSerializerUtil.serializer(b.getTotalSize())})
            .collect(Collectors.toList());

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
    final String key = this.formatKey(askBean);
    final SpotDepth oldSpotDepth = this.cacheMap.get(key);
    if (oldSpotDepth == null) {
      // 需要init
      return null;
    }
    final SpotDepth newSpotDepth = this.convert((JSONObject) data);

    final SpotDepth spotDepth = this.mergeDepthData(oldSpotDepth, newSpotDepth);
    this.cacheMap.put(key, newSpotDepth);
    return this.regularResult(askBean, spotDepth);
  }

  private SpotDepth mergeDepthData(final SpotDepth oldDepth, final SpotDepth newDepth) {
    final List<SpotDepthNode> spotBidList =
        this.mergeBidDepth(oldDepth.getBids(), newDepth.getBids());
    final List<SpotDepthNode> spotAskList =
        this.mergeAskDepth(oldDepth.getAsks(), newDepth.getAsks());
    final SpotDepth spotDepthData = new SpotDepth();
    spotDepthData.setAsks(spotAskList);
    spotDepthData.setBids(spotBidList);
    return spotDepthData;
  }

  private List<SpotDepthNode> mergeBidDepth(List<SpotDepthNode> oldBidList,
      List<SpotDepthNode> newBidList) {
    final List<SpotDepthNode> bidList = new ArrayList<>();
    int o = 0, n = 0;
    while (n < newBidList.size() && o < oldBidList.size()) {
      final SpotDepthNode spotDepthInfo = new SpotDepthNode();
      final SpotDepthNode oldSpotDepthInfo = oldBidList.get(o);
      final BigDecimal oldPrice = oldSpotDepthInfo.getPrice();
      final SpotDepthNode newSpotDepthInfo = newBidList.get(n);
      final BigDecimal newPrice = newSpotDepthInfo.getPrice();
      final int comparePriceRes = newPrice.compareTo(oldPrice);
      switch (comparePriceRes) {
        case -1:
          o++;
          spotDepthInfo.setPrice(oldPrice);
          spotDepthInfo.setTotalSize(BigDecimal.ZERO);
          bidList.add(spotDepthInfo);
          break;
        case 0:
          o++;
          n++;
          final BigDecimal oldAmount = oldSpotDepthInfo.getTotalSize();
          final BigDecimal newAmount = newSpotDepthInfo.getTotalSize();
          if (oldAmount.compareTo(newAmount) != 0) {
            spotDepthInfo.setPrice(newSpotDepthInfo.getPrice());
            spotDepthInfo.setTotalSize(newSpotDepthInfo.getTotalSize());
            bidList.add(spotDepthInfo);
          }
          break;
        case 1:
          n++;
          spotDepthInfo.setPrice(newSpotDepthInfo.getPrice());
          spotDepthInfo.setTotalSize(newSpotDepthInfo.getTotalSize());
          bidList.add(spotDepthInfo);
          break;
        default:
          break;
      }
    }
    while (n < newBidList.size()) {
      bidList.add(newBidList.get(n++));
    }
    while (o < oldBidList.size()) {
      final SpotDepthNode spotDepthInfo = oldBidList.get(o++);
      spotDepthInfo.setTotalSize(BigDecimal.ZERO);
      bidList.add(spotDepthInfo);
    }
    return bidList;
  }

  private List<SpotDepthNode> mergeAskDepth(List<SpotDepthNode> oldAskList,
      List<SpotDepthNode> newAskList) {
    final List<SpotDepthNode> askList = new ArrayList<>();
    if (CollectionUtils.isEmpty(oldAskList)) {
      oldAskList = new ArrayList<>();
    }
    if (CollectionUtils.isEmpty(newAskList)) {
      newAskList = new ArrayList<>();
    }
    int o = oldAskList.size() - 1;
    int n = newAskList.size() - 1;
    while (n >= 0 && o >= 0) {
      final SpotDepthNode spotDepthInfo = new SpotDepthNode();
      final SpotDepthNode oldSpotDepthInfo = oldAskList.get(o);
      final BigDecimal oldPrice = oldSpotDepthInfo.getPrice();
      final SpotDepthNode newSpotDepthInfo = newAskList.get(n);
      final BigDecimal newPrice = newSpotDepthInfo.getPrice();

      final int comparePriceRes = newPrice.compareTo(oldPrice);
      switch (comparePriceRes) {
        case -1:
          o--;
          spotDepthInfo.setPrice(oldSpotDepthInfo.getPrice());
          spotDepthInfo.setTotalSize(BigDecimal.ZERO);
          askList.add(spotDepthInfo);
          break;
        case 0:
          n--;
          o--;
          final BigDecimal oldAmount = oldSpotDepthInfo.getTotalSize();
          final BigDecimal newAmount = newSpotDepthInfo.getTotalSize();
          if (oldAmount.compareTo(newAmount) != 0) {
            spotDepthInfo.setPrice(newSpotDepthInfo.getPrice());
            spotDepthInfo.setTotalSize(newSpotDepthInfo.getTotalSize());
            askList.add(spotDepthInfo);
          }
          break;
        case 1:
          n--;
          spotDepthInfo.setPrice(newSpotDepthInfo.getPrice());
          spotDepthInfo.setTotalSize(newSpotDepthInfo.getTotalSize());
          askList.add(spotDepthInfo);
          break;
        default:
          break;
      }
    }
    while (n >= 0) {
      askList.add(newAskList.get(n--));
    }
    while (o >= 0) {
      final SpotDepthNode spotDepthInfo = oldAskList.get(o--);
      spotDepthInfo.setTotalSize(BigDecimal.ZERO);
      askList.add(spotDepthInfo);
    }
    return askList;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class SpotDepth {
    transient Boolean init;
    transient List<SpotDepthNode> bids;
    transient List<SpotDepthNode> asks;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class SpotDepthNode {
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    BigDecimal price;
    @JSONField(serializeUsing = BigDecimalSerializer.class)
    BigDecimal totalSize;
  }
}
