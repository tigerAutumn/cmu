package cc.newex.dax.boss.web.controller.outer.v1.extra.currency;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.extra.currency.CurrencyBaseInfoExtraVO;
import cc.newex.dax.extra.client.ExtraCmsCurrencyAdminClient;
import cc.newex.dax.extra.dto.currency.CurrencyBaseInfoDTO;
import cc.newex.dax.extra.dto.currency.CurrencyDetailInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.List;

/**
 * 币种wiki管理 - 币种基本信息
 *
 * @author liutiejun
 * @date 2018-07-11
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/boss/extra/currency/base")
public class CurrencyBaseInfoController {

    @Autowired
    private ExtraCmsCurrencyAdminClient extraCmsCurrencyAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增CurrencyBaseInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_BASE_INFO_ADD"})
    public ResponseResult add(@Valid final CurrencyBaseInfoExtraVO currencyBaseInfoExtraVO, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final CurrencyBaseInfoDTO currencyBaseInfoDTO = CurrencyBaseInfoDTO.builder()
                .code(currencyBaseInfoExtraVO.getCode())
                .name(currencyBaseInfoExtraVO.getName())
                .symbol(currencyBaseInfoExtraVO.getSymbol())
                .imgUrl(currencyBaseInfoExtraVO.getImgUrl())
                .issuePrice(currencyBaseInfoExtraVO.getIssuePrice())
                .issueDate(currencyBaseInfoExtraVO.getIssueDate())
                .maxSupply(currencyBaseInfoExtraVO.getMaxSupply())
                .circulatingSupply(currencyBaseInfoExtraVO.getCirculatingSupply())
                .officalWebsite(currencyBaseInfoExtraVO.getOfficalWebsite())
                .explorer(currencyBaseInfoExtraVO.getExplorer())
                .sourceCodeUrl(currencyBaseInfoExtraVO.getSourceCodeUrl())
                .status(currencyBaseInfoExtraVO.getStatus())
                .publisherId(user.getId())
                .sort(currencyBaseInfoExtraVO.getSort())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsCurrencyAdminClient.saveCurrencyBaseInfo(currencyBaseInfoDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改CurrencyBaseInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_BASE_INFO_EDIT"})
    public ResponseResult edit(@Valid final CurrencyBaseInfoExtraVO currencyBaseInfoExtraVO,
                               @RequestParam("id") final Long id, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final CurrencyBaseInfoDTO currencyBaseInfoDTO = CurrencyBaseInfoDTO.builder()
                .id(id)
                .code(currencyBaseInfoExtraVO.getCode())
                .name(currencyBaseInfoExtraVO.getName())
                .symbol(currencyBaseInfoExtraVO.getSymbol())
                .imgUrl(currencyBaseInfoExtraVO.getImgUrl())
                .issuePrice(currencyBaseInfoExtraVO.getIssuePrice())
                .issueDate(currencyBaseInfoExtraVO.getIssueDate())
                .maxSupply(currencyBaseInfoExtraVO.getMaxSupply())
                .circulatingSupply(currencyBaseInfoExtraVO.getCirculatingSupply())
                .officalWebsite(currencyBaseInfoExtraVO.getOfficalWebsite())
                .explorer(currencyBaseInfoExtraVO.getExplorer())
                .sourceCodeUrl(currencyBaseInfoExtraVO.getSourceCodeUrl())
                .status(currencyBaseInfoExtraVO.getStatus())
                .publisherId(user.getId())
                .sort(currencyBaseInfoExtraVO.getSort())
                .updatedDate(new Date())
                .build();

        ResponseResult result = null;

        if (id == null) {
            result = this.extraCmsCurrencyAdminClient.saveCurrencyBaseInfo(currencyBaseInfoDTO);
        } else {
            final String oldCode = this.extraCmsCurrencyAdminClient.getCurrencyBaseInfoById(id).getData().getCode();

            final List<CurrencyDetailInfoDTO> currencyDetailInfoDTOList = this.extraCmsCurrencyAdminClient.getCurrencyDetailInfoByCode(oldCode, null).getData();

            if (CollectionUtils.isNotEmpty(currencyDetailInfoDTOList)) {
                currencyDetailInfoDTOList.forEach(currencyDetailInfoDTO -> {
                    currencyDetailInfoDTO.setCode(currencyBaseInfoExtraVO.getCode());

                    this.extraCmsCurrencyAdminClient.updateCurrencyDetailInfo(currencyDetailInfoDTO);
                });
            }

            result = this.extraCmsCurrencyAdminClient.updateCurrencyBaseInfo(currencyBaseInfoDTO);
        }

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除币种信息、进展、动态")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_BASE_INFO_REMOVE"})
    public ResponseResult remove(final String code, final HttpServletRequest request) {
        final ResponseResult result = this.extraCmsCurrencyAdminClient.removeCurrencyBaseInfoByCode(code);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取CurrencyBaseInfo列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_BASE_INFO_VIEW"})
    public ResponseResult list(@RequestParam(value = "code", required = false) final String code,
                               @RequestParam(value = "name", required = false) final String name,
                               final DataGridPager<CurrencyBaseInfoDTO> pager) {

        final CurrencyBaseInfoDTO.CurrencyBaseInfoDTOBuilder builder = CurrencyBaseInfoDTO.builder();

        if (StringUtils.isNotBlank(code)) {
            builder.code(code);
        }

        if (StringUtils.isNotBlank(name)) {
            builder.name(name);
        }

        final CurrencyBaseInfoDTO currencyBaseInfoDTO = builder.build();
        pager.setQueryParameter(currencyBaseInfoDTO);

        final ResponseResult responseResult = this.extraCmsCurrencyAdminClient.listCurrencyBaseInfo(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

    @GetMapping(value = "/listAll")
    @OpLog(name = "获取所有的CurrencyBaseInfo列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_BASE_INFO_VIEW"})
    public ResponseResult listAll() {
        final ResponseResult result = this.extraCmsCurrencyAdminClient.listAllCurrencyBaseInfo();

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/getByCode")
    @OpLog(name = "获取CurrencyBaseInfo")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_DETAIL_INFO_VIEW"})
    public ResponseResult getByCode(@RequestParam(value = "code", required = false) final String code) {

        final ResponseResult responseResult = this.extraCmsCurrencyAdminClient.getCurrencyBaseInfoByCode(code);

        return ResultUtil.getCheckedResponseResult(responseResult);
    }

}
