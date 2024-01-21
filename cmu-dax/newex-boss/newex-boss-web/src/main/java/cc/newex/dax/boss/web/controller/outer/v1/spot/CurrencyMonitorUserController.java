package cc.newex.dax.boss.web.controller.outer.v1.spot;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.spot.CurrencyMonitorUserVO;
import cc.newex.dax.spot.client.CurrencyMonitorAdminClient;
import cc.newex.dax.spot.dto.ccex.currency.CurrencyMonitorUserDTO;
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
 * @date 2019-01-15
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/spot/currency/monitor-user")
public class CurrencyMonitorUserController {

    @Autowired
    private CurrencyMonitorAdminClient currencyMonitorAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增CurrencyMonitorUser")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_MONITOR_USER_ADD"})
    public ResponseResult add(@Valid final CurrencyMonitorUserVO currencyMonitorUserVO, final HttpServletRequest request) {
        final CurrencyMonitorUserDTO currencyMonitorUserDTO = CurrencyMonitorUserDTO.builder()
                .userId(currencyMonitorUserVO.getUserId())
                .remark(currencyMonitorUserVO.getRemark())
                .status(currencyMonitorUserVO.getStatus())
                .brokerId(currencyMonitorUserVO.getBrokerId())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.currencyMonitorAdminClient.saveCurrencyMonitorUser(currencyMonitorUserDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改CurrencyMonitorUser")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_MONITOR_USER_EDIT"})
    public ResponseResult edit(@Valid final CurrencyMonitorUserVO currencyMonitorUserVO, final Long id, final HttpServletRequest request) {
        final CurrencyMonitorUserDTO currencyMonitorUserDTO = CurrencyMonitorUserDTO.builder()
                .id(id)
                .userId(currencyMonitorUserVO.getUserId())
                .remark(currencyMonitorUserVO.getRemark())
                .status(currencyMonitorUserVO.getStatus())
                .brokerId(currencyMonitorUserVO.getBrokerId())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.currencyMonitorAdminClient.updateCurrencyMonitorUser(currencyMonitorUserDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取CurrencyMonitorUser列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_MONITOR_USER_VIEW"})
    public ResponseResult list(@RequestParam(value = "status", required = false) final Integer status,
                               @RequestParam(value = "brokerId", required = false) final Integer brokerId,
                               final DataGridPager<CurrencyMonitorUserDTO> pager) {
        final CurrencyMonitorUserDTO.CurrencyMonitorUserDTOBuilder builder = CurrencyMonitorUserDTO.builder();

        if (status != null) {
            builder.status(status);
        }

        if (brokerId != null) {
            builder.brokerId(brokerId);
        }

        final CurrencyMonitorUserDTO currencyMonitorUserDTO = builder.build();
        pager.setQueryParameter(currencyMonitorUserDTO);

        final ResponseResult responseResult = this.currencyMonitorAdminClient.listCurrencyMonitorUser(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除CurrencyMonitorUser")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_MONITOR_USER_REMOVE"})
    public ResponseResult remove(@RequestParam("id") final Long id) {
        final ResponseResult result = this.currencyMonitorAdminClient.removeCurrencyMonitorUser(id);

        return ResultUtil.getCheckedResponseResult(result);
    }

}
