package cc.newex.dax.boss.web.controller.outer.v1.spot;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.util.DateFormater;
import cc.newex.dax.boss.web.model.spot.BreakerRecordVO;
import cc.newex.dax.boss.web.model.spot.BreakerStrategyVO;
import cc.newex.dax.spot.client.SpotBreakerClient;
import cc.newex.dax.spot.dto.ccex.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 熔断管理
 *
 * @author liutiejun
 * @date 2018-08-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/boss/spot/breaker")
public class BreakerController {

    @Autowired
    private SpotBreakerClient spotBreakerClient;

    @RequestMapping(value = "/addStrategy")
    @OpLog(name = "新增熔断策略")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_STRATEGY_ADD"})
    public ResponseResult addStrategy(@Valid final BreakerStrategyVO breakerStrategyVO, final HttpServletRequest request) {
        final BreakerStrategyDTO breakerStrategyDTO = BreakerStrategyDTO.builder()
                .breakerDirectionEnum(BreakerDirectionEnum.getByType(breakerStrategyVO.getBreakerDirection()))
                .breakerPresetEnum(BreakerPresetEnum.getByCode(breakerStrategyVO.getBreakerPreset()))
                .monitorTime(breakerStrategyVO.getMonitorTime() * 1000)
                .triggerRatio(breakerStrategyVO.getTriggerRatio())
                .pauseTime(breakerStrategyVO.getPauseTime() * 1000)
                .intervalTime(breakerStrategyVO.getIntervalTime() * 1000)
                .remark(breakerStrategyVO.getRemark())
                .createDate(new Date())
                .modifyDate(new Date())
                .build();

        final ResponseResult result = this.spotBreakerClient.addStrategy(breakerStrategyDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/editStrategy")
    @OpLog(name = "修改熔断策略")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_STRATEGY_EDIT"})
    public ResponseResult editStrategy(@Valid final BreakerStrategyVO breakerStrategyVO,
                                       @RequestParam("id") @NotNull final Long id, final HttpServletRequest request) {
        final BreakerStrategyDTO breakerStrategyDTO = BreakerStrategyDTO.builder()
                .id(id)
                .breakerDirectionEnum(BreakerDirectionEnum.getByType(breakerStrategyVO.getBreakerDirection()))
                .breakerPresetEnum(BreakerPresetEnum.getByCode(breakerStrategyVO.getBreakerPreset()))
                .monitorTime(breakerStrategyVO.getMonitorTime() * 1000)
                .triggerRatio(breakerStrategyVO.getTriggerRatio())
                .pauseTime(breakerStrategyVO.getPauseTime() * 1000)
                .intervalTime(breakerStrategyVO.getIntervalTime() * 1000)
                .remark(breakerStrategyVO.getRemark())
                .modifyDate(new Date())
                .build();

        final ResponseResult result = this.spotBreakerClient.updateStrategy(breakerStrategyDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/removeStrategy")
    @OpLog(name = "删除熔断策略")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_STRATEGY_REMOVE"})
    public ResponseResult removeStrategy(final Long id, final HttpServletRequest request) {
        final ResponseResult result = this.spotBreakerClient.deleteStrategy(id);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/listStrategy")
    @OpLog(name = "分页获取熔断策略列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_STRATEGY_VIEW"})
    public ResponseResult listStrategy(@RequestParam(value = "id", required = false) final Long id,
                                       @RequestParam(value = "preset", required = false) final Byte preset,
                                       @RequestParam(value = "direction", required = false) final Integer direction,
                                       final DataGridPager<BreakerStrategyDTO> pager) {

        final ResponseResult<List<BreakerStrategyDTO>> result = this.spotBreakerClient.strategyList(id, preset, direction);

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final List<BreakerStrategyDTO> rows = result.getData();
        final Long total = Long.valueOf(CollectionUtils.size(rows));

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total, rows);

        return ResultUtils.success(dataGridPagerResult);
    }

    @GetMapping(value = "/listAllStrategy")
    @OpLog(name = "获取所有的熔断策略列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_STRATEGY_VIEW"})
    public ResponseResult listAllStrategy() {
        final ResponseResult<List<BreakerStrategyDTO>> result = this.spotBreakerClient.strategyList(null, null, null);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/addRecord")
    @OpLog(name = "新增熔断任务")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_RECORD_ADD"})
    public ResponseResult addRecord(@Valid final BreakerRecordVO breakerRecordVO, final HttpServletRequest request) {
        final BreakerRecordDTO breakerRecordDTO = BreakerRecordDTO.builder()
                .productId(breakerRecordVO.getProductId())
                .strategyId(breakerRecordVO.getStrategyId())
                .startDate(DateFormater.parse(breakerRecordVO.getStartDate()))
                .breakerDate(DateFormater.parse(breakerRecordVO.getBreakerDate()))
                .createDate(new Date())
                .modifyDate(new Date())
                .build();

        final ResponseResult result = this.spotBreakerClient.addRecord(breakerRecordDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/editRecord")
    @OpLog(name = "修改熔断任务")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_RECORD_EDIT"})
    public ResponseResult editRecord(@Valid final BreakerRecordVO breakerRecordVO,
                                     @RequestParam("id") @NotNull final Long id, final HttpServletRequest request) {
        return ResultUtils.success();
    }

    @RequestMapping(value = "/removeRecord")
    @OpLog(name = "删除熔断任务")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_RECORD_REMOVE"})
    public ResponseResult removeRecord(final Long id, final HttpServletRequest request) {
        final ResponseResult result = this.spotBreakerClient.deleteRecord(id);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/listRecord")
    @OpLog(name = "分页获取熔断任务列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_RECORD_VIEW"})
    public ResponseResult listRecord(@RequestParam(value = "id", required = false) final Long id,
                                     @RequestParam(value = "productId", required = false) final Long productId,
                                     @RequestParam(value = "strategyId", required = false) final Long strategyId,
                                     final DataGridPager<BreakerRecordDTO> pager) {
        final ResponseResult<List<BreakerRecordDTO>> result = this.spotBreakerClient.recordList(id, productId, strategyId);

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final List<BreakerRecordDTO> rows = result.getData();
        final Long total = Long.valueOf(CollectionUtils.size(rows));

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total, rows);

        return ResultUtils.success(dataGridPagerResult);
    }

    @GetMapping(value = "/listCurrency")
    @OpLog(name = "分页获取监控币对列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_CURRENCY_VIEW"})
    public ResponseResult listCurrency(@RequestParam(value = "productId", required = false) final Long productId,
                                       @RequestParam(value = "status", required = false) final Integer status,
                                       final DataGridPager<BreakerRecordDTO> pager) {
        final ResponseResult<List<BreakerCurrencyDTO>> result = this.spotBreakerClient.currencyList(productId, status);

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final List<BreakerCurrencyDTO> rows = result.getData();
        final Long total = Long.valueOf(CollectionUtils.size(rows));

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total, rows);

        return ResultUtils.success(dataGridPagerResult);
    }

    @GetMapping(value = "/listAllCurrency")
    @OpLog(name = "获取所有的监控币对")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_CURRENCY_VIEW"})
    public ResponseResult listAllCurrency() {
        final ResponseResult<List<BreakerCurrencyDTO>> result = this.spotBreakerClient.currencyList(null, null);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/listHistory")
    @OpLog(name = "分页获取监控任务历史记录列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_CURRENCY_VIEW"})
    public ResponseResult listHistory(@RequestParam(value = "productId", required = false) final Long productId,
                                      final DataGridPager<BreakerRecordDTO> pager) {
        final ResponseResult<List<BreakerHistoryDTO>> result = this.spotBreakerClient.historyList(productId);

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final List<BreakerHistoryDTO> rows = result.getData();
        final Long total = Long.valueOf(CollectionUtils.size(rows));

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(total, rows);

        return ResultUtils.success(dataGridPagerResult);
    }

    @PostMapping(value = "/breaker")
    @OpLog(name = "手动熔断")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_AUTO"})
    public ResponseResult breaker(final Long recordId) {
        final ResponseResult result = this.spotBreakerClient.breaker(recordId);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @PostMapping(value = "/recovery")
    @OpLog(name = "手动恢复")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BREAKER_RECOVERY"})
    public ResponseResult recovery(final Long recordId) {
        final ResponseResult result = this.spotBreakerClient.recovery(recordId);

        return ResultUtil.getCheckedResponseResult(result);
    }

}
