package cc.newex.dax.extra.service.wiki.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.extra.common.enums.RtCurrencyTypeEnum;
import cc.newex.dax.extra.common.enums.RtSocialCategoryEnum;
import cc.newex.dax.extra.domain.currency.CurrencyBaseInfo;
import cc.newex.dax.extra.domain.currency.CurrencyDetailInfo;
import cc.newex.dax.extra.domain.currency.CurrencyTrendInfo;
import cc.newex.dax.extra.domain.tokenin.CurrencyLevelInfo;
import cc.newex.dax.extra.domain.wiki.RtCurrencyDetail;
import cc.newex.dax.extra.domain.wiki.RtCurrencyRank;
import cc.newex.dax.extra.domain.wiki.RtCurrencyScore;
import cc.newex.dax.extra.dto.wiki.RtCurrencyBaseInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyDynamicInfoDTO.DynamicInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyProjectInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyProjectInfoDTO.ExchangeChartsDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyProjectInfoDTO.GithubChartsDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyProjectInfoDTO.IntroductionDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyProjectInfoDTO.ProgressDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencyProjectInfoDTO.ScoreInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencySocialInfoDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencySocialInfoDTO.CommunityChartsDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencySocialInfoDTO.SentimentCommunityChartsDTO;
import cc.newex.dax.extra.dto.wiki.RtCurrencySocialInfoDTO.SentimentScoreDTO;
import cc.newex.dax.extra.enums.currency.CurrencyStatusEnum;
import cc.newex.dax.extra.service.admin.currency.TokenInsightAdminService;
import cc.newex.dax.extra.service.currency.CurrencyBaseInfoService;
import cc.newex.dax.extra.service.currency.CurrencyDetailInfoService;
import cc.newex.dax.extra.service.currency.CurrencyProgressInfoService;
import cc.newex.dax.extra.service.currency.CurrencyTrendInfoService;
import cc.newex.dax.extra.service.wiki.CurrencyWikiService;
import cc.newex.dax.extra.service.wiki.RatingTokenService;
import cc.newex.dax.extra.service.wiki.RtCurrencyDetailService;
import cc.newex.dax.extra.service.wiki.RtCurrencyRankService;
import cc.newex.dax.extra.service.wiki.RtCurrencyScoreService;
import cc.newex.dax.extra.service.wiki.RtCurrencyTeamService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Currency wiki service.
 *
 * @author better
 * @date create in 2018/11/27 6:38 PM
 */
@Service
@Slf4j
public class CurrencyWikiServiceImpl implements CurrencyWikiService {

    private final RtCurrencyDetailService rtDetailService;
    private final RtCurrencyRankService rtRankService;
    private final RtCurrencyTeamService rtTeamService;
    private final RtCurrencyScoreService rtScoreService;
    private final CurrencyBaseInfoService baseInfoService;
    private final CurrencyDetailInfoService detailInfoService;
    private final CurrencyProgressInfoService progressInfoService;
    private final TokenInsightAdminService tokenInsightAdminService;
    private final ModelMapper modelMapper;
    private final CurrencyTrendInfoService trendInfoService;
    private final RatingTokenService ratingTokenService;


    /**
     * Instantiates a new Currency wiki service.
     *
     * @param rtDetailService          the detail service
     * @param rtRankService            the rank service
     * @param rtTeamService            the team service
     * @param rtScoreService
     * @param baseInfoService          the base info service
     * @param detailInfoService        the detail info service
     * @param progressInfoService      the progress info service
     * @param tokenInsightAdminService the token insight admin service
     * @param modelMapper              the model mapper
     * @param trendInfoService         the trend info service
     * @param ratingTokenService       the rating token service
     */
    @Autowired
    public CurrencyWikiServiceImpl(final RtCurrencyDetailService rtDetailService, final RtCurrencyRankService rtRankService, final RtCurrencyTeamService rtTeamService,
                                   final RtCurrencyScoreService rtScoreService, final CurrencyBaseInfoService baseInfoService, final CurrencyDetailInfoService detailInfoService,
                                   final CurrencyProgressInfoService progressInfoService, final TokenInsightAdminService tokenInsightAdminService, final ModelMapper modelMapper,
                                   final CurrencyTrendInfoService trendInfoService, final RatingTokenService ratingTokenService) {
        this.rtDetailService = rtDetailService;
        this.rtRankService = rtRankService;
        this.rtTeamService = rtTeamService;
        this.rtScoreService = rtScoreService;
        this.baseInfoService = baseInfoService;
        this.detailInfoService = detailInfoService;
        this.progressInfoService = progressInfoService;
        this.tokenInsightAdminService = tokenInsightAdminService;
        this.modelMapper = modelMapper;
        this.trendInfoService = trendInfoService;
        this.ratingTokenService = ratingTokenService;
    }

