package cc.newex.dax.extra.rest.controller.admin.v1.currency;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.currency.CurrencyBaseInfoExample;
import cc.newex.dax.extra.domain.currency.CurrencyBaseInfo;
import cc.newex.dax.extra.dto.currency.CurrencyBaseInfoDTO;
import cc.newex.dax.extra.service.admin.currency.CurrencyBaseInfoAdminService;
import cc.newex.dax.extra.service.admin.currency.CurrencyDetailInfoAdminService;
import cc.newex.dax.extra.service.admin.currency.TokenInsightAdminService;
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
@RequestMapping(value = "/admin/v1/extra/cms/currency/base")
public class CurrencyBaseInfoAdminController {

    @Autowired
    private CurrencyBaseInfoAdminService currencyBaseInfoAdminService;

    @Autowired
    private CurrencyDetailInfoAdminService currencyDetailInfoAdminService;

    @Autowired
    private TokenInsightAdminService tokenInsightAdminService;

    /**
     * 保存CurrencyBaseInfo
     *
     * @param currencyBaseInfoDTO
     * @return
     */
    @PostMapping(value = "/saveCurrencyBaseInfo")
    public ResponseResult saveCurrencyBaseInfo(@RequestBody @Valid final CurrencyBaseInfoDTO currencyBaseInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyBaseInfo currencyBaseInfo = mapper.map(currencyBaseInfoDTO, CurrencyBaseInfo.class);

        final int save = this.currencyBaseInfoAdminService.add(currencyBaseInfo);

        return ResultUtils.success(save);
    }

    /**
     * 更新CurrencyBaseInfo
     *
     * @param currencyBaseInfoDTO
     * @return
     */
    @PostMapping(value = "/updateCurrencyBaseInfo")
    public ResponseResult updateCurrencyBaseInfo(@RequestBody @Valid final CurrencyBaseInfoDTO currencyBaseInfoDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyBaseInfo currencyBaseInfo = mapper.map(currencyBaseInfoDTO, CurrencyBaseInfo.class);

        final int update = this.currencyBaseInfoAdminService.editById(currencyBaseInfo);

        return ResultUtils.success(update);
    }

    /**
     * List CurrencyBaseInfo
     *
     * @return
     */
    @PostMapping(value = "/listCurrencyBaseInfo")
    public ResponseResult listCurrencyBaseInfo(@RequestBody final DataGridPager<CurrencyBaseInfoDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();

        final CurrencyBaseInfo currencyBaseInfo = mapper.map(pager.getQueryParameter(), CurrencyBaseInfo.class);
        currencyBaseInfo.setIssuePrice(null);

        final CurrencyBaseInfoExample example = CurrencyBaseInfoExample.toExample(currencyBaseInfo);

        final List<CurrencyBaseInfo> list = this.currencyBaseInfoAdminService.getByPage(pageInfo, example);
        final List<CurrencyBaseInfoDTO> currencyBaseInfoDTOS = mapper.map(
                list, new TypeToken<List<CurrencyBaseInfoDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), currencyBaseInfoDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getCurrencyBaseInfoById")
    public ResponseResult<CurrencyBaseInfoDTO> getCurrencyBaseInfoById(@RequestParam("id") @NotNull final Long id) {
        final CurrencyBaseInfo currencyBaseInfo = this.currencyBaseInfoAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();

        CurrencyBaseInfoDTO currencyBaseInfoDTO = null;

        if (currencyBaseInfo != null) {
            currencyBaseInfoDTO = mapper.map(currencyBaseInfo, CurrencyBaseInfoDTO.class);
        }

        return ResultUtils.success(currencyBaseInfoDTO);
    }

    /**
     * List All CurrencyBaseInfo
     *
     * @return
     */
    @GetMapping(value = "/listAllCurrencyBaseInfo")
    public ResponseResult listAllCurrencyBaseInfo() {
        final CurrencyBaseInfoExample example = new CurrencyBaseInfoExample();
        example.setOrderByClause("code asc");

        final List<CurrencyBaseInfo> currencyBaseInfoList = this.currencyBaseInfoAdminService.getByExample(example);

        final ModelMapper mapper = new ModelMapper();

        final List<CurrencyBaseInfoDTO> currencyBaseInfoDTOS = mapper.map(
                currencyBaseInfoList, new TypeToken<List<CurrencyBaseInfoDTO>>() {
                }.getType()
        );

        return ResultUtils.success(currencyBaseInfoDTOS);
    }

    /**
     * 按币种编码查询
     *
     * @param code
     * @return
     */
    @GetMapping(value = "/getCurrencyBaseInfoByCode")
    public ResponseResult getCurrencyBaseInfoByCode(@RequestParam("code") @NotBlank final String code) {
        final ModelMapper mapper = new ModelMapper();

        final List<CurrencyBaseInfo> list = this.currencyBaseInfoAdminService.getByCode(code);

        final List<CurrencyBaseInfoDTO> currencyBaseInfoDTOS = mapper.map(
                list, new TypeToken<List<CurrencyBaseInfoDTO>>() {
                }.getType()
        );

        return ResultUtils.success(currencyBaseInfoDTOS);
    }

    /**
     * 删除CurrencyBaseInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeCurrencyBaseInfo")
    public ResponseResult removeCurrencyBaseInfo(@RequestParam("id") final Long id) {
        final int remove = this.currencyBaseInfoAdminService.removeById(id);

        return ResultUtils.success(remove);
    }

    /**
     * 按币种删除数据，同时删除币种信息、进展、动态
     *
     * @param code
     * @return
     */
    @PostMapping("/removeCurrencyBaseInfoByCode")
    public ResponseResult removeCurrencyBaseInfoByCode(@RequestParam("code") final String code) {
        return ResultUtils.success(this.currencyBaseInfoAdminService.removeByCode(code));
    }

}