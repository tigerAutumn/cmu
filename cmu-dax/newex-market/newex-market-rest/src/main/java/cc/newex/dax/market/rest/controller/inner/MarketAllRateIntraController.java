package cc.newex.dax.market.rest.controller.inner;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.config.MarketProperties;
import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import cc.newex.dax.market.common.util.DataKeyUtils;
import cc.newex.dax.market.criteria.MarketAllRateExample;
import cc.newex.dax.market.domain.MarketAllRate;
import cc.newex.dax.market.rest.common.util.WebUtil;
import cc.newex.dax.market.rest.controller.base.BaseController;
import cc.newex.dax.market.service.IpCheckService;
import cc.newex.dax.market.service.MarketAllRateService;
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
import java.util.Date;

/**
 * 控制器类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@RestController
@Slf4j
@RequestMapping(value = "/inner/v1/market/data/rate")
public class MarketAllRateIntraController
        extends BaseController<MarketAllRateService, MarketAllRate, MarketAllRateExample, Long> {

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
        final String marketAllRate = request.getParameter("rate");
        final MarketAllRate marketRate = JSONObject.parseObject(marketAllRate, MarketAllRate.class);
        if (marketRate == null || StringUtils.isEmpty(key)) {
            log.error("MarketAllRateIntraController.receiveData marketRate or key is empty! marketRate: {}, key: {}",
                    JSONObject.toJSONString(marketRate), key);
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_PARAM_EMPTY);
        }
        if (!DataKeyUtils.validateKey(key, this.marketProperties.getDataKey())) {
            final String ip = WebUtil.getClientAddress(request);
            log.error("MarketAllRateIntraController.detail illegal user! IP: {}", ip);
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_AUTH_FAIL);
        }

        // 汇率名称不能为空；汇率不能为空，且必须是正数。
        if (StringUtils.isEmpty(marketRate.getRateName()) || marketRate.getRateParities() == null
                || marketRate.getRateParities().signum() <= 0) {
            log.error("MarketAllRateIntraController.receiveData data not valid! data: {}",
                    JSONObject.toJSONString(marketRate));
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_PARAM_VALIDATE_FAIL);
        }
        final BigDecimal avg = this.service.selectPairAcg2Week(marketRate.getRateName());
        marketRate.setCreateTime(new Date());
        marketRate.setPairAvg(avg);
        this.service.add(marketRate);
        this.service.putRateRedis(marketRate.getRateName());
        return ResultUtils.success();
    }


}