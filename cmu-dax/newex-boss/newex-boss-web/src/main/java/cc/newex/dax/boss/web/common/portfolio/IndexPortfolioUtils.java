package cc.newex.dax.boss.web.common.portfolio;

import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.boss.web.common.util.DateFormater;
import cc.newex.dax.boss.web.model.portfolio.IndexPortfolioVO;
import cc.newex.dax.portfolio.dto.FeesConfigDTO;
import cc.newex.dax.portfolio.dto.PortfolioConfigDTO;
import cc.newex.dax.portfolio.dto.PortfolioInfoDTO;
import cc.newex.dax.portfolio.enums.FeesTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 数据转换工具类
 *
 * @author liutiejun
 * @date 2018-07-27
 */
@Slf4j
@Component
public class IndexPortfolioUtils {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 将DTO的数据转换为VO的格式
     *
     * @param portfolioConfigResult
     * @return
     */
    public DataGridPagerResult<IndexPortfolioVO> toIndexPortfolio(final DataGridPagerResult<PortfolioConfigDTO> portfolioConfigResult) {
        if (portfolioConfigResult == null) {
            return null;
        }

        final Long total = portfolioConfigResult.getTotal();
        final List<PortfolioConfigDTO> portfolioConfigDTOList = portfolioConfigResult.getRows();

        final List<IndexPortfolioVO> indexPortfolioVOList = this.toIndexPortfolio(portfolioConfigDTOList);

        return new DataGridPagerResult<>(total, indexPortfolioVOList);
    }

    /**
     * 将DTO的数据转换为VO的格式
     *
     * @param portfolioConfigDTOList
     * @return
     */
    public List<IndexPortfolioVO> toIndexPortfolio(final List<PortfolioConfigDTO> portfolioConfigDTOList) {
        if (CollectionUtils.isEmpty(portfolioConfigDTOList)) {
            return new ArrayList<>();
        }

        final List<IndexPortfolioVO> indexPortfolioVOList = new ArrayList<>();

        portfolioConfigDTOList.forEach(portfolioConfigDTO -> indexPortfolioVOList.add(this.toIndexPortfolio(portfolioConfigDTO)));

        return indexPortfolioVOList;
    }

    /**
     * 将DTO的数据转换为VO的格式
     *
     * @param portfolioConfigDTO
     * @return
     */
    public IndexPortfolioVO toIndexPortfolio(final PortfolioConfigDTO portfolioConfigDTO) {
        if (portfolioConfigDTO == null) {
            return null;
        }

        final IndexPortfolioVO.IndexPortfolioVOBuilder builder = IndexPortfolioVO.builder();

        builder.id(portfolioConfigDTO.getId());
        builder.portfolioType(portfolioConfigDTO.getPortfolioType());
        builder.name(portfolioConfigDTO.getName());
        builder.issuerLogo(portfolioConfigDTO.getLogo());
        builder.issuerId(portfolioConfigDTO.getIssuerId());
        builder.mixedIndexName(portfolioConfigDTO.getIndexCurrency());
        builder.prePurchStart(DateFormater.formatNoSecond(portfolioConfigDTO.getPrePurchaseStart()));
        builder.purchStart(DateFormater.formatNoSecond(portfolioConfigDTO.getPurchaseStart()));
        builder.purchEnd(DateFormater.formatNoSecond(portfolioConfigDTO.getPurchaseEnd()));
        builder.personLimit(portfolioConfigDTO.getPersonLimit());
        builder.purchLimit(portfolioConfigDTO.getPurchaseLimit());
        builder.minPurch(portfolioConfigDTO.getMinTradeSize());
        builder.maxSizeDigit(portfolioConfigDTO.getMaxSizeDigit());

        Integer midwaySwitch = portfolioConfigDTO.getMidwaySwitch();
        if (midwaySwitch == null) {
            midwaySwitch = 0;
        }
        builder.midwaySwitch(midwaySwitch);

        Integer redeemComponent = portfolioConfigDTO.getElementSwitch();
        if (redeemComponent == null) {
            redeemComponent = 0;
        }
        builder.redeemComponent(redeemComponent);

        Integer redeemUsdt = portfolioConfigDTO.getQuoteSwitch();
        if (redeemUsdt == null) {
            redeemUsdt = 0;
        }
        builder.redeemUsdt(redeemUsdt);
        builder.transferSpot(0);

        builder.envStatus(portfolioConfigDTO.getEnvStatus());
        builder.sort(portfolioConfigDTO.getSort());

        builder.createdDate(portfolioConfigDTO.getCreatedDate());
        builder.updatedDate(portfolioConfigDTO.getModifyDate());

        final List<PortfolioInfoDTO> portfolioInfoDTOList = portfolioConfigDTO.getPortfolioInfoDTOList();
        this.parsePortfolioInfo(portfolioInfoDTOList, builder);

        final List<FeesConfigDTO> feesConfigDTOList = portfolioConfigDTO.getFeesConfigDTOList();
        this.parseAllFeesConfig(feesConfigDTOList, builder);

        builder.holdNumber(portfolioConfigDTO.getHoldPersonAmount());
        builder.issueTotal(portfolioConfigDTO.getRemainAmount());

        return builder.build();
    }

