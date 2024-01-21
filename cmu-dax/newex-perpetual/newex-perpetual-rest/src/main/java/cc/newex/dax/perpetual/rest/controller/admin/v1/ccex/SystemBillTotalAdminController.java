package cc.newex.dax.perpetual.rest.controller.admin.v1.ccex;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.dto.SystemBillTotalDTO;
import cc.newex.dax.perpetual.service.SystemBillTotalService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 对账接口
 *
 * @author xionghui
 * @date 2018 /11/16
 */
@Slf4j
@RestController
@RequestMapping("/admin/v1/perpetual/systembilltotal")
public class SystemBillTotalAdminController {

    private final SystemBillTotalService systemBillTotalService;

    private ModelMapper modelMapper = new ModelMapper();

    /**
     * Instantiates a new System bill total admin controller.
     *
     * @param systemBillTotalService the system bill total service
     */
    @Autowired
    public SystemBillTotalAdminController(SystemBillTotalService systemBillTotalService) {
        this.systemBillTotalService = systemBillTotalService;
    }

    /**
     * Query system bill total response result.
     *
     * @param currencyCode    the currency code
     * @param startDateMillis the start date millis
     * @param endDateMillis   the end date millis
     * @return the response result
     */
    @GetMapping(value = "/querySystemBillTotal")
    public ResponseResult<List<SystemBillTotalDTO>> querySystemBillTotal(
            @RequestParam(value = "currencyCode", required = false) final String currencyCode,
            @RequestParam(value = "startDateMillis", required = false) final Long startDateMillis,
            @RequestParam(value = "endDateMillis", required = false) final Long endDateMillis) {

        return ResultUtils.success(this.systemBillTotalService.listByCondition(currencyCode, startDateMillis, endDateMillis));
    }
}
