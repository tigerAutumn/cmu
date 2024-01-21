package cc.newex.dax.push.service.impl.function.impl.indexes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.market.dto.result.TickerDto;
import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.client.FeignMarketServiceClient;
import cc.newex.dax.push.service.impl.function.FunctionService;

/**
 * 指数行情
 *
 * @author xionghui
 * @date 2018/09/18
 */
public class IndexesTickerFunctionServiceImpl implements FunctionService {
  private final Map<String, JSONObject> indexTickerDataCacheMap = new ConcurrentHashMap<>();
  private final FeignMarketServiceClient marketServiceClient;

  public IndexesTickerFunctionServiceImpl(FeignMarketServiceClient marketServiceClient) {
    this.marketServiceClient = marketServiceClient;
  }

  @Override
  public boolean hasInit() {
    return true;
  }

  @Override
  public Object initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    JSONObject data = this.indexTickerDataCacheMap.get(askBean.getBase());
    if (data == null) {
      synchronized (this) {
        data = this.indexTickerDataCacheMap.get(askBean.getBase());
        if (data == null) {
          data = this.getIndexTicker(askBean.getBase());
          if (data != null) {
            this.indexTickerDataCacheMap.put(askBean.getBase(), data);
          }
        }
      }
    }
    return data == null ? new JSONObject() : data;
  }

  private JSONObject getIndexTicker(String currencyCode) {
    ResponseResult<TickerDto> responseResult = this.marketServiceClient.ticker(currencyCode);
    if (responseResult != null && responseResult.getCode() == 0) {
      JSONObject result = JSON.parseObject(JSON.toJSONString(responseResult.getData()));
      return result;
    }
    return null;
  }

  @Override
  public Object replyMessage(AskBean askBean, Object data) {
    JSONObject dataJson = (JSONObject) data;
    if (dataJson != null) {
      this.indexTickerDataCacheMap.put(askBean.getBase(), dataJson);
    }
    return dataJson;
  }
}
