package cc.newex.dax.extra.rest.controller.outer.v1.common.cgm;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.asset.dto.AssetCurrencyDTO;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import cc.newex.dax.extra.rest.common.util.BrokerUtils;
import cc.newex.dax.extra.rest.model.ProjectTokenInfoVO;
import cc.newex.dax.extra.service.cgm.ProjectTokenInfoService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @date 2018/8/14 下午1:13
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/extra/public/cgm")
public class ProjectInfoPublicController {

    @Autowired
    private AdminServiceClient adminServiceClient;

    @Autowired
    private ProjectTokenInfoService projectTokenInfoService;

    /**
     * 获取所有的币种信息
     *
     * @return
     */
    @GetMapping(value = "/currencies")
    public ResponseResult list(final HttpServletRequest request) {

        final Integer brokerId = BrokerUtils.getBrokerId(request);

        final String lang = LocaleUtils.getLocale(request);

        final ResponseResult responseResult = this.adminServiceClient.getAllCurrencies("SPOT", brokerId);

        if (responseResult == null || responseResult.getCode() != 0) {
            return ResultUtils.success();
        }

        final List<AssetCurrencyDTO> assetCurrencyDTOList = JSON.parseArray(JSON.toJSONString(responseResult.getData()), AssetCurrencyDTO.class);

        final List<ProjectTokenInfo> projectTokenInfoList = this.projectTokenInfoService.listProjectsByBrokerId(brokerId);

        List<ProjectTokenInfoVO> projectTokenInfoVOList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(projectTokenInfoList)) {
            projectTokenInfoVOList = projectTokenInfoList.stream().map(projectTokenInfo -> {

                AssetCurrencyDTO assetCurrencyDTO =
                        assetCurrencyDTOList.stream()
                                .filter(assetCurrency -> assetCurrency.getSymbol().equalsIgnoreCase(projectTokenInfo.getTokenSymbol()))
                                .findFirst()
                                .orElse(new AssetCurrencyDTO());

                if (lang.equalsIgnoreCase(Locale.US.toLanguageTag())) {
                    return ProjectTokenInfoVO.builder()
                            .token(projectTokenInfo.getToken())
                            .tokenSymbol(projectTokenInfo.getTokenSymbol())
                            .issue(projectTokenInfo.getIssue())
                            .circulating(projectTokenInfo.getCirculating())
                            .price(projectTokenInfo.getPrice())
                            .onlineTime(projectTokenInfo.getOnlineTime().getTime())
                            .url(assetCurrencyDTO.getUsWikiUrl())
                            .build();
                } else {
                    return ProjectTokenInfoVO.builder()
                            .token(projectTokenInfo.getToken())
                            .tokenSymbol(projectTokenInfo.getTokenSymbol())
                            .issue(projectTokenInfo.getIssue())
                            .circulating(projectTokenInfo.getCirculating())
                            .price(projectTokenInfo.getPrice())
                            .onlineTime(projectTokenInfo.getOnlineTime().getTime())
                            .url(assetCurrencyDTO.getCnWikiUrl())
                            .build();
                }

            }).collect(Collectors.toList());
        }

        return ResultUtils.success(projectTokenInfoVOList);
    }

    /**
     * 查看币种的信息：总量、流通量、发行价
     *
     * @param tokenSymbol
     * @param request
     * @return
     */
    @GetMapping(value = "/tokenInfo/{tokenSymbol}")
    public ResponseResult getTokenInfo(@PathVariable("tokenSymbol") @NotBlank final String tokenSymbol, final HttpServletRequest request) {

        final ProjectTokenInfo projectTokenInfo = this.projectTokenInfoService.getByTokenSymbol(tokenSymbol);


        ProjectTokenInfoVO projectTokenInfoVO = null;

        if (Objects.nonNull(projectTokenInfo)) {
            projectTokenInfoVO = ProjectTokenInfoVO.builder()
                    .token(projectTokenInfo.getToken())
                    .tokenSymbol(projectTokenInfo.getTokenSymbol())
                    .issue(projectTokenInfo.getIssue())
                    .circulating(projectTokenInfo.getCirculating())
                    .price(projectTokenInfo.getPrice())
                    .onlineTime(projectTokenInfo.getOnlineTime().getTime())
                    .build();
        }

        return ResultUtils.success(projectTokenInfoVO);
    }

}