    /**
     * 将VO的数据转换为DTO的格式
     *
     * @param indexPortfolioVO
     * @param id
     * @return
     */
    public PortfolioConfigDTO toPortfolioConfig(final IndexPortfolioVO indexPortfolioVO, final Long id) {
        if (indexPortfolioVO == null) {
            return null;
        }

        final PortfolioConfigDTO.PortfolioConfigDTOBuilder builder = PortfolioConfigDTO.builder();

        builder.id(id);
        builder.portfolioType(indexPortfolioVO.getPortfolioType());
        builder.quoteCurrency("USDT");
        builder.name(indexPortfolioVO.getName());

        final String issuerLogo = this.getIssuerLogo(indexPortfolioVO);
        builder.logo(issuerLogo);

        builder.issuerId(indexPortfolioVO.getIssuerId());
        builder.indexCurrency(indexPortfolioVO.getMixedIndexName());
        builder.prePurchaseStart(DateFormater.parseNoSecond(indexPortfolioVO.getPrePurchStart()));
        builder.purchaseStart(DateFormater.parseNoSecond(indexPortfolioVO.getPurchStart()));
        builder.purchaseEnd(DateFormater.parseNoSecond(indexPortfolioVO.getPurchEnd()));
        builder.personLimit(indexPortfolioVO.getPersonLimit());
        builder.purchaseLimit(indexPortfolioVO.getPurchLimit());
        builder.minTradeSize(indexPortfolioVO.getMinPurch());
        builder.maxSizeDigit(indexPortfolioVO.getMaxSizeDigit());
        builder.midwaySwitch(indexPortfolioVO.getMidwaySwitch());

        Integer redeemComponent = indexPortfolioVO.getRedeemComponent();
        if (redeemComponent == null) {
            redeemComponent = 0;
        }

        Integer redeemUsdt = indexPortfolioVO.getRedeemUsdt();
        if (redeemUsdt == null) {
            redeemUsdt = 0;
        }

        builder.elementSwitch(redeemComponent);
        builder.quoteSwitch(redeemUsdt);

        if (redeemComponent.equals(1) || redeemUsdt.equals(1)) {
            builder.redeemSwitch(1);
        } else {
            builder.redeemSwitch(0);
        }

        builder.envStatus(indexPortfolioVO.getEnvStatus());
        builder.sort(indexPortfolioVO.getSort());

        builder.modifyDate(new Date());

        if (id == null) {
            builder.createdDate(new Date());
        }

        final List<PortfolioInfoDTO> portfolioInfoDTOList = this.toPortfolioInfo(indexPortfolioVO);
        builder.portfolioInfoDTOList(portfolioInfoDTOList);

        final List<FeesConfigDTO> feesConfigDTOList = this.toAllFeesConfig(indexPortfolioVO);
        builder.feesConfigDTOList(feesConfigDTOList);

        return builder.build();
    }

    /**
     * 解析手续费
     *
     * @param feesConfigDTOList
     * @param builder
     */
    private void parseAllFeesConfig(final List<FeesConfigDTO> feesConfigDTOList, final IndexPortfolioVO.IndexPortfolioVOBuilder builder) {
        if (CollectionUtils.isEmpty(feesConfigDTOList)) {
            return;
        }

        feesConfigDTOList.forEach(feesConfigDTO -> this.parseFeesConfig(feesConfigDTO, builder));
    }

