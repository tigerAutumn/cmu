package cc.newex.dax.extra.rest.controller.admin.v1.currency;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.currency.CurrencyProgressInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyProgressInfo;
import cc.newex.dax.extra.dto.currency.CurrencyProgressInfoDTO;
import cc.newex.dax.extra.service.admin.currency.CurrencyProgressInfoAdminService;
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

import javax.validation.Valid;
import java.util.List;

/**
 * @author liutiejun
 * @date 2018-08-20
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/currency/progress-info")
public class CurrencyProgressInfoAdminController {

    @Autowired
    private CurrencyProgressInfoAdminService currencyProgressInfoAdminService;

    /**
     * 保存CurrencyProgressInfo
     *
     * @param currencyProgressInfoDTO
     * @return
     */
    @PostMapping(value = "/saveCurrencyProgressInfo")
    public ResponseResult saveCurrencyProgressInfo(@RequestBody @Valid final CurrencyProgressInfoDTO currencyProgressInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyProgressInfo currencyProgressInfo = mapper.map(currencyProgressInfoDTO, CurrencyProgressInfo.class);

        final int save = this.currencyProgressInfoAdminService.add(currencyProgressInfo);

        return ResultUtils.success(save);
    }

    /**
     * 更新CurrencyProgressInfo
     *
     * @param currencyProgressInfoDTO
     * @return
     */
    @PostMapping(value = "/updateCurrencyProgressInfo")
    public ResponseResult updateCurrencyProgressInfo(@RequestBody @Valid final CurrencyProgressInfoDTO currencyProgressInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyProgressInfo currencyProgressInfo = mapper.map(currencyProgressInfoDTO, CurrencyProgressInfo.class);

        final int update = this.currencyProgressInfoAdminService.editById(currencyProgressInfo);

        return ResultUtils.success(update);
    }

    /**
     * 删除CurrencyProgressInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeCurrencyProgressInfo")
    public ResponseResult removeCurrencyProgressInfo(@RequestParam("id") final Long id) {
        final int remove = this.currencyProgressInfoAdminService.removeById(id);

        return ResultUtils.success(remove);
    }

    /**
     * List CurrencyProgressInfo
     *
     * @return
     */
    @PostMapping(value = "/listCurrencyProgressInfo")
    public ResponseResult listCurrencyProgressInfo(@RequestBody final DataGridPager<CurrencyProgressInfoDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final CurrencyProgressInfo currencyProgressInfo = mapper.map(pager.getQueryParameter(), CurrencyProgressInfo.class);
        final CurrencyProgressInfoExample example = CurrencyProgressInfoExample.toExample(currencyProgressInfo);

        final List<CurrencyProgressInfo> list = this.currencyProgressInfoAdminService.getByPage(pageInfo, example);
        final List<CurrencyProgressInfoDTO> currencyProgressInfoDTOS = mapper.map(
                list, new TypeToken<List<CurrencyProgressInfoDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), currencyProgressInfoDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getCurrencyProgressInfoById")
    public ResponseResult getCurrencyProgressInfoById(@RequestParam("id") final Long id) {
        final CurrencyProgressInfo currencyProgressInfo = this.currencyProgressInfoAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final CurrencyProgressInfoDTO currencyProgressInfoDTO = mapper.map(currencyProgressInfo, CurrencyProgressInfoDTO.class);

        return ResultUtils.success(currencyProgressInfoDTO);
    }

}
