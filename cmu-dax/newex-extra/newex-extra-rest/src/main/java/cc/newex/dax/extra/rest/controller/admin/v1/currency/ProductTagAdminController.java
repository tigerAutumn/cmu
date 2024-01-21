package cc.newex.dax.extra.rest.controller.admin.v1.currency;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.criteria.currency.ProductTagExample;
import cc.newex.dax.extra.domain.currency.ProductTag;
import cc.newex.dax.extra.dto.common.TreeNodeDTO;
import cc.newex.dax.extra.dto.currency.ProductTagDTO;
import cc.newex.dax.extra.service.admin.currency.ProductTagAdminService;
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
 * @author liutiejun
 * @date 2018-09-26
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/cms/currency/product-tag")
public class ProductTagAdminController {

    @Autowired
    private ProductTagAdminService productTagAdminService;

    @Autowired
    private TagCategoryAdminService tagCategoryAdminService;

    /**
     * 保存ProductTag
     *
     * @param productTagDTO
     * @return
     */
    @PostMapping(value = "/saveProductTag")
    public ResponseResult saveProductTag(@RequestBody final ProductTagDTO productTagDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ProductTag productTag = mapper.map(productTagDTO, ProductTag.class);

        final int save = this.productTagAdminService.add(productTag);

        return ResultUtils.success(save);
    }

    /**
     * 批量保存ProductTag
     *
     * @param productTagDTOList
     * @return
     */
    @PostMapping(value = "/batchSaveProductTag")
    public ResponseResult batchSaveProductTag(@RequestBody final List<ProductTagDTO> productTagDTOList) {
        final ModelMapper mapper = new ModelMapper();
        final List<ProductTag> productTagList = mapper.map(productTagDTOList, new TypeToken<List<ProductTag>>() {
        }.getType());

        final int save = this.productTagAdminService.batchAdd(productTagList);

        return ResultUtils.success(save);
    }

    /**
     * 更新ProductTag
     *
     * @param productTagDTO
     * @return
     */
    @PostMapping(value = "/updateProductTag")
    public ResponseResult updateProductTag(@RequestBody final ProductTagDTO productTagDTO) {
        final ModelMapper mapper = new ModelMapper();
        final ProductTag productTag = mapper.map(productTagDTO, ProductTag.class);

        final int update = this.productTagAdminService.editById(productTag);

        return ResultUtils.success(update);
    }

    /**
     * 批量更新ProductTag
     *
     * @param productTagDTOList
     * @return
     */
    @PostMapping(value = "/batchUpdateProductTag")
    public ResponseResult batchUpdateProductTag(@RequestBody final List<ProductTagDTO> productTagDTOList) {
        final ModelMapper mapper = new ModelMapper();
        final List<ProductTag> productTagList = mapper.map(productTagDTOList, new TypeToken<List<ProductTag>>() {
        }.getType());

        final int update = this.productTagAdminService.batchEdit(productTagList);

        return ResultUtils.success(update);
    }

    /**
     * 批量更新ProductTag
     *
     * @param productTagDTOList
     * @return
     */
    @PostMapping(value = "/batchSaveOrUpdateProductTag")
    public ResponseResult batchSaveOrUpdateProductTag(@RequestBody final List<ProductTagDTO> productTagDTOList) {
        final int result = this.productTagAdminService.batchSaveOrUpdateProductTag(productTagDTOList);

        return ResultUtils.success(result);
    }

    /**
     * List ProductTag
     *
     * @return
     */
    @PostMapping(value = "/listProductTag")
    public ResponseResult listProductTag(@RequestBody final DataGridPager<ProductTagDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final ProductTag productTag = mapper.map(pager.getQueryParameter(), ProductTag.class);
        final ProductTagExample example = ProductTagExample.toExample(productTag);

        final List<ProductTag> list = this.productTagAdminService.getByPage(pageInfo, example);
        final List<ProductTagDTO> productTagDTOS = mapper.map(
                list, new TypeToken<List<ProductTagDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), productTagDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getProductTagById")
    public ResponseResult getProductTagById(@RequestParam("id") final Long id) {
        final ProductTag productTag = this.productTagAdminService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final ProductTagDTO productTagDTO = mapper.map(productTag, ProductTagDTO.class);

        return ResultUtils.success(productTagDTO);
    }

    /**
     * 查询币对对应的标签，返回树形结构
     *
     * @param productCode
     * @param type
     * @return
     */
    @GetMapping(value = "/listTreeNodeByProductCode")
    public ResponseResult<List<TreeNodeDTO>> listTreeNodeByProductCode(@RequestParam("productCode") final String productCode, @RequestParam("type") final Integer type) {
        final String locale = Locale.CHINA.toLanguageTag();

        final List<TreeNodeDTO> treeNodeList = this.tagCategoryAdminService.listTreeNode(locale, type);

        final List<ProductTag> productTagList = this.productTagAdminService.getByCode(productCode);

        final Map<String, Boolean> checkMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(productTagList)) {
            productTagList.stream().forEach(productTag -> checkMap.put(productTag.getTagInfoCode(), true));
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
