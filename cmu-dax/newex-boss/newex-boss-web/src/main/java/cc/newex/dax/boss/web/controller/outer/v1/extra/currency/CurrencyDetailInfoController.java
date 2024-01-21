package cc.newex.dax.boss.web.controller.outer.v1.extra.currency;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.extra.currency.CurrencyDetailInfoExtraVO;
import cc.newex.dax.extra.client.ExtraCmsCurrencyAdminClient;
import cc.newex.dax.extra.dto.currency.CurrencyDetailInfoDTO;
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
 * 币种wiki管理 - 币种详细介绍
 *
 * @author liutiejun
 * @date 2018-07-11
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/boss/extra/currency/detail")
public class CurrencyDetailInfoController {

    @Autowired
    private ExtraCmsCurrencyAdminClient extraCmsCurrencyAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增CurrencyDetailInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_DETAIL_INFO_ADD"})
    public ResponseResult add(@Valid final CurrencyDetailInfoExtraVO currencyDetailInfoExtraVO, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final CurrencyDetailInfoDTO currencyDetailInfoDTO = CurrencyDetailInfoDTO.builder()
                .code(currencyDetailInfoExtraVO.getCode())
                .locale(currencyDetailInfoExtraVO.getLocale())
                .concept(currencyDetailInfoExtraVO.getConcept())
                .whitePaper(currencyDetailInfoExtraVO.getWhitePaper())
                .introduction(currencyDetailInfoExtraVO.getIntroduction())
                .telegram(currencyDetailInfoExtraVO.getTelegram())
                .facebook(currencyDetailInfoExtraVO.getFacebook())
                .twitter(currencyDetailInfoExtraVO.getTwitter())
                .weibo(currencyDetailInfoExtraVO.getWeibo())
                .status(currencyDetailInfoExtraVO.getStatus())
                .coinMarketCapUrl(currencyDetailInfoExtraVO.getCoinMarketCapUrl())
                .publisherId(user.getId())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsCurrencyAdminClient.saveCurrencyDetailInfo(currencyDetailInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改CurrencyDetailInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_DETAIL_INFO_EDIT"})
    public ResponseResult edit(@Valid final CurrencyDetailInfoExtraVO currencyDetailInfoExtraVO,
                               @RequestParam("id") final Long id, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final CurrencyDetailInfoDTO currencyDetailInfoDTO = CurrencyDetailInfoDTO.builder()
                .id(id)
                .code(currencyDetailInfoExtraVO.getCode())
                .locale(currencyDetailInfoExtraVO.getLocale())
                .concept(currencyDetailInfoExtraVO.getConcept())
                .whitePaper(currencyDetailInfoExtraVO.getWhitePaper())
                .introduction(currencyDetailInfoExtraVO.getIntroduction())
                .telegram(currencyDetailInfoExtraVO.getTelegram())
                .facebook(currencyDetailInfoExtraVO.getFacebook())
                .twitter(currencyDetailInfoExtraVO.getTwitter())
                .weibo(currencyDetailInfoExtraVO.getWeibo())
                .status(currencyDetailInfoExtraVO.getStatus())
                .publisherId(user.getId())
                .updatedDate(new Date())
                .coinMarketCapUrl(currencyDetailInfoExtraVO.getCoinMarketCapUrl())
                .build();

        ResponseResult result = null;

        if (id == null) {
            result = this.extraCmsCurrencyAdminClient.saveCurrencyDetailInfo(currencyDetailInfoDTO);
        } else {
            result = this.extraCmsCurrencyAdminClient.updateCurrencyDetailInfo(currencyDetailInfoDTO);
        }

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取CurrencyDetailInfo列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_DETAIL_INFO_VIEW"})
    public ResponseResult list(@RequestParam(value = "code", required = false) final String code,
                               @RequestParam(value = "locale", required = false) final String locale,
                               final DataGridPager<CurrencyDetailInfoDTO> pager) {

        final CurrencyDetailInfoDTO.CurrencyDetailInfoDTOBuilder builder = CurrencyDetailInfoDTO.builder();

        if (StringUtils.isNotBlank(code)) {
            builder.code(code);
        }

        if (StringUtils.isNotBlank(locale)) {
            builder.locale(locale);
        }

        final CurrencyDetailInfoDTO currencyDetailInfoDTO = builder.build();
        pager.setQueryParameter(currencyDetailInfoDTO);

        final ResponseResult responseResult = this.extraCmsCurrencyAdminClient.listCurrencyDetailInfo(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

    @GetMapping(value = "/getByCode")
    @OpLog(name = "获取币种详细介绍")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_DETAIL_INFO_VIEW"})
    public ResponseResult getByCode(@RequestParam(value = "code", required = false) final String code,
                                    @RequestParam(value = "locale", required = false) final String locale) {

        final ResponseResult responseResult = this.extraCmsCurrencyAdminClient.getCurrencyDetailInfoByCode(code, locale);

        return ResultUtil.getCheckedResponseResult(responseResult);
    }

}