    /**
     * Gets rt currency by condition.
     *
     * @param currencyCode the currency name
     * @param type         the type
     * @return the rt currency by condition
     */
    @Override
    public RtCurrencyInfoDTO getRtCurrencyByCondition(final String currencyCode, final String locale, final RtCurrencyTypeEnum type) {

        switch (type) {
            case BASE:
                return this.getCurrencyBaseInfo(currencyCode, locale);
            case PROJECT:
                return this.getCurrencyProjectInfo(currencyCode, locale);
            default:
                throw new IllegalArgumentException("type is error, source => " + type);
        }
    }

    @Override
    public RtCurrencySocialInfoDTO getCurrencySocialInfo(final String currencyCode, final RtSocialCategoryEnum category, final String platform) {

        final RtCurrencyRank rtRank = this.rtRankService.getCidByCurrencyCode(currencyCode);
        if (Objects.isNull(rtRank)) {
            log.warn("CurrencyLevelInfo is not exists with currencyCode=>{}", currencyCode);
            return new RtCurrencySocialInfoDTO();
        }
        final SentimentScoreDTO sentimentScore = this.buildEmotionScore(rtRank.getCid());
        switch (category) {
            case ALL:
                return new RtCurrencySocialInfoDTO(this.buildCommunityCharts(rtRank.getCid(), platform), sentimentScore, this.buildSentimentCommunityCharts(rtRank.getCid(),
                        platform));
            case EMOTION:
                return new RtCurrencySocialInfoDTO(null, sentimentScore, this.buildSentimentCommunityCharts(rtRank.getCid(), platform));
            case COMMUNITY:
                return new RtCurrencySocialInfoDTO(this.buildCommunityCharts(rtRank.getCid(), platform), sentimentScore, null);
            default:
                throw new IllegalArgumentException("category is error, category = " + category);
        }
    }

    @Override
    public List<DynamicInfoDTO> getCurrencyDynamicPageInfo(final String currencyCode, final String locale, final PageInfo pageInfo) {
        return this.buildDynamicInfos(currencyCode, locale, pageInfo);
    }

    /**
     * 获取wiki中币种的基础信息
     *
     * @param currencyCode 币对名称
     * @return currency base info
     */
    @Override
    public RtCurrencyBaseInfoDTO getCurrencyBaseInfo(final String currencyCode, final String locale) {
        // 1.获取Rt(第三方)的币种排名
        final RtCurrencyRank rtRank = this.rtRankService.getCidByCurrencyCode(currencyCode);

        // 2.获取自己的币种基础信息
        final CurrencyBaseInfo baseInfo = this.baseInfoService.getByCode(currencyCode);

        // 3.获取自己的币种详细信息
        final CurrencyDetailInfo detailInfo = this.detailInfoService.getByCode(currencyCode, locale);

        if (Objects.isNull(rtRank)) {
            return this.buildDefaultBaseInfo(baseInfo, detailInfo);
        }

        // 3.第三方存在,获取其详细信息
        final RtCurrencyDetail rtDetail = this.rtDetailService.getDetailByCid(rtRank.getCid());

        // 4.组装数据,有限级: 第三方 > 自己
        return this.buildAndMergeCurrencyBaseInfo(baseInfo, rtDetail, rtRank, detailInfo);
    }

