package cc.newex.dax.boss.web.controller.outer.v1.extra.currency;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.util.DateFormater;
import cc.newex.dax.boss.web.model.extra.currency.CurrencyTrendInfoExtraVO;
import cc.newex.dax.extra.client.ExtraCmsCurrencyAdminClient;
import cc.newex.dax.extra.dto.currency.CurrencyTrendInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2018-08-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/boss/extra/currency/trend-info")
public class CurrencyTrendInfoController {

    @Autowired
    private ExtraCmsCurrencyAdminClient extraCmsCurrencyAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增CurrencyTrendInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_TREND_INFO_ADD"})
    public ResponseResult add(@Valid final CurrencyTrendInfoExtraVO currencyTrendInfoExtraVO, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final CurrencyTrendInfoDTO currencyTrendInfoDTO = CurrencyTrendInfoDTO.builder()
                .code(currencyTrendInfoExtraVO.getCode())
                .locale(currencyTrendInfoExtraVO.getLocale())
                .title(currencyTrendInfoExtraVO.getTitle())
                .content(StringUtils.defaultString(currencyTrendInfoExtraVO.getContent(), ""))
                .link(currencyTrendInfoExtraVO.getLink())
                .publishDate(DateFormater.parse(currencyTrendInfoExtraVO.getPublishDate(), "yyyy-MM-dd"))
                .status(currencyTrendInfoExtraVO.getStatus())
                .publisherId(user.getId())
                .sort(currencyTrendInfoExtraVO.getSort())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsCurrencyAdminClient.saveCurrencyTrendInfo(currencyTrendInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改CurrencyTrendInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_TREND_INFO_EDIT"})
    public ResponseResult edit(@Valid final CurrencyTrendInfoExtraVO currencyTrendInfoExtraVO,
                               @RequestParam("id") final Long id, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final CurrencyTrendInfoDTO currencyTrendInfoDTO = CurrencyTrendInfoDTO.builder()
                .id(id)
                .code(currencyTrendInfoExtraVO.getCode())
                .locale(currencyTrendInfoExtraVO.getLocale())
                .title(currencyTrendInfoExtraVO.getTitle())
                .content(StringUtils.defaultString(currencyTrendInfoExtraVO.getContent(), ""))
                .link(currencyTrendInfoExtraVO.getLink())
                .publishDate(DateFormater.parse(currencyTrendInfoExtraVO.getPublishDate(), "yyyy-MM-dd"))
                .status(currencyTrendInfoExtraVO.getStatus())
                .publisherId(user.getId())
                .sort(currencyTrendInfoExtraVO.getSort())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsCurrencyAdminClient.updateCurrencyTrendInfo(currencyTrendInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除CurrencyTrendInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_TREND_INFO_REMOVE"})
    public ResponseResult remove(final Long id, final HttpServletRequest request) {
        final ResponseResult result = this.extraCmsCurrencyAdminClient.removeCurrencyTrendInfo(id);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取CurrencyTrendInfo列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_TREND_INFO_VIEW"})
    public ResponseResult list(@RequestParam(value = "code", required = false) final String code,
                               @RequestParam(value = "locale", required = false) final String locale,
                               final DataGridPager<CurrencyTrendInfoDTO> pager) {

        final CurrencyTrendInfoDTO.CurrencyTrendInfoDTOBuilder builder = CurrencyTrendInfoDTO.builder();

        if (StringUtils.isNotBlank(code)) {
            builder.code(code);
        }

        if (StringUtils.isNotBlank(locale)) {
            builder.locale(locale);
        }

        final CurrencyTrendInfoDTO currencyTrendInfoDTO = builder.build();
        pager.setQueryParameter(currencyTrendInfoDTO);

        final ResponseResult result = this.extraCmsCurrencyAdminClient.listCurrencyTrendInfo(pager);

        return ResultUtil.getDataGridResult(result);
    }

}
