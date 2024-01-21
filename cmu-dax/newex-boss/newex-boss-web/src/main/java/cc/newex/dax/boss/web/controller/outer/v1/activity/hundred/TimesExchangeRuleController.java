package cc.newex.dax.boss.web.controller.outer.v1.activity.hundred;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.activity.client.celebration.WheelAdminClient;
import cc.newex.dax.activity.dto.ccex.celebration.TimesExchangeRuleDTO;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.activity.hundred.TimesExchangeRuleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2019-01-03
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/activity/wheel/times/exchange-rule")
public class TimesExchangeRuleController {

    @Autowired
    private WheelAdminClient wheelAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增TimesExchangeRule")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TIMES_EXCHANGE_RULE_ADD"})
    public ResponseResult add(@Valid final TimesExchangeRuleVO timesExchangeRuleVO, final HttpServletRequest request) {
        final TimesExchangeRuleDTO timesExchangeRuleDTO = TimesExchangeRuleDTO.builder()
                .totalTimes(timesExchangeRuleVO.getTotalTimes())
                .currencyId(timesExchangeRuleVO.getCurrencyId())
                .amount(timesExchangeRuleVO.getAmount())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.wheelAdminClient.saveTimesExchangeRule(timesExchangeRuleDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改TimesExchangeRule")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TIMES_EXCHANGE_RULE_EDIT"})
    public ResponseResult edit(@Valid final TimesExchangeRuleVO timesExchangeRuleVO, final Long id, final HttpServletRequest request) {
        final TimesExchangeRuleDTO timesExchangeRuleDTO = TimesExchangeRuleDTO.builder()
                .id(id)
                .totalTimes(timesExchangeRuleVO.getTotalTimes())
                .currencyId(timesExchangeRuleVO.getCurrencyId())
                .amount(timesExchangeRuleVO.getAmount())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.wheelAdminClient.updateTimesExchangeRule(timesExchangeRuleDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取TimesExchangeRule列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TIMES_EXCHANGE_RULE_VIEW"})
    public ResponseResult list(final DataGridPager<TimesExchangeRuleDTO> pager) {
        final TimesExchangeRuleDTO.TimesExchangeRuleDTOBuilder builder = TimesExchangeRuleDTO.builder();

        final TimesExchangeRuleDTO timesExchangeRuleDTO = builder.build();

        pager.setQueryParameter(timesExchangeRuleDTO);

        final ResponseResult responseResult = this.wheelAdminClient.listTimesExchangeRule(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除TimesExchangeRule")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_TIMES_EXCHANGE_RULE_REMOVE"})
    public ResponseResult remove(@RequestParam("id") final Long id) {
        final ResponseResult result = this.wheelAdminClient.removeTimesExchangeRule(id);

        return ResultUtil.getCheckedResponseResult(result);
    }

}