    /**
     * 构建默认的基础信息
     *
     * @param baseInfo
     * @return
     */
    private RtCurrencyBaseInfoDTO buildDefaultBaseInfo(final CurrencyBaseInfo baseInfo, final CurrencyDetailInfo detailInfo) {
        RtCurrencyBaseInfoDTO result = new RtCurrencyBaseInfoDTO();

        if (Objects.nonNull(baseInfo)) {
            result = RtCurrencyBaseInfoDTO.builder()
                    .browserUrl(baseInfo.getExplorer())
                    .currency(baseInfo.getName())
                    .symbol(baseInfo.getCode())
                    .circulatingSupply(baseInfo.getCirculatingSupply())
                    .issuePrice(baseInfo.getIssuePrice())
                    .issueTime(baseInfo.getIssueDate())
                    .totalCirculation(baseInfo.getMaxSupply().toString())
                    .officialWebsiteUrl(baseInfo.getOfficalWebsite())
                    .sourceCodeUrl(baseInfo.getSourceCodeUrl())
                    .exchangeNum(Short.valueOf("0"))
                    .logoUrl(baseInfo.getImgUrl())
                    .build();
        }

        if (Objects.nonNull(detailInfo)) {
            result.setWhitePaperUrl(detailInfo.getWhitePaper());
            result.setFacebookUrl(detailInfo.getFacebook());
            result.setWeiBoUrl(detailInfo.getWeibo());
            result.setTwitterUrl(detailInfo.getTwitter());
            result.setTelegramUrl(detailInfo.getTelegram());
            result.setConcept(detailInfo.getConcept());
            result.setCoinMarketCapUrl(detailInfo.getCoinMarketCapUrl());
        }

        return result;
    }

    /**
     * 合并并构建RtCurrencyBaseInfoDTO
     *
     * @param baseInfo
     * @param detail
     * @param rank
     * @return
     */
    private RtCurrencyBaseInfoDTO buildAndMergeCurrencyBaseInfo(final CurrencyBaseInfo baseInfo, final RtCurrencyDetail detail, final RtCurrencyRank rank, final CurrencyDetailInfo detailInfo) {
        final String invalidTimeStr = "0000-00-00";
        final String invalidValue = "0";

        final RtCurrencyBaseInfoDTO rtBaseInfo = this.buildDefaultBaseInfo(baseInfo, detailInfo);
        this.modelMapper.map(rank, rtBaseInfo);

        if (!Objects.equals(detail.getIssuePrice(), invalidValue)) {
            rtBaseInfo.setIssuePrice(detail.getIssuePrice());
        }
        if (!Objects.equals(detail.getTotalCirculation(), invalidValue)) {
            rtBaseInfo.setTotalCirculation(detail.getTotalCirculation());
        }
        if (!Objects.equals(detail.getCirculatingSupply(), 0L)) {
            rtBaseInfo.setCirculatingSupply(detail.getCirculatingSupply());
        }
        if (!Objects.equals(detail.getIssueTime(), invalidTimeStr)) {
            rtBaseInfo.setIssueTime(detail.getIssueTime());
        }
        rtBaseInfo.setExchangeNum(detail.getExchangeNum());
        rtBaseInfo.setLogoUrl(baseInfo.getImgUrl());
        return rtBaseInfo;
    }

    /**
     * 获取wiki中币种的项目信息
     *
     * @param currencyCode 币对名称
     * @return currency project info
     */
    @Override
    public RtCurrencyProjectInfoDTO getCurrencyProjectInfo(final String currencyCode, final String locale) {

        final RtCurrencyRank rtRank = this.rtRankService.getCidByCurrencyCode(currencyCode);
        if (Objects.isNull(rtRank)) {
            log.warn("RtCurrencyRank is not exists with currencyCode => {}", currencyCode);
            return RtCurrencyProjectInfoDTO.builder()
                    .score(this.buildDefaultScore(currencyCode, locale)).introduction(this.buildDefaultIntroduction(currencyCode, locale)).progress(this.buildDefaultProgress(currencyCode, locale))
                    .build();
        }
        return RtCurrencyProjectInfoDTO.builder()
                .score(this.buildRtScore(currencyCode, locale, rtRank.getCid())).introduction(this.buildDefaultIntroduction(currencyCode, locale)).price(this.buildExchangeCharts(rtRank.getCid()))
                .teams(this.rtTeamService.listRtTeamByCid(rtRank.getCid())).progress(this.buildDefaultProgress(currencyCode, locale)).github(this.buildGithubCharts(rtRank.getCid()))
                .build();
    }

    /**
     * 构建默认的score
     *
     * @param currencyCode
     * @param locale
     * @return
     */
    private ScoreInfoDTO buildDefaultScore(final String currencyCode, final String locale) {
        final ScoreInfoDTO scoreInfo = new ScoreInfoDTO();
        final CurrencyLevelInfo levelInfo = this.tokenInsightAdminService.getLevelInfo(currencyCode, locale);
        if (Objects.isNull(levelInfo)) {
            log.warn("CurrencyLevelInfo is not exists with currencyCode=>{} and locale=>{}", currencyCode, locale);
            return scoreInfo;
        }
        scoreInfo.setLevel(levelInfo.getLevelResult());
        scoreInfo.setDescription(levelInfo.getDescription());
        scoreInfo.setAnticipation(levelInfo.getAnticipation());
        return scoreInfo;
    }

