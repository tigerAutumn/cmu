package cc.newex.dax.extra.rest.controller.admin.v1.currency;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.currency.CurrencyTrendInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyTrendInfo;
import cc.newex.dax.extra.dto.currency.CurrencyTrendInfoDTO;
import cc.newex.dax.extra.service.admin.currency.CurrencyTrendInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @date 2018-08-21
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/currency/trend-info")
public class CurrencyTrendInfoAdminController {

    @Autowired
    private CurrencyTrendInfoAdminService currencyTrendInfoAdminService;

    /**
     * 保存CurrencyTrendInfo
     *
     * @param currencyTrendInfoDTO
     * @return
     */
    @PostMapping(value = "/saveCurrencyTrendInfo")
    public ResponseResult saveCurrencyTrendInfo(@RequestBody @Valid final CurrencyTrendInfoDTO currencyTrendInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyTrendInfo currencyTrendInfo = mapper.map(currencyTrendInfoDTO, CurrencyTrendInfo.class);

        this.setDefaultValue(currencyTrendInfo);

        final int save = this.currencyTrendInfoAdminService.add(currencyTrendInfo);

        return ResultUtils.success(save);
    }

    /**
     * 更新CurrencyTrendInfo
     *
     * @param currencyTrendInfoDTO
     * @return
     */
    @PostMapping(value = "/updateCurrencyTrendInfo")
    public ResponseResult updateCurrencyTrendInfo(@RequestBody @Valid final CurrencyTrendInfoDTO currencyTrendInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyTrendInfo currencyTrendInfo = mapper.map(currencyTrendInfoDTO, CurrencyTrendInfo.class);

        this.setDefaultValue(currencyTrendInfo);

        final int update = this.currencyTrendInfoAdminService.editById(currencyTrendInfo);

        return ResultUtils.success(update);
    }

    private void setDefaultValue(final CurrencyTrendInfo currencyTrendInfo) {
        if (currencyTrendInfo == null) {
            return;
        }

        final String content = StringUtils.defaultString(currencyTrendInfo.getContent(), "");

        currencyTrendInfo.setContent(content);
    }

    /**
     * 删除CurrencyTrendInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeCurrencyTrendInfo")
    public ResponseResult removeCurrencyTrendInfo(@RequestParam("id") final Long id) {
        final int remove = this.currencyTrendInfoAdminService.removeById(id);

        return ResultUtils.success(remove);
    }

    /**
     * List CurrencyTrendInfo
     *
     * @return
     */
    @PostMapping(value = "/listCurrencyTrendInfo")
    public ResponseResult listCurrencyTrendInfo(@RequestBody final DataGridPager<CurrencyTrendInfoDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final CurrencyTrendInfo currencyTrendInfo = mapper.map(pager.getQueryParameter(), CurrencyTrendInfo.class);
        final CurrencyTrendInfoExample example = CurrencyTrendInfoExample.toExample(currencyTrendInfo);

        final List<CurrencyTrendInfo> list = this.currencyTrendInfoAdminService.getByPage(pageInfo, example);
        final List<CurrencyTrendInfoDTO> currencyTrendInfoDTOS = mapper.map(
                list, new TypeToken<List<CurrencyTrendInfoDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), currencyTrendInfoDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getCurrencyTrendInfoById")
    public ResponseResult getCurrencyTrendInfoById(@RequestParam("id") final Long id) {
        final CurrencyTrendInfo currencyTrendInfo = this.currencyTrendInfoAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final CurrencyTrendInfoDTO currencyTrendInfoDTO = mapper.map(currencyTrendInfo, CurrencyTrendInfoDTO.class);

        return ResultUtils.success(currencyTrendInfoDTO);
    }

}
