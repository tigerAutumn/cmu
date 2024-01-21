package cc.newex.dax.extra.rest.controller.outer.v1.common.currency;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.util.SimpleLocaleUtils;
import cc.newex.dax.extra.domain.tokenin.CurrencyLevelInfo;
import cc.newex.dax.extra.service.admin.currency.TokenInsightAdminService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 币种的评级信息 控制器类
 *
 * @author liutiejun
 * @date 2018-07-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/extra/cms/currency/level")
public class CurrencyLevelInfoController {

    @Autowired
    private TokenInsightAdminService tokenInsightAdminService;

    /**
     * 查询币种的评级信息
     *
     * @param code   币种简称
     * @return
     */
    @GetMapping(value = "/getLevelInfo")
    public ResponseResult getLevelInfo(@RequestParam("code") @NotBlank final String code, final HttpServletRequest request) {
        // 可选为zh-cn、en-us
        final String locale = SimpleLocaleUtils.getLocaleByDefault(request, Locale.US.toLanguageTag().toLowerCase());

        CurrencyLevelInfo currencyLevelInfo = this.tokenInsightAdminService.getLevelInfo(code, locale);

        if (currencyLevelInfo == null) {
            // 方便移动端处理
            currencyLevelInfo = new CurrencyLevelInfo();
        }

        return ResultUtils.success(currencyLevelInfo);
    }

}
