package cc.newex.dax.boss.web.controller.outer.v1.spot;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.util.DateFormater;
import cc.newex.dax.boss.web.model.spot.ProductTagVO;
import cc.newex.dax.boss.web.model.spot.ProductVO;
import cc.newex.dax.extra.client.ExtraCmsCurrencyAdminClient;
import cc.newex.dax.extra.dto.common.TreeNodeDTO;
import cc.newex.dax.extra.dto.currency.CurrencyTagDTO;
import cc.newex.dax.extra.dto.currency.ProductTagDTO;
import cc.newex.dax.extra.enums.currency.TagCategoryTypeEnum;
import cc.newex.dax.spot.client.SpotCurrencyBrokerRelationClient;
import cc.newex.dax.spot.client.SpotCurrencyPairServiceClient;
import cc.newex.dax.spot.client.SpotOrderClient;
import cc.newex.dax.spot.dto.ccex.CurrencyPairBrokerRelationDTO;
import cc.newex.dax.spot.dto.ccex.CurrencyPairDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Product controller.
 *
 * @author newex -jinlong
 * @date 2018 -04-28
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/spot/product")
public class ProductController {

    @Autowired
    private SpotOrderClient orderClient;

    @Autowired
    private SpotCurrencyPairServiceClient spotClient;

    @Autowired
    private SpotCurrencyBrokerRelationClient brokerClient;

    @Autowired
    private ExtraCmsCurrencyAdminClient extraClient;

