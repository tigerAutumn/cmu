package cc.newex.dax.extra.rest.controller.outer.v1.common.currency;

import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.html.HTMLDecoder;
import cc.newex.dax.extra.common.thread.SimpleThreadPool;
import cc.newex.dax.extra.common.util.SimpleLocaleUtils;
import cc.newex.dax.extra.criteria.currency.CurrencyBaseInfoExample;
import cc.newex.dax.extra.criteria.currency.CurrencyTrendInfoExample;
import cc.newex.dax.extra.domain.currency.*;
import cc.newex.dax.extra.domain.tokenin.CurrencyLevelInfo;
import cc.newex.dax.extra.domain.tokenin.CurrencySupplyInfo;
import cc.newex.dax.extra.dto.common.TreeNodeDTO;
import cc.newex.dax.extra.dto.currency.*;
import cc.newex.dax.extra.enums.currency.CurrencyPairOnlineEnum;
import cc.newex.dax.extra.enums.currency.CurrencyPairZoneEnum;
import cc.newex.dax.extra.enums.currency.CurrencyStatusEnum;
import cc.newex.dax.extra.rest.model.page.front.FrontPagerResult;
import cc.newex.dax.extra.rest.model.tree.TreeNodeVO;
import cc.newex.dax.extra.service.admin.currency.CurrencyTagAdminService;
import cc.newex.dax.extra.service.admin.currency.ProductTagAdminService;
import cc.newex.dax.extra.service.admin.currency.TagCategoryAdminService;
import cc.newex.dax.extra.service.admin.currency.TokenInsightAdminService;
import cc.newex.dax.extra.service.currency.CurrencyBaseInfoService;
import cc.newex.dax.extra.service.currency.CurrencyDetailInfoService;
import cc.newex.dax.extra.service.currency.CurrencyProgressInfoService;
import cc.newex.dax.extra.service.currency.CurrencyTrendInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.Future;

