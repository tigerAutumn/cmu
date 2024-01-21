package cc.newex.dax.asset.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.criteria.ReconciliationConfExample;
import cc.newex.dax.asset.domain.BalanceSummary;
import cc.newex.dax.asset.domain.PageBossEntity;
import cc.newex.dax.asset.domain.ReconciliationConf;
import cc.newex.dax.asset.dto.BalanceSummaryResDto;
import cc.newex.dax.asset.service.BalanceSummaryService;
import cc.newex.dax.asset.service.ReconciliationConfService;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.exception.UnsupportedCurrency;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 2018/5/4
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/asset")
public class InnerBalanceSummaryController {

    @Autowired
    private BalanceSummaryService balanceSummaryService;
    @Autowired
    private ReconciliationConfService reconciliationConfService;

    @GetMapping(value = "/balanceSummary/threshold/edit")
    ResponseResult editThreshold(@RequestParam("currency") final Integer currency,
                                 @RequestParam("threshold") final BigDecimal threshold) {
        ResponseResult<PageBossEntity> result;
        try {
            ReconciliationConfExample example = new ReconciliationConfExample();
            example.createCriteria().andCurrencyEqualTo(currency);

            ReconciliationConf ReconciliationConf = new ReconciliationConf();
            ReconciliationConf.setTotalThreshold(threshold);
            int num = this.reconciliationConfService.editByExample(ReconciliationConf, example);
            Assert.isTrue(num > 0, "该币种不存在");
            result = ResultUtils.success();
        } catch (UnsupportedCurrency e) {
            InnerBalanceSummaryController.log.error("editThreshold error", e);
            return ResultUtils.failure("editThreshold error");
        } catch (Throwable e) {
            InnerBalanceSummaryController.log.error("editThreshold error", e);
            return ResultUtils.failure(e.getMessage());
        }

        return result;
    }

    @GetMapping(value = "/balanceSummary/get")
    ResponseResult getBalanceSummary(@RequestParam(value = "currency", required = false) String currency,
                                     @RequestParam(value = "timeNode", required = false) Date timeNode,
                                     @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        ResponseResult<PageBossEntity> result;
        try {
            PageBossEntity pageBossEntity = PageBossEntity.getPage(this.balanceSummaryService,
                    pageNum, pageSize, this.balanceSummaryService.getBalanceSummaryExample(currency, timeNode));

            List<ReconciliationConf> confList = this.reconciliationConfService.getAll();
            Map<Integer, BigDecimal> confMap = confList.stream()
                    .collect(Collectors.toMap(ReconciliationConf::getCurrency, ReconciliationConf::getTotalThreshold));

            List<BalanceSummary> balanceSummaryList = pageBossEntity.getRows();
            List<BalanceSummaryResDto> balanceSummaryResDtos = balanceSummaryList.parallelStream().map(
                    (balanceSummary) -> {
                        JSONObject bizBalanceJson = JSONObject.parseObject(balanceSummary.getBizBalance());
                        BigDecimal spotBalance = bizBalanceJson.getBigDecimal(BizEnum.SPOT.getName());
                        BigDecimal c2cBalance = bizBalanceJson.getBigDecimal(BizEnum.C2C.getName());
                        BigDecimal contractBalance = bizBalanceJson.getBigDecimal(BizEnum.CONTRACT.getName());

                        BalanceSummaryResDto deposit = BalanceSummaryResDto.builder()
                                .currency(balanceSummary.getCurrency())
                                .walletBalance(balanceSummary.getWalletBalance())
                                .spotBalance(spotBalance == null ? BigDecimal.ZERO : spotBalance)
                                .c2cBalance(c2cBalance == null ? BigDecimal.ZERO : c2cBalance)
                                .contractBalance(contractBalance == null ? BigDecimal.ZERO : contractBalance)
                                .depositUnconfirmed(balanceSummary.getDepositUnconfirmed())
                                .withdrawUnconfirmed(balanceSummary.getWithdrawUnconfirmed())
                                .difference(balanceSummary.getDifference())
                                .timeNode(balanceSummary.getCreateDate())
                                .threshold(confMap.get(balanceSummary.getCurrency()))
                                .build();
                        return deposit;
                    }).collect(Collectors.toList());
            pageBossEntity.setRows(balanceSummaryResDtos);
            result = ResultUtils.success(pageBossEntity);
        } catch (UnsupportedCurrency e) {
            InnerBalanceSummaryController.log.error("getBalanceSummary error", e);
            return ResultUtils.failure("getBalanceSummary error");
        } catch (Throwable e) {
            InnerBalanceSummaryController.log.error("getBalanceSummary error", e);
            return ResultUtils.failure(e.getMessage());
        }

        return result;
    }

}
