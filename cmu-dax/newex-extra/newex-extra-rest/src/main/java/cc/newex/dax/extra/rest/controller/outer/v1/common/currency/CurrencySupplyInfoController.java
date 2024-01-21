package cc.newex.dax.extra.rest.controller.outer.v1.common.currency;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.tokenin.CurrencySupplyInfo;
import cc.newex.dax.extra.service.admin.currency.TokenInsightAdminService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 币种的流通量、流通市值信息 控制器类
 *
 * @author liutiejun
 * @date 2018-07-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/extra/cms/currency/supply")
public class CurrencySupplyInfoController {

    @Autowired
    private TokenInsightAdminService tokenInsightAdminService;

    /**
     * 查询币种的流通量、流通率等信息
     *
     * @param code 币种简称
     * @return
     */
    @GetMapping(value = "/getSupplyInfo")
    public ResponseResult getSupplyInfo(@RequestParam("code") @NotBlank final String code) {
        final CurrencySupplyInfo currencySupplyInfo = this.tokenInsightAdminService.getSupplyInfo(code);

        return ResultUtils.success(currencySupplyInfo);
    }

}
