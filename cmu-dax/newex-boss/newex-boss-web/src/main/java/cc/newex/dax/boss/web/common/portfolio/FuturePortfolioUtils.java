package cc.newex.dax.boss.web.common.portfolio;

import cc.newex.dax.boss.web.common.util.DateFormater;
import cc.newex.dax.boss.web.model.portfolio.FuturePortfolioVO;
import cc.newex.dax.portfolio.dto.PortfolioConfigDTO;
import cc.newex.dax.portfolio.dto.PortfolioInfoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author liutiejun
 * @date 2018-07-27
 */
public class FuturePortfolioUtils {

    public static PortfolioConfigDTO toPortfolioConfig(final FuturePortfolioVO futurePortfolioVO, final Long id) {
        if (futurePortfolioVO == null) {
            return null;
        }

        final PortfolioConfigDTO.PortfolioConfigDTOBuilder builder = PortfolioConfigDTO.builder();

        builder.id(id);
        builder.portfolioType(futurePortfolioVO.getPortfolioType());
        builder.quoteCurrency("USDT");
        builder.name(futurePortfolioVO.getName());
//        builder.logo(futurePortfolioVO.getIssuerLogo());
        builder.issuerId(futurePortfolioVO.getIssuerId());
        builder.purchaseStart(DateFormater.parseNoSecond(futurePortfolioVO.getPurchStart()));
        builder.purchaseEnd(DateFormater.parseNoSecond(futurePortfolioVO.getPurchEnd()));
        builder.personLimit(futurePortfolioVO.getPersonLimit());
        builder.purchaseLimit(futurePortfolioVO.getPurchLimit());
        builder.minTradeSize(futurePortfolioVO.getMinPurch());
        builder.deliveryDate(DateFormater.parseNoSecond(futurePortfolioVO.getDeliveryTime()));

        builder.envStatus(futurePortfolioVO.getEnvStatus());
        builder.sort(futurePortfolioVO.getSort());

        final List<PortfolioInfoDTO> portfolioInfoDTOList = new ArrayList<>();
        portfolioInfoDTOList.add(toZhPortfolioInfo(futurePortfolioVO));
        portfolioInfoDTOList.add(toEnPortfolioInfo(futurePortfolioVO));

        builder.portfolioInfoDTOList(portfolioInfoDTOList);

        return builder.build();
    }

    /**
     * 封装中文信息
     *
     * @param futurePortfolioVO
     * @return
     */
    private static PortfolioInfoDTO toZhPortfolioInfo(final FuturePortfolioVO futurePortfolioVO) {
        if (futurePortfolioVO == null) {
            return null;
        }

        final PortfolioInfoDTO.PortfolioInfoDTOBuilder builder = PortfolioInfoDTO.builder();

        builder.id(futurePortfolioVO.getZhId());
        builder.portfolioName(futurePortfolioVO.getName());
        builder.lang(Locale.CHINA.toLanguageTag());
        builder.title(futurePortfolioVO.getZhSubtitle());
//        builder.projectName(futurePortfolioVO.getZhIssuerName());
        builder.envStatus(futurePortfolioVO.getEnvStatus());
        builder.productIntroduce(futurePortfolioVO.getZhProductIntro());
        builder.issuerIntroduce(futurePortfolioVO.getZhIssuerIntro());

        return builder.build();
    }

    /**
     * 封装英文信息
     *
     * @param futurePortfolioVO
     * @return
     */
    private static PortfolioInfoDTO toEnPortfolioInfo(final FuturePortfolioVO futurePortfolioVO) {
        if (futurePortfolioVO == null) {
            return null;
        }

        final PortfolioInfoDTO.PortfolioInfoDTOBuilder builder = PortfolioInfoDTO.builder();

        builder.id(futurePortfolioVO.getEnId());
        builder.portfolioName(futurePortfolioVO.getName());
        builder.lang(Locale.US.toLanguageTag());
        builder.title(futurePortfolioVO.getEnSubtitle());
//        builder.projectName(futurePortfolioVO.getEnIssuerName());
        builder.envStatus(futurePortfolioVO.getEnvStatus());
        builder.productIntroduce(futurePortfolioVO.getEnProductIntro());
        builder.issuerIntroduce(futurePortfolioVO.getEnIssuerIntro());

        return builder.build();
    }

}
