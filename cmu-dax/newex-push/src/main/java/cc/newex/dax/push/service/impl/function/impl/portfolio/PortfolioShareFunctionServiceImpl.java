package cc.newex.dax.push.service.impl.function.impl.portfolio;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.portfolio.dto.PortfolioConfigDTO;
import cc.newex.dax.push.bean.AskBean;
import cc.newex.dax.push.bean.PortfolioShareData;
import cc.newex.dax.push.bean.PortfolioShareRequest;
import cc.newex.dax.push.bean.WebSocketBean;
import cc.newex.dax.push.cache.PushRedisCache;
import cc.newex.dax.push.client.FeignPortfolioConfigClient;
import cc.newex.dax.push.service.impl.function.FunctionService;
import lombok.extern.slf4j.Slf4j;

/**
 * 组合的配置：比如剩余的组合份数，购买信息等
 *
 * @author xionghui
 * @date 2018/09/20
 */
@Slf4j
public class PortfolioShareFunctionServiceImpl implements FunctionService {
  private final FeignPortfolioConfigClient portfolioConfigClient;
  private final PushRedisCache pushRedisCache;

  private final Map<PortfolioShareRequest, PortfolioShareData> portfolioShareCacheMap =
      new ConcurrentHashMap<>();

  public PortfolioShareFunctionServiceImpl(FeignPortfolioConfigClient portfolioConfigClient,
      PushRedisCache pushRedisCache) {
    this.portfolioConfigClient = portfolioConfigClient;
    this.pushRedisCache = pushRedisCache;
  }

  @Override
  public boolean hasInit() {
    return true;
  }

  @Override
  public Object initReply(WebSocketBean webSocketBean, AskBean askBean, String params) {
    final PortfolioShareRequest request =
        PortfolioShareRequest.builder().base(askBean.getBase()).quote(askBean.getQuote()).build();
    PortfolioShareData shareData = this.portfolioShareCacheMap.get(request);
    if (shareData == null) {
      synchronized (this) {
        // double check
        shareData = this.portfolioShareCacheMap.get(request);
        if (shareData == null) {
          shareData = this.getPortfolioShare(request);
          if (!ObjectUtils.isEmpty(shareData)) {
            this.portfolioShareCacheMap.put(request, shareData);
          }
        }
      }
    }
    return shareData;
  }

  private PortfolioShareData getPortfolioShare(final PortfolioShareRequest request) {
    final ResponseResult<List<PortfolioConfigDTO>> responseResult =
        this.portfolioConfigClient.portfolioConfigList();
    log.info("PortfolioShareFunctionServiceImpl getPortfolioShare responseResult: {}",
        responseResult == null ? null
            : responseResult.getCode() + ", " + responseResult.getMsg() + ", "
                + responseResult.getData());
    if (responseResult != null && responseResult.getCode() == 0) {
      final List<PortfolioConfigDTO> portfolioConfigDTOData = responseResult.getData();
      for (final PortfolioConfigDTO dto : portfolioConfigDTOData) {
        if (request.getBase().equalsIgnoreCase(dto.getName())
            && request.getQuote().equalsIgnoreCase((dto.getQuoteCurrency()))) {
          JSONObject jsonObject = this.pushRedisCache.getCurrency(request.getBase());
          if (jsonObject != null) {
            return PortfolioShareData.builder().baseId(jsonObject.getInteger(request.getBase()))
                .base(request.getBase()).quoteId(jsonObject.getInteger(request.getQuote()))
                .quote(request.getQuote()).current(dto.getInitAmount())
                .remaining(dto.getRemainAmount()).hold(dto.getHoldAmount()).build();
          }
        }
      }
    }
    return null;
  }

  @Override
  public Object replyMessage(final AskBean askBean, final Object data) {
    final JSONObject dataJson = (JSONObject) data;
    this.updateShareCacheMap(askBean, dataJson);
    return JSON.parseObject(dataJson.toJSONString(), JSON.class);
  }

  /**
   * share数据变化,更新map缓存数据
   *
   * @param askBean
   * @param dataJson
   */
  private void updateShareCacheMap(final AskBean askBean, final JSONObject dataJson) {
    final PortfolioShareRequest request =
        PortfolioShareRequest.builder().base(askBean.getBase()).quote(askBean.getQuote()).build();
    final PortfolioShareData data = dataJson.toJavaObject(PortfolioShareData.class);
    this.portfolioShareCacheMap.put(request, data);
  }
}
