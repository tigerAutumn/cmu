package cc.newex.dax.extra.rest.controller.admin.v1.currency;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.currency.CurrencyDetailInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyDetailInfo;
import cc.newex.dax.extra.dto.currency.CurrencyDetailInfoDTO;
import cc.newex.dax.extra.service.admin.currency.CurrencyDetailInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 全球数字货币基本信息表 控制器类
 *
 * @author newex-team
 * @date 2018-06-29
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/currency/detail")
public class CurrencyDetailInfoAdminController {

    @Autowired
    private CurrencyDetailInfoAdminService currencyDetailInfoAdminService;

    /**
     * 保存CurrencyDetailInfo
     *
     * @param currencyDetailInfoDTO
     * @return
     */
    @PostMapping(value = "/saveCurrencyDetailInfo")
    public ResponseResult saveCurrencyDetailInfo(@RequestBody @Valid final CurrencyDetailInfoDTO currencyDetailInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyDetailInfo currencyDetailInfo = mapper.map(currencyDetailInfoDTO, CurrencyDetailInfo.class);

        final int save = this.currencyDetailInfoAdminService.add(currencyDetailInfo);

        return ResultUtils.success(save);
    }

    /**
     * 更新CurrencyDetailInfo
     *
     * @param currencyDetailInfoDTO
     * @return
     */
    @PostMapping(value = "/updateCurrencyDetailInfo")
    public ResponseResult updateCurrencyDetailInfo(@RequestBody @Valid final CurrencyDetailInfoDTO currencyDetailInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyDetailInfo currencyDetailInfo = mapper.map(currencyDetailInfoDTO, CurrencyDetailInfo.class);

        final int update = this.currencyDetailInfoAdminService.editById(currencyDetailInfo);

        return ResultUtils.success(update);
    }

    /**
     * List CurrencyDetailInfo
     *
     * @return
     */
    @PostMapping(value = "/listCurrencyDetailInfo")
    public ResponseResult listCurrencyDetailInfo(@RequestBody final DataGridPager<CurrencyDetailInfoDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final CurrencyDetailInfo currencyDetailInfo = mapper.map(pager.getQueryParameter(), CurrencyDetailInfo.class);
        final CurrencyDetailInfoExample example = CurrencyDetailInfoExample.toExample(currencyDetailInfo);

        final List<CurrencyDetailInfo> list = this.currencyDetailInfoAdminService.getByPage(pageInfo, example);
        final List<CurrencyDetailInfoDTO> currencyDetailInfoDTOS = mapper.map(
                list, new TypeToken<List<CurrencyDetailInfoDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), currencyDetailInfoDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getCurrencyDetailInfoById")
    public ResponseResult getCurrencyDetailInfoById(@RequestParam("id") @NotNull final Long id) {
        final CurrencyDetailInfo currencyDetailInfo = this.currencyDetailInfoAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();

        CurrencyDetailInfoDTO currencyDetailInfoDTO = null;

        if (currencyDetailInfo != null) {
            currencyDetailInfoDTO = mapper.map(currencyDetailInfo, CurrencyDetailInfoDTO.class);
        }

        return ResultUtils.success(currencyDetailInfoDTO);
    }

    /**
     * 按照币种编码和语言查询，支持多语言，如果当前语言不支持，默认为en-us
     *
     * @param code
     * @param locale
     * @return
     */
    @GetMapping(value = "/getCurrencyDetailInfoByCode")
    public ResponseResult<List<CurrencyDetailInfoDTO>> getCurrencyDetailInfoByCode(@RequestParam(name = "code") @NotBlank final String code,
                                                                                   @RequestParam(name = "locale", required = false) final String locale) {
        final ModelMapper mapper = new ModelMapper();

        final List<CurrencyDetailInfo> list = this.currencyDetailInfoAdminService.getAllByCode(code, locale);

        final List<CurrencyDetailInfoDTO> currencyDetailInfoDTOS = mapper.map(
                list, new TypeToken<List<CurrencyDetailInfoDTO>>() {
                }.getType()
        );

        return ResultUtils.success(currencyDetailInfoDTOS);
    }

    /**
     * 删除CurrencyDetailInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeCurrencyDetailInfo")
    public ResponseResult removeCurrencyDetailInfo(@RequestParam("id") final Long id) {
        final int remove = this.currencyDetailInfoAdminService.removeById(id);

        return ResultUtils.success(remove);
    }

}