    /**
     * 解析手续费
     *
     * @param feesConfigDTO
     * @param builder
     */
    private void parseFeesConfig(final FeesConfigDTO feesConfigDTO, final IndexPortfolioVO.IndexPortfolioVOBuilder builder) {
        if (feesConfigDTO == null) {
            return;
        }

        final Integer id = feesConfigDTO.getId();
        final String type = feesConfigDTO.getType();
        final BigDecimal rate = feesConfigDTO.getRate();

        if (FeesTypeEnum.APPLY.getType().equalsIgnoreCase(type)) {
            builder.purchId(id);
            builder.purchFee(rate);
        } else if (FeesTypeEnum.REDEEM.getType().equalsIgnoreCase(type)) {
            builder.redeemId(id);
            builder.redeemFee(rate);
        } else if (FeesTypeEnum.MANAGEMENT.getType().equalsIgnoreCase(type)) {
            builder.manageId(id);
            builder.manageFee(rate);
        }

    }

    /**
     * 设置手续费
     *
     * @param indexPortfolioVO
     * @return
     */
    private List<FeesConfigDTO> toAllFeesConfig(final IndexPortfolioVO indexPortfolioVO) {
        final String portfolioName = indexPortfolioVO.getName();

        // 申购手续费
        final Integer purchId = indexPortfolioVO.getPurchId();
        final BigDecimal purchFee = indexPortfolioVO.getPurchFee();

        // 赎回手续费
        final Integer redeemId = indexPortfolioVO.getRedeemId();
        final BigDecimal redeemFee = indexPortfolioVO.getRedeemFee();

        // 组合资产管理费
        final Integer manageId = indexPortfolioVO.getManageId();
        final BigDecimal manageFee = indexPortfolioVO.getManageFee();

        final List<FeesConfigDTO> feesConfigDTOList = new ArrayList<>();

        feesConfigDTOList.add(this.toFeesConfig(purchId, portfolioName, FeesTypeEnum.APPLY.getType(), purchFee));
        feesConfigDTOList.add(this.toFeesConfig(redeemId, portfolioName, FeesTypeEnum.REDEEM.getType(), redeemFee));
        feesConfigDTOList.add(this.toFeesConfig(manageId, portfolioName, FeesTypeEnum.MANAGEMENT.getType(), manageFee));

        return feesConfigDTOList;
    }

    private FeesConfigDTO toFeesConfig(final Integer id, final String portfolioName, final String type, final BigDecimal rate) {
        final FeesConfigDTO feesConfigDTO = FeesConfigDTO.builder()
                .id(id)
                .portfolioName(portfolioName)
                .type(type)
                .rate(rate)
                .build();

        return feesConfigDTO;
    }

    private String getIssuerLogo(final IndexPortfolioVO indexPortfolioVO) {
        if (indexPortfolioVO == null) {
            return null;
        }

        final String issuerLogo = indexPortfolioVO.getIssuerLogo();
        if (StringUtils.isBlank(issuerLogo)) {
            return null;
        }

        if (StringUtils.startsWithIgnoreCase(issuerLogo, "https://")) {
            return issuerLogo;
        }

        return this.fileUploadService.getUrl(issuerLogo);
    }

    /**
     * 将图片的内容转换为base64字符串
     *
     * @param issuerLogoFile
     * @return
     */
    private String toBase64(final MultipartFile issuerLogoFile) {
        if (issuerLogoFile == null) {
            return null;
        }

        byte[] bytes = null;
        try {
            bytes = issuerLogoFile.getBytes();
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
        }

        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }

        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 封装不同语言的信息
     *
     * @param indexPortfolioVO
     * @return
     */
    private List<PortfolioInfoDTO> toPortfolioInfo(final IndexPortfolioVO indexPortfolioVO) {
        final List<PortfolioInfoDTO> portfolioInfoDTOList = new ArrayList<>();

        portfolioInfoDTOList.add(this.toZhPortfolioInfo(indexPortfolioVO));
        portfolioInfoDTOList.add(this.toEnPortfolioInfo(indexPortfolioVO));

        return portfolioInfoDTOList;
    }

