package cc.newex.dax.boss.web.controller.outer.v1.extra.currency;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.util.DateFormater;
import cc.newex.dax.boss.web.model.extra.currency.CurrencyProgressInfoExtraVO;
import cc.newex.dax.extra.client.ExtraCmsCurrencyAdminClient;
import cc.newex.dax.extra.dto.currency.CurrencyProgressInfoDTO;
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
 * @date 2018-08-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/boss/extra/currency/progress-info")
public class CurrencyProgressInfoController {

    @Autowired
    private ExtraCmsCurrencyAdminClient extraCmsCurrencyAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增CurrencyProgressInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_PROGRESS_INFO_ADD"})
    public ResponseResult add(@Valid final CurrencyProgressInfoExtraVO currencyProgressInfoExtraVO, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final CurrencyProgressInfoDTO currencyProgressInfoDTO = CurrencyProgressInfoDTO.builder()
                .code(currencyProgressInfoExtraVO.getCode())
                .locale(currencyProgressInfoExtraVO.getLocale())
                .publishDate(DateFormater.parse(currencyProgressInfoExtraVO.getPublishDate(), "yyyy-MM-dd"))
                .content(currencyProgressInfoExtraVO.getContent())
                .status(currencyProgressInfoExtraVO.getStatus())
                .publisherId(user.getId())
                .sort(currencyProgressInfoExtraVO.getSort())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsCurrencyAdminClient.saveCurrencyProgressInfo(currencyProgressInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改CurrencyProgressInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_PROGRESS_INFO_EDIT"})
    public ResponseResult edit(@Valid final CurrencyProgressInfoExtraVO currencyProgressInfoExtraVO,
                               @RequestParam("id") final Long id, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final CurrencyProgressInfoDTO currencyProgressInfoDTO = CurrencyProgressInfoDTO.builder()
                .id(id)
                .code(currencyProgressInfoExtraVO.getCode())
                .locale(currencyProgressInfoExtraVO.getLocale())
                .publishDate(DateFormater.parse(currencyProgressInfoExtraVO.getPublishDate(), "yyyy-MM-dd"))
                .content(currencyProgressInfoExtraVO.getContent())
                .status(currencyProgressInfoExtraVO.getStatus())
                .publisherId(user.getId())
                .sort(currencyProgressInfoExtraVO.getSort())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsCurrencyAdminClient.updateCurrencyProgressInfo(currencyProgressInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除CurrencyProgressInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_PROGRESS_INFO_REMOVE"})
    public ResponseResult remove(final Long id, final HttpServletRequest request) {
        final ResponseResult result = this.extraCmsCurrencyAdminClient.removeCurrencyProgressInfo(id);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取CurrencyProgressInfo列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_PROGRESS_INFO_VIEW"})
    public ResponseResult list(@RequestParam(value = "code", required = false) final String code,
                               @RequestParam(value = "locale", required = false) final String locale,
                               final DataGridPager<CurrencyProgressInfoDTO> pager) {

        final CurrencyProgressInfoDTO.CurrencyProgressInfoDTOBuilder builder = CurrencyProgressInfoDTO.builder();

        if (StringUtils.isNotBlank(code)) {
            builder.code(code);
        }

        if (StringUtils.isNotBlank(locale)) {
            builder.locale(locale);
        }

        final CurrencyProgressInfoDTO currencyProgressInfoDTO = builder.build();
        pager.setQueryParameter(currencyProgressInfoDTO);

        final ResponseResult result = this.extraCmsCurrencyAdminClient.listCurrencyProgressInfo(pager);

        return ResultUtil.getDataGridResult(result);
    }

}