    /**
     * 构建Rt Score
     *
     * @param currencyCode
     * @param locale
     * @param cid
     * @return
     */
    private ScoreInfoDTO buildRtScore(final String currencyCode, final String locale, final String cid) {
        final ScoreInfoDTO scoreInfo = this.buildDefaultScore(currencyCode, locale);
        final RtCurrencyScore rtCurrencyScore = this.rtScoreService.getByCidAndCurrencyCode(currencyCode, cid);
        this.modelMapper.map(rtCurrencyScore, scoreInfo);
        return scoreInfo;
    }

    /**
     * 构建默认的项目介绍
     *
     * @param currencyCode
     * @param locale
     * @return
     */
    private IntroductionDTO buildDefaultIntroduction(final String currencyCode, final String locale) {
        final CurrencyDetailInfo detailInfo = this.detailInfoService.getByCode(currencyCode, locale);
        if (Objects.isNull(detailInfo)) {
            log.warn("CurrencyDetailInfo is not exists with currencyCode=>{} and locale=>{}", currencyCode, locale);
            return null;
        }
        return new IntroductionDTO(detailInfo.getIntroduction());
    }

    /**
     * 构建rt币种Github
     *
     * @param cid 代币ID
     * @return
     */
    private List<GithubChartsDTO> buildGithubCharts(final String cid) {
        final JSONArray json = this.ratingTokenService.getGithubCharts(cid);
        return json.toJavaList(GithubChartsDTO.class);
    }

    /**
     * 构建rt价格趋势
     *
     * @param cid 代币ID
     * @return
     */
    private List<ExchangeChartsDTO> buildExchangeCharts(final String cid) {
        final String category = "week";
        final JSONArray json = this.ratingTokenService.getExchangeCharts(cid, category);
        return json.toJavaList(ExchangeChartsDTO.class);
    }


    /**
     * 构建币种项目进展
     *
     * @param currencyCode 币种code
     * @return
     */
    private List<ProgressDTO> buildDefaultProgress(final String currencyCode, final String locale) {
        return Optional.ofNullable(this.progressInfoService.getByCode(currencyCode, locale))
                .map(itemList -> itemList.stream()
                        .map(item -> new ProgressDTO(item.getPublishDate(), item.getContent(), item.getSort()))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    /**
     * 获取情感得分
     *
     * @param cid
     * @return
     */
    private SentimentScoreDTO buildEmotionScore(final String cid) {
        final JSONObject json = this.ratingTokenService.getSentimentScore(cid);
        return json.toJavaObject(SentimentScoreDTO.class);
    }

    /**
     * 获取情感得分
     *
     * @param cid
     * @return
     */
    private List<SentimentCommunityChartsDTO> buildSentimentCommunityCharts(final String cid, final String platform) {
        final Integer timeDelta = 7;
        final JSONArray json = this.ratingTokenService.getSentimentCommunityCharts(cid, platform, timeDelta);
        return json.toJavaList(SentimentCommunityChartsDTO.class);
    }

    /**
     * 获取社交热度
     *
     * @param cid
     * @return
     */
    private List<CommunityChartsDTO> buildCommunityCharts(final String cid, final String platform) {
        final JSONArray json = this.ratingTokenService.getCommunityCharts(cid, platform);
        return json.toJavaList(CommunityChartsDTO.class);
    }

    /**
     * 构建项目币种动态信息
     *
     * @param currencyCode
     * @param locale
     * @return
     */
    private List<DynamicInfoDTO> buildDynamicInfos(final String currencyCode, final String locale, final PageInfo pageInfo) {

        final List<CurrencyTrendInfo> queryData = this.trendInfoService.listByCodeAndLocaleAndStatus(currencyCode, locale, CurrencyStatusEnum.PUBLISH.getCode(), pageInfo);
        if (CollectionUtils.isEmpty(queryData)) {
            return Collections.emptyList();
        }
        return queryData.stream()
                .map(item -> new DynamicInfoDTO(item.getTitle(), item.getLink(), item.getContent(), "", item.getPublishDate()))
                .collect(Collectors.toList());
    }
}
