package cc.newex.dax.boss.web.controller.outer.v1.users;


import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.users.client.UsersAdminClient;
import cc.newex.dax.users.dto.admin.DictCountryReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 丁昆
 * @date 2018/6/22
 * @des 国家管理
 */
@RestController
@Slf4j
@RequestMapping("/v1/boss/spot/country")

public class CountryController {

    @Autowired
    UsersAdminClient userAdminClient;

    @GetMapping(value = "/list")
    @OpLog(name = "获取国家列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_COUNTRY_VIEW"})
    public ResponseResult list(final DataGridPager<DictCountryReqDTO> pager, final String name, final String capital,
                               final String locale, final Integer status) {

        final DictCountryReqDTO dictCountryReqDTO = DictCountryReqDTO.builder()
                .locale(locale)
                .name(name)
                .status(status)
                .build();
        pager.setQueryParameter(dictCountryReqDTO);

        try {
            final ResponseResult list = this.userAdminClient.getDictCountry(pager);
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            CountryController.log.error("list country error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/add")
    @OpLog(name = "添加国家列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_COUNTRY_ADD"})
    public ResponseResult add(final DictCountryReqDTO dictCountryReqDTO) {
        try {
            final ResponseResult list = this.userAdminClient.addDictCountry(dictCountryReqDTO);
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            CountryController.log.error("add country error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑国家列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_COUNTRY_EDIT"})
    public ResponseResult edit(final DictCountryReqDTO dictCountryReqDTO) {
        try {
            final ResponseResult list = this.userAdminClient.updateDictCountry(dictCountryReqDTO.getId(), dictCountryReqDTO);
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            CountryController.log.error("edit country error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除国家列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_USERS_COUNTRY_REMOVE"})
    public ResponseResult remove(final Integer id) {
        try {
            final ResponseResult list = this.userAdminClient.remove(id);
            return ResultUtil.getCheckedResponseResult(list);
        } catch (final Exception e) {
            CountryController.log.error("remove country error " + e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }
    }

}
