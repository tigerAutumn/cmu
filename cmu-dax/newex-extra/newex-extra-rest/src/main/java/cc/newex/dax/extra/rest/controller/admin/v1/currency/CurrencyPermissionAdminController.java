package cc.newex.dax.extra.rest.controller.admin.v1.currency;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.currency.CurrencyPermissionExample;
import cc.newex.dax.extra.domain.currency.CurrencyPermission;
import cc.newex.dax.extra.dto.currency.CurrencyPermissionDTO;
import cc.newex.dax.extra.service.admin.currency.CurrencyPermissionAdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liutiejun
 * @date 2018-08-20
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/currency/permission")
public class CurrencyPermissionAdminController {

    @Autowired
    private CurrencyPermissionAdminService currencyPermissionAdminService;

    /**
     * 保存CurrencyPermission
     *
     * @param currencyPermissionDTO
     * @return
     */
    @PostMapping(value = "/saveCurrencyPermission")
    public ResponseResult saveCurrencyPermission(@RequestBody final CurrencyPermissionDTO currencyPermissionDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyPermission currencyPermission = mapper.map(currencyPermissionDTO, CurrencyPermission.class);

        final int save = this.currencyPermissionAdminService.add(currencyPermission);

        return ResultUtils.success(save);
    }

    /**
     * 更新CurrencyPermission
     *
     * @param currencyPermissionDTO
     * @return
     */
    @PostMapping(value = "/updateCurrencyPermission")
    public ResponseResult updateCurrencyPermission(@RequestBody final CurrencyPermissionDTO currencyPermissionDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyPermission currencyPermission = mapper.map(currencyPermissionDTO, CurrencyPermission.class);

        final int update = this.currencyPermissionAdminService.editById(currencyPermission);

        return ResultUtils.success(update);
    }

    /**
     * 删除CurrencyPermission
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeCurrencyPermission")
    public ResponseResult removeCurrencyPermission(@RequestParam("id") final Long id) {
        final int remove = this.currencyPermissionAdminService.removeById(id);

        return ResultUtils.success(remove);
    }

    /**
     * List CurrencyPermission
     *
     * @return
     */
    @PostMapping(value = "/listCurrencyPermission")
    public ResponseResult listCurrencyPermission(@RequestBody final DataGridPager<CurrencyPermissionDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final CurrencyPermission currencyPermission = mapper.map(pager.getQueryParameter(), CurrencyPermission.class);
        final CurrencyPermissionExample example = CurrencyPermissionExample.toExample(currencyPermission);

        final List<CurrencyPermission> list = this.currencyPermissionAdminService.getByPage(pageInfo, example);
        final List<CurrencyPermissionDTO> currencyPermissionDTOS = mapper.map(
                list, new TypeToken<List<CurrencyPermissionDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), currencyPermissionDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getCurrencyPermissionById")
    public ResponseResult getCurrencyPermissionById(@RequestParam("id") final Long id) {
        final CurrencyPermission currencyPermission = this.currencyPermissionAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final CurrencyPermissionDTO currencyPermissionDTO = mapper.map(currencyPermission, CurrencyPermissionDTO.class);

        return ResultUtils.success(currencyPermissionDTO);
    }

}