    /**
     * List response result.
     *
     * @param pager         the pager
     * @param baseCurrency  the base currency
     * @param quoteCurrency the quote currency
     * @param status        the status
     * @param marginOpen    the margin open
     * @return the response result
     */
    @GetMapping(value = "/searchlist")
    @OpLog(name = "搜索分页获取币对列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_PRODUCT_VIEW"})
    public ResponseResult list(final DataGridPager pager,
                               @RequestParam(value = "baseCurrency", required = false) final Integer[] baseCurrency,
                               @RequestParam(value = "quoteCurrency", required = false) final Integer[] quoteCurrency,
                               @RequestParam(value = "status", required = false) final Byte[] status,
                               @RequestParam(value = "marginOpen", required = false) final Boolean marginOpen) {

        final ResponseResult<DataGridPagerResult<CurrencyPairDTO>> result = this.spotClient.getCurrencyPairs(pager, baseCurrency, quoteCurrency, status, marginOpen);

        if (result == null || result.getCode() != 0 || result.getData() == null) {
            return ResultUtil.getDataGridResult();
        }

        final Long total = result.getData().getTotal();
        final List<CurrencyPairDTO> rows = result.getData().getRows();

        final ModelMapper mapper = new ModelMapper();

        final List<ProductVO> listVo = mapper.map(rows, new TypeToken<List<ProductVO>>() {
        }.getType());

        listVo.forEach(v -> {
            v.setBrokerIds(this.getBrokerIds(v.getId().longValue()));
        });

        final DataGridPagerResult dataGridPagerResult = new DataGridPagerResult(Long.valueOf(total), listVo);

        return ResultUtils.success(dataGridPagerResult);
    }

    private Date getExpireDate(final ProductVO productVO) throws Exception {
        if (productVO == null) {
            return null;
        }

        // 是否显示过期时间,1:显示，0:不显示
        final Integer status = productVO.getStatus();

        // 过期时间
        final String expireDate = productVO.getExpireDate();

        if (Objects.isNull(status) || status.equals(0)) {
            productVO.setStatus(0);

            return null;
        }

        if (StringUtils.isBlank(expireDate)) {
            throw new Exception("error data");
        }

        return DateFormater.parse(expireDate, "yyyy-MM-dd");
    }

    /**
     * Add response result.
     *
     * @param productVO the product vo
     * @return the response result
     */
    @RequestMapping(value = "/add")
    @OpLog(name = "新增币对")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_PRODUCT_ADD"})
    public ResponseResult add(@Valid final ProductVO productVO) {
        Date expireDate = null;
        try {
            expireDate = this.getExpireDate(productVO);
        } catch (final Exception e) {
            return ResultUtils.failure(BizErrorCodeEnum.ADD_ERROR);
        }

        final CurrencyPairDTO currencyPairDTO = CurrencyPairDTO.builder()
                .baseCurrency(productVO.getBaseCurrency())
                .quoteCurrency(productVO.getQuoteCurrency())
                .minTradeSize(productVO.getMinTradeSize())
                .maxPriceDigit(productVO.getMaxPriceDigit())
                .maxSizeDigit(productVO.getMaxSizeDigit())
                .quotePrecision(productVO.getQuotePrecision())
                .quoteIncrement(productVO.getQuoteIncrement())
                .baseIncrement(productVO.getBaseIncrement())
                .isMarginOpen(productVO.isMarginOpen())
                .maxMarginLeverage(productVO.getMaxMarginLeverage())
                .marginRiskPreRatio(productVO.getMarginRiskPreRatio())
                .marginRiskRatio(productVO.getMarginRiskRatio())
                .sort(productVO.getSort())
                .online(productVO.getOnline())
                .lastPrice(productVO.getLastPrice())
                .name(productVO.getName())
                .mergeType(productVO.getMergeType())
                .makerRate(productVO.getMakerRate())
                .tickerRate(productVO.getTickerRate())
                .baseLimit(productVO.getBaseLimit())
                .baseUserLimit(productVO.getBaseUserLimit())
                .quoteLimit(productVO.getQuoteLimit())
                .quoteUserLimit(productVO.getQuoteUserLimit())
                .baseInterestRate(productVO.getBaseInterestRate())
                .quoteInterestRate(productVO.getQuoteInterestRate())
                .zone(productVO.getZone())
                .status(productVO.getStatus())
                .expireDate(expireDate)
                .build();

        log.info("add product info => {}", productVO);
        final ResponseResult result = this.spotClient.addCurrencyPair(currencyPairDTO);

        if (result.getCode() != 0 || result.getData() == null) {
            return ResultUtils.failure("add product error");
        }
        final CurrencyPairDTO currencyPair = this.spotClient.getCurrencyPair((Integer) result.getData()).getData();
        // 将币种的标签关联过来
        this.saveRelationCurrencyTag(productVO.getBaseCurrencyCode(), currencyPair.getCode(), currencyPair.getId());
        this.setupBrokerIds(currencyPair.getId(), productVO.getBrokerIds());
        return ResultUtil.getCheckedResponseResult(result);
    }

    /**
     * Edit response result.
     *
     * @param productVO the product vo
     * @param id        the id
     * @return the response result
     */
    @RequestMapping(value = "/edit")
    @OpLog(name = "修改币对")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_PRODUCT_EDIT"})
    public ResponseResult edit(@Valid final ProductVO productVO, @RequestParam(value = "id") final Integer id) {
        Date expireDate = null;
        try {
            expireDate = this.getExpireDate(productVO);
        } catch (final Exception e) {
            return ResultUtils.failure(BizErrorCodeEnum.UPDATE_ERROR);
        }

        final CurrencyPairDTO currencyPairDTO = CurrencyPairDTO.builder()
                .id(id)
                .baseCurrency(productVO.getBaseCurrency())
                .quoteCurrency(productVO.getQuoteCurrency())
                .minTradeSize(productVO.getMinTradeSize())
                .maxPriceDigit(productVO.getMaxPriceDigit())
                .maxSizeDigit(productVO.getMaxSizeDigit())
                .quotePrecision(productVO.getQuotePrecision())
                .quoteIncrement(productVO.getQuoteIncrement())
                .baseIncrement(productVO.getBaseIncrement())
                .isMarginOpen(productVO.isMarginOpen())
                .maxMarginLeverage(productVO.getMaxMarginLeverage())
                .marginRiskPreRatio(productVO.getMarginRiskPreRatio())
                .marginRiskRatio(productVO.getMarginRiskRatio())
                .sort(productVO.getSort())
                .online(productVO.getOnline())
                .lastPrice(productVO.getLastPrice())
                .name(productVO.getName())
                .mergeType(productVO.getMergeType())
                .makerRate(productVO.getMakerRate())
                .tickerRate(productVO.getTickerRate())
                .baseLimit(productVO.getBaseLimit())
                .baseUserLimit(productVO.getBaseUserLimit())
                .quoteLimit(productVO.getQuoteLimit())
                .quoteUserLimit(productVO.getQuoteUserLimit())
                .baseInterestRate(productVO.getBaseInterestRate())
                .quoteInterestRate(productVO.getQuoteInterestRate())
                .zone(productVO.getZone())
                .status(productVO.getStatus())
                .expireDate(expireDate)
                .build();

        final ResponseResult result = this.spotClient.updateCurrencyPair(currencyPairDTO);

        if (result.getCode() != 0) {
            return ResultUtils.failure("edit product error");
        }
        this.setupBrokerIds(id, productVO.getBrokerIds());
        return ResultUtil.getCheckedResponseResult(result);
    }

    /**
     * Edit product tag response result.
     *
     * @param productTagVO the product tag vo
     * @return the response result
     */
    @PostMapping(value = "/editProductTag")
    @OpLog(name = "修改币对的标签")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_PRODUCT_TAG_EDIT"})
    public ResponseResult<?> editProductTag(@RequestBody final ProductTagVO productTagVO) {

        final String[] tagsArray = StringUtils.split(productTagVO.getTags(), ",");
        // 为null, 删除对应的币对标签
        if (ArrayUtils.isEmpty(tagsArray)) {
            final List<ProductTagDTO> removeProductTag = Collections.singletonList(
                    ProductTagDTO.builder()
                            .productCode(productTagVO.getCode())
                            .type(TagCategoryTypeEnum.PRODUCT.getCode())
                            .build());
            this.extraClient.batchSaveOrUpdateProductTag(removeProductTag);
            return ResultUtils.success();
        }

        final List<ProductTagDTO> productTagList = Stream.of(tagsArray)
                .map(tag -> {
                    String[] tagArray = StringUtils.split(tag, "/");
                    return ProductTagDTO.builder()
                            .productId(productTagVO.getId())
                            .productCode(productTagVO.getCode())
                            .type(TagCategoryTypeEnum.PRODUCT.getCode())
                            .tagInfoCode(tagArray[1])
                            .currencyCode(productTagVO.getCurrencyCode())
                            .tagCategoryCode(tagArray[0])
                            .createdDate(new Date())
                            .build();
                })
                .collect(Collectors.toList());

        final ResponseResult result = this.extraClient.batchSaveOrUpdateProductTag(productTagList);
        return ResultUtil.getCheckedResponseResult(result);
    }

    /**
     * Gets product tag tree.
     *
     * @param code the code
     * @return the product tag tree
     */
    @GetMapping(value = "/productTagTree")
    @OpLog(name = "获取币对的标签列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_PRODUCT_TAG_VIEW"})
    public ResponseResult<List<TreeNodeDTO>> getProductTagTree(@RequestParam("code") @NotBlank final String code) {

        return this.extraClient.listTreeNodeByProductCode(code, TagCategoryTypeEnum.PRODUCT.getCode());
    }

    /**
     * 将币对的币种的标签关联过来
     *
     * @param baseCurrencyCode 币种code
     */
    private void saveRelationCurrencyTag(final String baseCurrencyCode, final String productCode, final Integer productId) {

        // 拿到币种关联：标签-标签分类
        final List<CurrencyTagDTO> currencyTags = this.extraClient.getCurrencyTagByCurrencyCode(baseCurrencyCode).getData();
        final List<ProductTagDTO> productTags = Optional.ofNullable(currencyTags)
                .map(result -> result.stream()
                        .map(currencyTagDTO -> ProductTagDTO.builder()
                                .productId(productId.longValue())
                                .productCode(productCode)
                                .type(TagCategoryTypeEnum.CURRENCY.getCode())
                                .tagInfoCode(currencyTagDTO.getTagInfoCode())
                                .currencyCode(baseCurrencyCode)
                                .tagCategoryCode(currencyTagDTO.getTagCategoryCode())
                                .createdDate(new Date())
                                .build())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        log.info("create product info, save relation currency tag result => {}", productTags);
        if (!CollectionUtils.isEmpty(productTags)) {
            this.extraClient.batchSaveOrUpdateProductTag(productTags);
        }
    }

    private void setupBrokerIds(final Integer productId, final List<Integer> brokerIds) {

        final List<CurrencyPairBrokerRelationDTO> list = new ArrayList<>();
        brokerIds.forEach(v -> {
            final CurrencyPairBrokerRelationDTO dto = CurrencyPairBrokerRelationDTO.builder()
                    .brokerId(v)
                    .productId(productId.longValue())
                    .createDate(new Date()).build();
            list.add(dto);
        });
        // TODO: 2018/10/24 修改不了券商和币种之间的关系
        this.brokerClient.setup(list);
    }

    private List<Integer> getBrokerIds(final Long productId) {
        final ResponseResult<List<CurrencyPairBrokerRelationDTO>> result = this.brokerClient.get(productId);
        return result.getData().stream().map(CurrencyPairBrokerRelationDTO::getBrokerId).collect(Collectors.toList());
    }

    /**
     * 批量撤单
     *
     * @param code
     * @param userId
     * @param brokerId
     * @return
     */
    @PostMapping("/batchCancel")
    @OpLog(name = "批量撤单")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SPOT_PRODUCT_BATCH_CANCEL"})
    public ResponseResult batchCancel(@RequestParam(value = "cancelCode") final String code,
                                      @RequestParam(value = "userId") final Long userId,
                                      @RequestParam(value = "brokerId") final Integer brokerId,
                                      final HttpServletRequest request) {

        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);

        log.info("batch cancel, adminId: {}, code: {}, userId: {}, brokerId: {}", user.getId(), code, userId, brokerId);

        final ResponseResult result = this.orderClient.batchCancel(code, userId, brokerId);

        return ResultUtil.getCheckedResponseResult(result);
    }

}
