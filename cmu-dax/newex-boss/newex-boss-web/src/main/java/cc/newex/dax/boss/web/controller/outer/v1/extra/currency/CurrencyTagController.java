package cc.newex.dax.boss.web.controller.outer.v1.extra.currency;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.web.model.extra.currency.CurrencyTagExtraVO;
import cc.newex.dax.extra.client.ExtraCmsCurrencyAdminClient;
import cc.newex.dax.extra.dto.common.TreeNodeDTO;
import cc.newex.dax.extra.dto.currency.CurrencyTagDTO;
import cc.newex.dax.extra.dto.currency.ProductTagDTO;
import cc.newex.dax.extra.enums.currency.TagCategoryTypeEnum;
import cc.newex.dax.spot.client.SpotCurrencyPairServiceClient;
import cc.newex.dax.spot.dto.ccex.CurrencyPairBaseInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author better
 * @date create in 2018/9/26 上午9:57
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/boss/extra/currency/tag")
public class CurrencyTagController {

    @Autowired
    private ExtraCmsCurrencyAdminClient extraClient;

    @Autowired
    private SpotCurrencyPairServiceClient spotClient;

    @RequestMapping(value = "/editCurrencyTag")
    @OpLog(name = "修改币种的标签")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_TAG_EDIT"})
    public ResponseResult editCurrencyTag(@RequestBody final CurrencyTagExtraVO currencyTagExtraVO) {

        // 币种code
        final String currencyCode = currencyTagExtraVO.getCode();
        // 切割选择的标签
        final String[] tagsArray = StringUtils.split(currencyTagExtraVO.getTags(), ",");
        // 生成币种的标签
        this.saveCurrencyTag(currencyCode, tagsArray);
        // 为币种关联的币对生成对应的标签
        this.saveRelationProductTag(currencyCode, tagsArray);
        return ResultUtils.success();
    }


    @RequestMapping(value = "/currencyTagList")
    @OpLog(name = "根据code获取对应的标签")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_CURRENCY_TAG_VIEW"})
    public ResponseResult<List<TreeNodeDTO>> getCurrencyTags(@RequestParam(value = "code") @NotBlank final String code) {

        return this.extraClient.listTreeNodeByCurrencyCode(code);
    }

    /**
     * 保存币种标签
     *
     * @param currencyCode 币种code
     * @param tagsArray    标签数组
     */
    private void saveCurrencyTag(final String currencyCode, final String[] tagsArray) {
        if (ArrayUtils.isEmpty(tagsArray)) {
            final List<CurrencyTagDTO> removeCurrency = Collections.singletonList(CurrencyTagDTO.builder().code(currencyCode).build());
            this.extraClient.batchSaveOrUpdateCurrencyTag(removeCurrency);
            return;
        }
        final List<CurrencyTagDTO> result = Stream.of(tagsArray)
                .map(tag -> {
                    //切割标签，格式：tagCategoryCode-tagInfoCode，例如：2-123
                    String[] tagArray = StringUtils.split(tag, "/");
                    return CurrencyTagDTO.builder()
                            .code(currencyCode)
                            .tagInfoCode(tagArray[1])
                            .tagCategoryCode(tagArray[0])
                            .createdDate(new Date())
                            .build();
                })
                .collect(Collectors.toList());

        //保存操作
        log.info("save currency tag result => {}", result);
        this.extraClient.batchSaveOrUpdateCurrencyTag(result);
    }

    /**
     * 找到币种关联的币对，将币种的标签关联过去
     *
     * @param currencyCode 币种的code
     * @param tagsArray    标签数组
     */
    private void saveRelationProductTag(final String currencyCode, final String[] tagsArray) {

        // 查询所有的币对信息
        final List<CurrencyPairBaseInfoDTO> currencyPairList = this.spotClient.getCurrencyPairs().getData();
        log.info("get currency pairs result => {}", currencyPairList);
        //生成map,key=币对code,value=币对id
        final Map<String, Integer> currencyPairCodeIdMap = Optional.ofNullable(currencyPairList)
                .map(currencyPairBaseInfoList ->
                        currencyPairBaseInfoList.stream()
                                .filter(currencyPairBaseInfo -> {
                                    // 将币对的code切割
                                    String[] codeArray = StringUtils.split(currencyPairBaseInfo.getCode(), "_");
                                    // 判断是否匹配
                                    return StringUtils.equalsIgnoreCase(codeArray[0], currencyCode);
                                })
                                .collect(Collectors.toMap(CurrencyPairBaseInfoDTO::getCode, CurrencyPairBaseInfoDTO::getId)))
                .orElse(Collections.emptyMap());

        // tags为空, 删除
        if (ArrayUtils.isEmpty(tagsArray)) {
            final List<ProductTagDTO> removeProductTag = currencyPairCodeIdMap.keySet().stream()
                    .map(code -> ProductTagDTO.builder()
                            .productCode(code)
                            .type(TagCategoryTypeEnum.CURRENCY.getCode())
                            .build())
                    .collect(Collectors.toList());

            this.extraClient.batchSaveOrUpdateProductTag(removeProductTag);
            return;
        }

        //构建保存的ProductTagList
        final List<ProductTagDTO> result = currencyPairCodeIdMap.keySet().stream()
                .flatMap(code ->
                        Stream.of(tagsArray).map(tag ->
                        {
                            //切割标签，格式：tagCategoryCode/tagInfoCode，例如：2/123
                            String[] tagArray = StringUtils.split(tag, "/");
                            //构建币种的ProductTag对象
                            return ProductTagDTO.builder()
                                    .currencyCode(currencyCode)
                                    .tagInfoCode(tagArray[1])
                                    .tagCategoryCode(tagArray[0])
                                    .productCode(code)
                                    .productId(currencyPairCodeIdMap.get(code).longValue())
                                    .type(TagCategoryTypeEnum.CURRENCY.getCode())
                                    .createdDate(new Date())
                                    .build();
                        })
                )
                .collect(Collectors.toList());

        log.info("relation product tag result => {}", result);
        if (!CollectionUtils.isEmpty(result)) {
            this.extraClient.batchSaveOrUpdateProductTag(result);
        }
    }
}
