package cc.newex.dax.boss.web.controller.outer.v1.extra.currency;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.extra.currency.CurrencyPermissionExtraVO;
import cc.newex.dax.extra.client.ExtraCmsCurrencyAdminClient;
import cc.newex.dax.extra.dto.currency.CurrencyPermissionDTO;
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
@RequestMapping(value = "/v1/boss/extra/currency/permission")
public class CurrencyPermissionController {

    @Autowired
    private ExtraCmsCurrencyAdminClient extraCmsCurrencyAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增CurrencyPermission")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_PERMISSION_ADD"})
    public ResponseResult add(@Valid final CurrencyPermissionExtraVO currencyPermissionExtraVO, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final CurrencyPermissionDTO currencyPermissionDTO = CurrencyPermissionDTO.builder()
                .code(currencyPermissionExtraVO.getCode())
                .userId(currencyPermissionExtraVO.getUserId())
                .name(currencyPermissionExtraVO.getName())
                .organization(currencyPermissionExtraVO.getOrganization())
                .mobile(currencyPermissionExtraVO.getMobile())
                .status(currencyPermissionExtraVO.getStatus())
                .publisherId(user.getId())
                .sort(currencyPermissionExtraVO.getSort())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsCurrencyAdminClient.saveCurrencyPermission(currencyPermissionDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改CurrencyPermission")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_PERMISSION_EDIT"})
    public ResponseResult edit(@Valid final CurrencyPermissionExtraVO currencyPermissionExtraVO,
                               @RequestParam("id") final Long id, final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        final CurrencyPermissionDTO currencyPermissionDTO = CurrencyPermissionDTO.builder()
                .id(id)
                .code(currencyPermissionExtraVO.getCode())
                .userId(currencyPermissionExtraVO.getUserId())
                .name(currencyPermissionExtraVO.getName())
                .organization(currencyPermissionExtraVO.getOrganization())
                .mobile(currencyPermissionExtraVO.getMobile())
                .status(currencyPermissionExtraVO.getStatus())
                .publisherId(user.getId())
                .sort(currencyPermissionExtraVO.getSort())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.extraCmsCurrencyAdminClient.updateCurrencyPermission(currencyPermissionDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/remove")
    @OpLog(name = "删除CurrencyPermission")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_PERMISSION_REMOVE"})
    public ResponseResult remove(final Long id, final HttpServletRequest request) {
        final ResponseResult result = this.extraCmsCurrencyAdminClient.removeCurrencyPermission(id);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取CurrencyPermission列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_PERMISSION_VIEW"})
    public ResponseResult list(@RequestParam(value = "code", required = false) final String code,
                               @RequestParam(value = "userId", required = false) final Integer userId,
                               final DataGridPager<CurrencyPermissionDTO> pager) {

        final CurrencyPermissionDTO.CurrencyPermissionDTOBuilder builder = CurrencyPermissionDTO.builder();

        if (StringUtils.isNotBlank(code)) {
            builder.code(code);
        }

        if (userId != null && userId > 0) {
            builder.userId(userId);
        }

        final CurrencyPermissionDTO currencyPermissionDTO = builder.build();
        pager.setQueryParameter(currencyPermissionDTO);

        final ResponseResult result = this.extraCmsCurrencyAdminClient.listCurrencyPermission(pager);

        return ResultUtil.getDataGridResult(result);
    }

}
