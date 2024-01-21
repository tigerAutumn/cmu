package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.common.TreeNodeDTO;
import cc.newex.dax.extra.dto.currency.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

import java.util.List;

/**
 * 币种WIKI API
 *
 * @author liutiejun
 * @date 2018-07-11
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/cms/currency")
public interface ExtraCmsCurrencyAdminClient {

    /**
     * 保存CurrencyBaseInfo
     *
     * @param currencyBaseInfoDTO
     * @return
     */
    @PostMapping(value = "/base/saveCurrencyBaseInfo")
    ResponseResult saveCurrencyBaseInfo(@RequestBody final CurrencyBaseInfoDTO currencyBaseInfoDTO);

    /**
     * 更新CurrencyBaseInfo
     *
     * @param currencyBaseInfoDTO
     * @return
     */
    @PostMapping(value = "/base/updateCurrencyBaseInfo")
    ResponseResult updateCurrencyBaseInfo(@RequestBody final CurrencyBaseInfoDTO currencyBaseInfoDTO);

    /**
     * List CurrencyBaseInfo
     *
     * @return
     */
    @PostMapping(value = "/base/listCurrencyBaseInfo")
    ResponseResult listCurrencyBaseInfo(@RequestBody final DataGridPager<CurrencyBaseInfoDTO> pager);

    /**
     * List All CurrencyBaseInfo
     *
     * @return
     */
    @GetMapping(value = "/base/listAllCurrencyBaseInfo")
    ResponseResult listAllCurrencyBaseInfo();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/base/getCurrencyBaseInfoById")
    ResponseResult<CurrencyBaseInfoDTO> getCurrencyBaseInfoById(@RequestParam("id") final Long id);

    /**
     * 按币种编码查询
     *
     * @param code
     * @return
     */
    @GetMapping(value = "/base/getCurrencyBaseInfoByCode")
    ResponseResult getCurrencyBaseInfoByCode(@RequestParam("code") final String code);

    /**
     * 删除CurrencyBaseInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/base/removeCurrencyBaseInfo")
    ResponseResult removeCurrencyBaseInfo(@RequestParam("id") final Long id);

    /**
     * 按币种删除数据，同时删除币种信息、进展、动态
     *
     * @param code
     * @return
     */
    @PostMapping("/base/removeCurrencyBaseInfoByCode")
    ResponseResult removeCurrencyBaseInfoByCode(@RequestParam("code") final String code);

    /**
     * 保存CurrencyDetailInfo
     *
     * @param currencyDetailInfoDTO
     * @return
     */
    @PostMapping(value = "/detail/saveCurrencyDetailInfo")
    ResponseResult saveCurrencyDetailInfo(@RequestBody final CurrencyDetailInfoDTO currencyDetailInfoDTO);

    /**
     * 更新CurrencyDetailInfo
     *
     * @param currencyDetailInfoDTO
     * @return
     */
    @PostMapping(value = "/detail/updateCurrencyDetailInfo")
    ResponseResult updateCurrencyDetailInfo(@RequestBody final CurrencyDetailInfoDTO currencyDetailInfoDTO);

    /**
     * List CurrencyDetailInfo
     *
     * @return
     */
    @PostMapping(value = "/detail/listCurrencyDetailInfo")
    ResponseResult listCurrencyDetailInfo(@RequestBody final DataGridPager<CurrencyDetailInfoDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/detail/getCurrencyDetailInfoById")
    ResponseResult getCurrencyDetailInfoById(@RequestParam("id") final Long id);

    /**
     * 按照币种编码和语言查询，支持多语言，如果当前语言不支持，默认为en-us
     *
     * @param code
     * @param locale
     * @return
     */
    @GetMapping(value = "/detail/getCurrencyDetailInfoByCode")
    ResponseResult<List<CurrencyDetailInfoDTO>> getCurrencyDetailInfoByCode(@RequestParam(name = "code") final String code,
                                                                            @RequestParam(name = "locale", required = false) final String locale);

    /**
     * 删除CurrencyDetailInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/detail/removeCurrencyDetailInfo")
    ResponseResult removeCurrencyDetailInfo(@RequestParam("id") final Long id);

    /**
     * 查询币种的评级信息
     *
     * @param code   币种简称
     * @param locale 可选为zh-cn、en-us
     * @return
     */
    @GetMapping(value = "/level/getLevelInfo")
    ResponseResult getLevelInfo(@RequestParam("code") final String code, @RequestParam("locale") final String locale);

    /**
     * 查询币种的流通量、流通率等信息
     *
     * @param code 币种简称
     * @return
     */
    @GetMapping(value = "/supply/getSupplyInfo")
    ResponseResult getSupplyInfo(@RequestParam("code") final String code);

    /**
     * 保存CurrencyProgressInfo
     *
     * @param currencyProgressInfoDTO
     * @return
     */
    @PostMapping(value = "/progress-info/saveCurrencyProgressInfo")
    ResponseResult saveCurrencyProgressInfo(@RequestBody final CurrencyProgressInfoDTO currencyProgressInfoDTO);

    /**
     * 更新CurrencyProgressInfo
     *
     * @param currencyProgressInfoDTO
     * @return
     */
    @PostMapping(value = "/progress-info/updateCurrencyProgressInfo")
    ResponseResult updateCurrencyProgressInfo(@RequestBody final CurrencyProgressInfoDTO currencyProgressInfoDTO);

    /**
     * 删除CurrencyProgressInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/progress-info/removeCurrencyProgressInfo")
    ResponseResult removeCurrencyProgressInfo(@RequestParam("id") final Long id);

    /**
     * List CurrencyProgressInfo
     *
     * @return
     */
    @PostMapping(value = "/progress-info/listCurrencyProgressInfo")
    ResponseResult listCurrencyProgressInfo(@RequestBody final DataGridPager<CurrencyProgressInfoDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/progress-info/getCurrencyProgressInfoById")
    ResponseResult getCurrencyProgressInfoById(@RequestParam("id") final Long id);

    /**
     * 保存CurrencyPermission
     *
     * @param currencyPermissionDTO
     * @return
     */
    @PostMapping(value = "/permission/saveCurrencyPermission")
    ResponseResult saveCurrencyPermission(@RequestBody final CurrencyPermissionDTO currencyPermissionDTO);

    /**
     * 更新CurrencyPermission
     *
     * @param currencyPermissionDTO
     * @return
     */
    @PostMapping(value = "/permission/updateCurrencyPermission")
    ResponseResult updateCurrencyPermission(@RequestBody final CurrencyPermissionDTO currencyPermissionDTO);

    /**
     * 删除CurrencyPermission
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/permission/removeCurrencyPermission")
    ResponseResult removeCurrencyPermission(@RequestParam("id") final Long id);

    /**
     * List CurrencyPermission
     *
     * @return
     */
    @PostMapping(value = "/permission/listCurrencyPermission")
    ResponseResult listCurrencyPermission(@RequestBody final DataGridPager<CurrencyPermissionDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/permission/getCurrencyPermissionById")
    ResponseResult getCurrencyPermissionById(@RequestParam("id") final Long id);

    /**
     * 保存CurrencyTrendInfo
     *
     * @param currencyTrendInfoDTO
     * @return
     */
    @PostMapping(value = "/trend-info/saveCurrencyTrendInfo")
    ResponseResult saveCurrencyTrendInfo(@RequestBody final CurrencyTrendInfoDTO currencyTrendInfoDTO);

    /**
     * 更新CurrencyTrendInfo
     *
     * @param currencyTrendInfoDTO
     * @return
     */
    @PostMapping(value = "/trend-info/updateCurrencyTrendInfo")
    ResponseResult updateCurrencyTrendInfo(@RequestBody final CurrencyTrendInfoDTO currencyTrendInfoDTO);

    /**
     * 删除CurrencyTrendInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/trend-info/removeCurrencyTrendInfo")
    ResponseResult removeCurrencyTrendInfo(@RequestParam("id") final Long id);

    /**
     * List CurrencyTrendInfo
     *
     * @return
     */
    @PostMapping(value = "/trend-info/listCurrencyTrendInfo")
    ResponseResult listCurrencyTrendInfo(@RequestBody final DataGridPager<CurrencyTrendInfoDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/trend-info/getCurrencyTrendInfoById")
    ResponseResult getCurrencyTrendInfoById(@RequestParam("id") final Long id);

    /**--币种标签、币对标签相关API--**/
    /**
     * 保存TagCategory
     *
     * @param tagCategoryDTO
     * @return
     */
    @PostMapping(value = "/tag-category/saveTagCategory")
    ResponseResult saveTagCategory(@RequestBody @Valid final TagCategoryDTO tagCategoryDTO);

    /**
     * 更新TagCategory
     *
     * @param tagCategoryDTO
     * @return
     */
    @PostMapping(value = "/tag-category/updateTagCategory")
    ResponseResult updateTagCategory(@RequestBody @Valid final TagCategoryDTO tagCategoryDTO);

    /**
     * 删除TagCategory
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/tag-category/removeTagCategory")
    ResponseResult removeTagCategory(@RequestParam("id") final Long id, @RequestParam("code") final String code);

    /**
     * List TagCategory
     *
     * @return
     */
    @PostMapping(value = "/tag-category/listTagCategory")
    ResponseResult listTagCategory(@RequestBody final DataGridPager<TagCategoryDTO> pager);

    /**
     * List All TagCategory
     *
     * @return
     */
    @GetMapping(value = "/tag-category/listAllTagCategory")
    ResponseResult<List<TagCategoryDTO>> listAllTagCategory();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/tag-category/getTagCategoryById")
    ResponseResult getTagCategoryById(@RequestParam("id") final Long id);

    /**
     * 保存TagInfo
     *
     * @param tagInfoDTO
     * @return
     */
    @PostMapping(value = "/tag-info/saveTagInfo")
    ResponseResult saveTagInfo(@RequestBody @Valid final TagInfoDTO tagInfoDTO);

    /**
     * 更新TagInfo
     *
     * @param tagInfoDTO
     * @return
     */
    @PostMapping(value = "/tag-info/updateTagInfo")
    ResponseResult updateTagInfo(@RequestBody @Valid final TagInfoDTO tagInfoDTO);

    /**
     * 删除TagInfo
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/tag-info/removeTagInfo")
    ResponseResult removeTagInfo(@RequestParam("id") final Long id, @RequestParam("code") final String code);

    /**
     * List TagInfo
     *
     * @return
     */
    @PostMapping(value = "/tag-info/listTagInfo")
    ResponseResult listTagInfo(@RequestBody final DataGridPager<TagInfoDTO> pager);

    /**
     * List All TagInfo
     *
     * @return
     */
    @GetMapping(value = "/tag-info/listAllTagInfo")
    ResponseResult<List<TagInfoDTO>> listAllTagInfo();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/tag-info/getTagInfoById")
    ResponseResult getTagInfoById(@RequestParam("id") final Long id);

    /**
     * 保存CurrencyTag
     *
     * @param currencyTagDTO
     * @return
     */
    @PostMapping(value = "/currency-tag/saveCurrencyTag")
    ResponseResult saveCurrencyTag(@RequestBody final CurrencyTagDTO currencyTagDTO);

    /**
     * 批量保存CurrencyTag
     *
     * @param currencyTagDTOList
     * @return
     */
    @PostMapping(value = "/currency-tag/batchSaveCurrencyTag")
    ResponseResult batchSaveCurrencyTag(@RequestBody final List<CurrencyTagDTO> currencyTagDTOList);

    /**
     * 更新CurrencyTag
     *
     * @param currencyTagDTO
     * @return
     */
    @PostMapping(value = "/currency-tag/updateCurrencyTag")
    ResponseResult updateCurrencyTag(@RequestBody final CurrencyTagDTO currencyTagDTO);

    /**
     * 批量更新CurrencyTag
     *
     * @param currencyTagDTOList
     * @return
     */
    @PostMapping(value = "/currency-tag/batchUpdateCurrencyTag")
    ResponseResult batchUpdateCurrencyTag(@RequestBody final List<CurrencyTagDTO> currencyTagDTOList);

    /**
     * 批量更新CurrencyTag
     *
     * @param currencyTagDTOList
     * @return
     */
    @PostMapping(value = "/currency-tag/batchSaveOrUpdateCurrencyTag")
    ResponseResult batchSaveOrUpdateCurrencyTag(@RequestBody final List<CurrencyTagDTO> currencyTagDTOList);

    /**
     * List CurrencyTag
     *
     * @return
     */
    @PostMapping(value = "/currency-tag/listCurrencyTag")
    ResponseResult listCurrencyTag(@RequestBody final DataGridPager<CurrencyTagDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/currency-tag/getCurrencyTagById")
    ResponseResult getCurrencyTagById(@RequestParam("id") final Long id);

    /**
     * 根据currencyCode查询
     *
     * @param currencyCode
     * @return
     */
    @GetMapping(value = "/currency-tag/getCurrencyTagByCurrencyCode")
    ResponseResult<List<CurrencyTagDTO>> getCurrencyTagByCurrencyCode(@RequestParam("currency") final String currencyCode);

    /**
     * 保存ProductTag
     *
     * @param productTagDTO
     * @return
     */
    @PostMapping(value = "/product-tag/saveProductTag")
    ResponseResult saveProductTag(@RequestBody final ProductTagDTO productTagDTO);

    /**
     * 批量保存ProductTag
     *
     * @param productTagDTOList
     * @return
     */
    @PostMapping(value = "/product-tag/batchSaveProductTag")
    ResponseResult batchSaveProductTag(@RequestBody final List<ProductTagDTO> productTagDTOList);

    /**
     * 更新ProductTag
     *
     * @param productTagDTO
     * @return
     */
    @PostMapping(value = "/product-tag/updateProductTag")
    ResponseResult updateProductTag(@RequestBody final ProductTagDTO productTagDTO);

    /**
     * 批量更新ProductTag
     *
     * @param productTagDTOList
     * @return
     */
    @PostMapping(value = "/product-tag/batchUpdateProductTag")
    ResponseResult batchUpdateProductTag(@RequestBody final List<ProductTagDTO> productTagDTOList);

    /**
     * 批量更新ProductTag
     *
     * @param productTagDTOList
     * @return
     */
    @PostMapping(value = "/product-tag/batchSaveOrUpdateProductTag")
    ResponseResult batchSaveOrUpdateProductTag(@RequestBody final List<ProductTagDTO> productTagDTOList);

    /**
     * List ProductTag
     *
     * @return
     */
    @PostMapping(value = "/product-tag/listProductTag")
    ResponseResult listProductTag(@RequestBody final DataGridPager<ProductTagDTO> pager);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/product-tag/getProductTagById")
    ResponseResult getProductTagById(@RequestParam("id") final Long id);

    /**
     * 查询币种对应的标签，返回树形结构
     *
     * @param currencyCode
     * @return
     */
    @GetMapping(value = "/currency-tag/listTreeNodeByCurrencyCode")
    ResponseResult<List<TreeNodeDTO>> listTreeNodeByCurrencyCode(@RequestParam("currency") final String currencyCode);

    /**
     * 查询币对对应的标签，返回树形结构
     *
     * @param productCode
     * @param type
     * @return
     */
    @GetMapping(value = "/product-tag/listTreeNodeByProductCode")
    ResponseResult<List<TreeNodeDTO>> listTreeNodeByProductCode(@RequestParam("productCode") final String productCode, @RequestParam("type") final Integer type);
}