/**
 * 币种wiki
 *
 * @author liutiejun
 * @date 2018-08-20
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/v1/extra/public/currency")
public class CurrencyInfoPublicController {

    @Autowired
    private TokenInsightAdminService tokenInsightAdminService;

    @Autowired
    private CurrencyBaseInfoService currencyBaseInfoService;

    @Autowired
    private CurrencyDetailInfoService currencyDetailInfoService;

    @Autowired
    private CurrencyProgressInfoService currencyProgressInfoService;

    @Autowired
    private CurrencyTrendInfoService currencyTrendInfoService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private TagCategoryAdminService tagCategoryAdminService;

    @Autowired
    private ProductTagAdminService productTagAdminService;

    @Autowired
    private CurrencyTagAdminService currencyTagAdminService;

    /**
     * 查询币种的评级信息
     *
     * @param code    币种简称
     * @param request
     * @return
     */
    @GetMapping(value = "/level-info")
    public ResponseResult getLevelInfo(@RequestParam("code") @NotBlank final String code, final HttpServletRequest request) {
        // 可选为zh-cn、en-us
        final String locale = SimpleLocaleUtils.getLocaleByDefault(request, Locale.US.toLanguageTag().toLowerCase());

        CurrencyLevelInfo currencyLevelInfo = this.tokenInsightAdminService.getLevelInfo(code, locale);

        if (currencyLevelInfo == null) {
            // 方便移动端处理
            currencyLevelInfo = new CurrencyLevelInfo();
        }

        return ResultUtils.success(currencyLevelInfo);
    }

    /**
     * 查看币种信息
     *
     * @param code    币种简称
     * @param request
     * @return
     */
    @GetMapping(value = "/all-info")
    public ResponseResult getAllInfo(@RequestParam("code") @NotBlank final String code, final HttpServletRequest request) {
        final String locale = SimpleLocaleUtils.getLocaleByDefault(request, Locale.US.toLanguageTag().toLowerCase());

        final Future<CurrencyBaseInfo> baseInfoFuture = this.getBaseInfo(code);
        final Future<CurrencyDetailInfo> detailInfoFuture = this.getDetailInfo(code, locale);
        final Future<CurrencyLevelInfo> levelInfoFuture = this.getLevelInfo(code, locale);
        final Future<List<CurrencyProgressInfo>> progressInfosFuture = this.getProgressInfo(code, locale);

        CurrencyBaseInfo currencyBaseInfo = null;
        CurrencyDetailInfo currencyDetailInfo = null;
        CurrencyLevelInfo currencyLevelInfo = null;
        List<CurrencyProgressInfo> currencyProgressInfos = null;
        try {
            currencyBaseInfo = baseInfoFuture.get();
            currencyDetailInfo = detailInfoFuture.get();
            currencyLevelInfo = levelInfoFuture.get();
            currencyProgressInfos = progressInfosFuture.get();
        } catch (final Exception e) {
            log.warn(e.getMessage(), e);
        }

        final ModelMapper mapper = new ModelMapper();

        CurrencyBaseInfoDTO currencyBaseInfoDTO = null;
        if (currencyBaseInfo != null) {
            currencyBaseInfoDTO = mapper.map(currencyBaseInfo, CurrencyBaseInfoDTO.class);

            final String imgUrl = this.fileUploadService.getUrl(currencyBaseInfoDTO.getImgUrl());
            currencyBaseInfoDTO.setImgUrl(imgUrl);
        }

        CurrencyDetailInfoDTO currencyDetailInfoDTO = null;
        if (currencyDetailInfo != null) {
            currencyDetailInfoDTO = mapper.map(currencyDetailInfo, CurrencyDetailInfoDTO.class);

//            String concept = currencyDetailInfoDTO.getConcept();
//            concept = HTMLDecoder.decode(concept);
//
//            String introduction = currencyDetailInfoDTO.getIntroduction();
//            introduction = HTMLDecoder.decode(introduction);
//            introduction = StringUtils.replace(introduction, "\r", "<br />");
//            introduction = StringUtils.replace(introduction, "\n", "<br />");
//
//            currencyDetailInfoDTO.setConcept(concept);
//            currencyDetailInfoDTO.setIntroduction(introduction);
        }

        CurrencyLevelInfoDTO currencyLevelInfoDTO = null;
        if (currencyLevelInfo != null) {
            currencyLevelInfo.setTokeninsightID(currencyLevelInfo.getAbbreviate());
            currencyLevelInfo.setLanguage(locale);

            currencyLevelInfoDTO = mapper.map(currencyLevelInfo, CurrencyLevelInfoDTO.class);
        }

        List<CurrencyProgressInfoDTO> currencyProgressInfoDTOS = null;
        if (currencyProgressInfos != null) {
            currencyProgressInfoDTOS = mapper.map(
                    currencyProgressInfos, new TypeToken<List<CurrencyProgressInfoDTO>>() {
                    }.getType()
            );
        }

        final CurrencyAllInfoDTO currencyAllInfoDTO = CurrencyAllInfoDTO.builder()
                .baseInfo(currencyBaseInfoDTO)
                .detailInfo(currencyDetailInfoDTO)
                .levelInfo(currencyLevelInfoDTO)
                .progressInfos(currencyProgressInfoDTOS)
                .build();

        return ResultUtils.success(currencyAllInfoDTO);
    }

    private Future<CurrencyBaseInfo> getBaseInfo(final String code) {
        final Future<CurrencyBaseInfo> future = SimpleThreadPool.submit(() -> this.currencyBaseInfoService.getByCode(code));

        return future;
    }

    private Future<CurrencyDetailInfo> getDetailInfo(final String code, final String locale) {
        final Future<CurrencyDetailInfo> future = SimpleThreadPool.submit(() -> this.currencyDetailInfoService.getByCode(code, locale));

        return future;
    }

    private Future<CurrencyLevelInfo> getLevelInfo(final String code, final String locale) {
        final Future<CurrencyLevelInfo> future = SimpleThreadPool.submit(() -> this.tokenInsightAdminService.getLevelInfo(code, locale));

        return future;
    }

    private Future<CurrencySupplyInfo> getSupplyInfo(final String code) {
        final Future<CurrencySupplyInfo> future = SimpleThreadPool.submit(() -> this.tokenInsightAdminService.getSupplyInfo(code));

        return future;
    }

    private Future<List<CurrencyProgressInfo>> getProgressInfo(final String code, final String locale) {
        final Future<List<CurrencyProgressInfo>> future = SimpleThreadPool.submit(() -> this.currencyProgressInfoService.getByCode(code, locale));

        return future;
    }

    /**
     * 查看项目进展
     *
     * @param code    币种简称
     * @param request
     * @return
     */
    @GetMapping(value = "/progress-info")
    public ResponseResult getProgressInfo(@RequestParam("code") @NotBlank final String code, final HttpServletRequest request) {
        final String locale = SimpleLocaleUtils.getLocaleByDefault(request, Locale.US.toLanguageTag().toLowerCase());

        final ModelMapper mapper = new ModelMapper();

        final List<CurrencyProgressInfo> list = this.currencyProgressInfoService.getByCode(code, locale);
        final List<CurrencyProgressInfoDTO> currencyProgressInfoDTOS = mapper.map(
                list, new TypeToken<List<CurrencyProgressInfoDTO>>() {
                }.getType()
        );

        return ResultUtils.success(currencyProgressInfoDTOS);
    }

    /**
     * 查看项目动态，分页查询
     *
     * @param code    币种简称
     * @param request
     * @return
     */
    @GetMapping(value = "/trend-info")
    public ResponseResult getTrendInfo(@RequestParam("pageIndex") @NotNull @Min(1) final Integer pageIndex,
                                       @RequestParam("pageSize") @NotNull @Min(1) final Integer pageSize,
                                       @RequestParam("code") @NotBlank final String code, final HttpServletRequest request) {
        final PageInfo pageInfo = new PageInfo((pageIndex - 1) * pageSize, pageSize, "publish_date", PageInfo.SORT_TYPE_DES);

        final String locale = SimpleLocaleUtils.getLocaleByDefault(request, Locale.US.toLanguageTag().toLowerCase());

        final CurrencyTrendInfoExample example = new CurrencyTrendInfoExample();
        example.createCriteria()
                .andCodeEqualTo(code)
                .andLocaleEqualTo(locale)
                .andStatusEqualTo(CurrencyStatusEnum.PUBLISH.getCode());

        final ModelMapper mapper = new ModelMapper();

        final List<CurrencyTrendInfo> list = this.currencyTrendInfoService.getByPage(pageInfo, example);
        final List<CurrencyTrendInfoDTO> currencyTrendInfoDTOS = mapper.map(
                list, new TypeToken<List<CurrencyTrendInfoDTO>>() {
                }.getType()
        );

        final Long totalItemCount = pageInfo.getTotals();

        final FrontPagerResult<CurrencyTrendInfoDTO> frontPagerResult = new FrontPagerResult<>(currencyTrendInfoDTOS, pageIndex, pageSize, totalItemCount);

        return ResultUtils.success(frontPagerResult);
    }

    /**
     * 查看币种列表
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseResult list(final HttpServletRequest request) {
        final CurrencyBaseInfoExample example = new CurrencyBaseInfoExample();
        example.setOrderByClause("sort asc");
        example.createCriteria().andStatusEqualTo(CurrencyStatusEnum.PUBLISH.getCode());

        final ModelMapper mapper = new ModelMapper();

        final List<CurrencyBaseInfo> currencyBaseInfoList = this.currencyBaseInfoService.getByExample(example);

        List<CurrencyBaseInfoDTO> currencyBaseInfoDTOS = null;
        if (currencyBaseInfoList != null) {
            currencyBaseInfoDTOS = mapper.map(currencyBaseInfoList, new TypeToken<List<CurrencyBaseInfoDTO>>() {
            }.getType());
        }

        return ResultUtils.success(currencyBaseInfoDTOS);
    }

    /**
     * 查看所有的标签
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/list/tag")
    public ResponseResult listTag(final HttpServletRequest request) {
        final String locale = SimpleLocaleUtils.getLocale(request);
        //Cookie[] cookies =  request.getCookies();
        //final Cookie cookie = Arrays.asList(cookies).stream().filter(a->a.getName().equals("locale")).findFirst().get();
        //final String locale = cookie.getValue();
        final List<TreeNodeDTO> treeNodeList = this.tagCategoryAdminService.listTreeNode(locale, null);

        final ModelMapper modelMapper = new ModelMapper();

        final List<TreeNodeVO> treeNodeVOList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(treeNodeList)) {
            treeNodeList.forEach(treeNode -> {
                final TreeNodeVO treeNodeVO = modelMapper.map(treeNode, TreeNodeVO.class);
                treeNodeVO.setText(HTMLDecoder.decode(treeNode.getText()));
                treeNodeVO.setTotal(0);

                treeNodeVOList.add(treeNodeVO);
            });

            treeNodeVOList.forEach(node -> {
                final List<TreeNodeVO> children = node.getChildren();

                if (CollectionUtils.isNotEmpty(children)) {
                    children.forEach(leaf -> {
                        final int count = this.productTagAdminService.countByTagInfo(leaf.getId(), CurrencyPairOnlineEnum.ONLINE.getCode(), CurrencyPairZoneEnum.MAIN.getCode());
                        leaf.setText(HTMLDecoder.decode(leaf.getText()));
                        leaf.setTotal(count);
                    });
                }
            });
        }

        return ResultUtils.success(treeNodeVOList);
    }

    /**
     * 根据标签搜索对应的币对
     *
     * @param tags
     * @param request
     * @return
     */
    @GetMapping(value = "/search/tag")
    public ResponseResult searchTag(final String[] tags, final HttpServletRequest request) {
        final List<String> tagList = new ArrayList<>();

        if (ArrayUtils.isNotEmpty(tags)) {
            Arrays.stream(tags).forEach(tag -> tagList.add(tag));
        }

        final List<ProductTag> productTagList = this.productTagAdminService.getByTagInfo(tagList);

        final Map<String, Long> productMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(productTagList)) {
            productTagList.stream().forEach(productTag -> {
                final Long productId = productTag.getProductId();
                final String productCode = productTag.getProductCode();

                productMap.put(productCode, productId);
            });
        }

        return ResultUtils.success(productMap.keySet());
    }

}
