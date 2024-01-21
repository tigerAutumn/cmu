package cc.newex.dax.market.rest.controller.inner;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.config.MarketProperties;
import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import cc.newex.dax.market.common.util.DataKeyUtils;
import cc.newex.dax.market.criteria.LatestTickerExample;
import cc.newex.dax.market.domain.LatestTicker;
import cc.newex.dax.market.rest.common.util.WebUtil;
import cc.newex.dax.market.rest.controller.base.BaseController;
import cc.newex.dax.market.service.IpCheckService;
import cc.newex.dax.market.service.LatestTickerService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 用于内部处理。
 *
 * @author newex-team
 */
@Slf4j
@RestController
@RequestMapping(value = "/inner/v1/market/data/ticker")
public class TickerDataIntraController
        extends BaseController<LatestTickerService, LatestTicker, LatestTickerExample, Long> {

    @Autowired
    private MarketProperties marketProperties;
    @Autowired
    private IpCheckService ipCheckService;

    @PostMapping(value = "/receive")
    public ResponseResult receiveData(@RequestParam(value = "key", required = false) final String key,
                                      final HttpServletRequest request) {
        if (!this.ipCheckService.IPWhiteCheck(request)) {
            log.error("ip 错误 {}", IpUtil.getRealIPAddress(request));
            return ResultUtils.failure(BizErrorCodeEnum.IP_ERROR);
        }
        final String tickers = request.getParameter("ticker");
        final JSONObject jsonObject = JSONObject.parseObject(tickers);
        final LatestTicker ticker = jsonObject.toJavaObject(LatestTicker.class);
        if (ticker == null || StringUtils.isEmpty(key)) {
            log.error("TickerDataIntraController.receiveData data or sign is empty!");
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_PARAM_EMPTY);
        }

        if (!DataKeyUtils.validateKey(key, this.marketProperties.getDataKey())) {
            final String ip = WebUtil.getClientAddress(request);
            log.error("TickerDataIntraController.detail illegal user! IP: {}", ip);
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_AUTH_FAIL);
        }

        // 参数非空校验。数据校验先不添加。
        if (ticker.getLast() == null || ticker.getHigh() == null || ticker.getLow() == null ||
                ticker.getBuy() == null || ticker.getSell() == null || ticker.getVolume() == null
                || ticker.getMarketFrom() == null) {
            log.error("TickerDataIntraController.receiveData data not valid! data: {}",
                    JSONObject.toJSONString(ticker));
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_PARAM_VALIDATE_FAIL);
        }

        if (ticker.getChange24() == null) {
            ticker.setChange24(BigDecimal.ZERO);
        }

        return this.service.updateTickerByMarketFrom(ticker, ticker.getMarketFrom());
    }

}
