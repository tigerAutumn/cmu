package cc.newex.dax.extra.rest.controller.admin.v1.currency;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.currency.CurrencyTagExample;
import cc.newex.dax.extra.domain.currency.CurrencyTag;
import cc.newex.dax.extra.dto.common.TreeNodeDTO;
import cc.newex.dax.extra.dto.currency.CurrencyTagDTO;
import cc.newex.dax.extra.enums.currency.TagCategoryTypeEnum;
import cc.newex.dax.extra.service.admin.currency.CurrencyTagAdminService;
import cc.newex.dax.extra.service.admin.currency.TagCategoryAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 币种标签表 控制器类
 *
 * @author liutiejun
 * @date 2018-07-19
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/currency/currency-tag")
public class CurrencyTagAdminController {

    @Autowired
    private CurrencyTagAdminService currencyTagAdminService;

    @Autowired
    private TagCategoryAdminService tagCategoryAdminService;

    /**
     * 保存CurrencyTag
     *
     * @param currencyTagDTO
     * @return
     */
    @PostMapping(value = "/saveCurrencyTag")
    public ResponseResult saveCurrencyTag(@RequestBody final CurrencyTagDTO currencyTagDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyTag currencyTag = mapper.map(currencyTagDTO, CurrencyTag.class);

        final int save = this.currencyTagAdminService.add(currencyTag);

        return ResultUtils.success(save);
    }

    /**
     * 批量保存CurrencyTag
     *
     * @param currencyTagDTOList
     * @return
     */
    @PostMapping(value = "/batchSaveCurrencyTag")
    public ResponseResult batchSaveCurrencyTag(@RequestBody final List<CurrencyTagDTO> currencyTagDTOList) {
        final ModelMapper mapper = new ModelMapper();
        final List<CurrencyTag> currencyTagList = mapper.map(currencyTagDTOList, new TypeToken<List<CurrencyTag>>() {
        }.getType());

        final int save = this.currencyTagAdminService.batchAdd(currencyTagList);

        return ResultUtils.success(save);
    }

    /**
     * 更新CurrencyTag
     *
     * @param currencyTagDTO
     * @return
     */
    @PostMapping(value = "/updateCurrencyTag")
    public ResponseResult updateCurrencyTag(@RequestBody final CurrencyTagDTO currencyTagDTO) {
        final ModelMapper mapper = new ModelMapper();
        final CurrencyTag currencyTag = mapper.map(currencyTagDTO, CurrencyTag.class);

        final int update = this.currencyTagAdminService.editById(currencyTag);

        return ResultUtils.success(update);
    }

    /**
     * 批量更新CurrencyTag
     *
     * @param currencyTagDTOList
     * @return
     */
    @PostMapping(value = "/batchUpdateCurrencyTag")
    public ResponseResult batchUpdateCurrencyTag(@RequestBody final List<CurrencyTagDTO> currencyTagDTOList) {
        final ModelMapper mapper = new ModelMapper();
        final List<CurrencyTag> currencyTagList = mapper.map(currencyTagDTOList, new TypeToken<List<CurrencyTag>>() {
        }.getType());

        final int update = this.currencyTagAdminService.batchEdit(currencyTagList);

        return ResultUtils.success(update);
    }

    /**
     * 批量更新CurrencyTag
     *
     * @param currencyTagDTOList
     * @return
     */
    @PostMapping(value = "/batchSaveOrUpdateCurrencyTag")
    public ResponseResult batchSaveOrUpdateCurrencyTag(@RequestBody final List<CurrencyTagDTO> currencyTagDTOList) {
        final int result = this.currencyTagAdminService.batchSaveOrUpdateCurrencyTag(currencyTagDTOList);

        return ResultUtils.success(result);
    }

    /**
     * List CurrencyTag
     *
     * @return
     */
    @PostMapping(value = "/listCurrencyTag")
    public ResponseResult listCurrencyTag(@RequestBody final DataGridPager<CurrencyTagDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final CurrencyTag currencyTag = mapper.map(pager.getQueryParameter(), CurrencyTag.class);
        final CurrencyTagExample example = CurrencyTagExample.toExample(currencyTag);

        final List<CurrencyTag> list = this.currencyTagAdminService.getByPage(pageInfo, example);
        final List<CurrencyTagDTO> currencyTagDTOS = mapper.map(
                list, new TypeToken<List<CurrencyTagDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), currencyTagDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getCurrencyTagById")
    public ResponseResult getCurrencyTagById(@RequestParam("id") final Long id) {
        final CurrencyTag currencyTag = this.currencyTagAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final CurrencyTagDTO currencyTagDTO = mapper.map(currencyTag, CurrencyTagDTO.class);

        return ResultUtils.success(currencyTagDTO);
    }

    /**
     * 根据currencyCode查询
     *
     * @param currencyCode
     * @return
     */
    @GetMapping(value = "/getCurrencyTagByCurrencyCode")
    public ResponseResult<List<CurrencyTagDTO>> getCurrencyTagByCurrencyCode(@RequestParam("currency") final String currencyCode) {
        final ModelMapper mapper = new ModelMapper();

        final List<CurrencyTag> currencyTagList = this.currencyTagAdminService.getByCode(currencyCode);

        final List<CurrencyTagDTO> currencyTagDTOS = mapper.map(
                currencyTagList, new TypeToken<List<CurrencyTagDTO>>() {
                }.getType()
        );

        return ResultUtils.success(currencyTagDTOS);
    }

    /**
     * 查询币种对应的标签，返回树形结构
     *
     * @param currencyCode
     * @return
     */
    @GetMapping(value = "/listTreeNodeByCurrencyCode")
    public ResponseResult<List<TreeNodeDTO>> listTreeNodeByCurrencyCode(@RequestParam("currency") final String currencyCode) {
        final String locale = Locale.CHINA.toLanguageTag();
        final Integer type = TagCategoryTypeEnum.CURRENCY.getCode();

        final List<TreeNodeDTO> treeNodeList = this.tagCategoryAdminService.listTreeNode(locale, type);

        final List<CurrencyTag> currencyTagList = this.currencyTagAdminService.getByCode(currencyCode);

        final Map<String, Boolean> checkMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(currencyTagList)) {
            currencyTagList.stream().forEach(currencyTag -> checkMap.put(currencyTag.getTagInfoCode(), true));
        }

        if (CollectionUtils.isNotEmpty(treeNodeList)) {
            for (final TreeNodeDTO treeNode : treeNodeList) {
                final List<TreeNodeDTO> children = treeNode.getChildren();

                if (CollectionUtils.isEmpty(children)) {
                    continue;
                }

                final int total = this.check(children, checkMap);

                if (children.size() == total) {
                    treeNode.setChecked(true);
                }
            }
        }

        return ResultUtils.success(treeNodeList);
    }

    private int check(final List<TreeNodeDTO> children, final Map<String, Boolean> checkMap) {
        int total = 0;

        for (final TreeNodeDTO treeNode : children) {
            final Boolean check = checkMap.get(treeNode.getId());

            if (BooleanUtils.isTrue(check)) {
                treeNode.setChecked(true);

                total++;
            }
        }

        return total;
    }

}