    /**
     * 封装中文信息
     *
     * @param indexPortfolioVO
     * @return
     */
    private PortfolioInfoDTO toZhPortfolioInfo(final IndexPortfolioVO indexPortfolioVO) {
        if (indexPortfolioVO == null) {
            return null;
        }

        final PortfolioInfoDTO.PortfolioInfoDTOBuilder builder = PortfolioInfoDTO.builder();

        builder.id(indexPortfolioVO.getZhId());
        builder.portfolioName(indexPortfolioVO.getName());
        builder.lang(Locale.CHINA.toLanguageTag());
        builder.title(indexPortfolioVO.getZhSubtitle());
        builder.projectName(indexPortfolioVO.getZhIssuerName());
        builder.envStatus(indexPortfolioVO.getEnvStatus());
        builder.productIntroduce(indexPortfolioVO.getZhProductIntro());
        builder.issuerIntroduce(indexPortfolioVO.getZhIssuerIntro());

        return builder.build();
    }

    /**
     * 解析中文信息
     *
     * @param portfolioInfoDTO
     * @param builder
     */
    private void parseZhPortfolioInfo(final PortfolioInfoDTO portfolioInfoDTO, final IndexPortfolioVO.IndexPortfolioVOBuilder builder) {
        if (portfolioInfoDTO == null) {
            return;
        }

        builder.zhId(portfolioInfoDTO.getId());
        builder.zhSubtitle(portfolioInfoDTO.getTitle());
        builder.zhIssuerName(portfolioInfoDTO.getProjectName());
        builder.zhProductIntro(portfolioInfoDTO.getProductIntroduce());
        builder.zhIssuerIntro(portfolioInfoDTO.getIssuerIntroduce());
    }

    /**
     * 封装英文信息
     *
     * @param indexPortfolioVO
     * @return
     */
    private PortfolioInfoDTO toEnPortfolioInfo(final IndexPortfolioVO indexPortfolioVO) {
        if (indexPortfolioVO == null) {
            return null;
        }

        final PortfolioInfoDTO.PortfolioInfoDTOBuilder builder = PortfolioInfoDTO.builder();

        builder.id(indexPortfolioVO.getEnId());
        builder.portfolioName(indexPortfolioVO.getName());
        builder.lang(Locale.US.toLanguageTag());
        builder.title(indexPortfolioVO.getEnSubtitle());
        builder.projectName(indexPortfolioVO.getEnIssuerName());
        builder.envStatus(indexPortfolioVO.getEnvStatus());
        builder.productIntroduce(indexPortfolioVO.getEnProductIntro());
        builder.issuerIntroduce(indexPortfolioVO.getEnIssuerIntro());

        return builder.build();
    }

    /**
     * 解析英文信息
     *
     * @param portfolioInfoDTO
     * @param builder
     */
    private void parseEnPortfolioInfo(final PortfolioInfoDTO portfolioInfoDTO, final IndexPortfolioVO.IndexPortfolioVOBuilder builder) {
        if (portfolioInfoDTO == null) {
            return;
        }

        builder.enId(portfolioInfoDTO.getId());
        builder.enSubtitle(portfolioInfoDTO.getTitle());
        builder.enIssuerName(portfolioInfoDTO.getProjectName());
        builder.enProductIntro(portfolioInfoDTO.getProductIntroduce());
        builder.enIssuerIntro(portfolioInfoDTO.getIssuerIntroduce());
    }

    /**
     * 解析中英文信息
     *
     * @param portfolioInfoDTOList
     * @param builder
     */
    private void parsePortfolioInfo(final List<PortfolioInfoDTO> portfolioInfoDTOList, final IndexPortfolioVO.IndexPortfolioVOBuilder builder) {
        if (CollectionUtils.isEmpty(portfolioInfoDTOList)) {
            return;
        }

        for (final PortfolioInfoDTO portfolioInfoDTO : portfolioInfoDTOList) {
            final String lang = portfolioInfoDTO.getLang();

            if (StringUtils.isBlank(lang)) {
                continue;
            }

            if (lang.equalsIgnoreCase(Locale.CHINA.toLanguageTag())) {
                this.parseZhPortfolioInfo(portfolioInfoDTO, builder);
            }

            if (lang.equalsIgnoreCase(Locale.US.toLanguageTag())) {
                this.parseEnPortfolioInfo(portfolioInfoDTO, builder);
            }
        }
    }

}
