package cc.newex.dax.asset.rest.controller.outer.v1.open;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.criteria.AssetCurrencyCompressExample;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.AssetCurrencyCompress;
import cc.newex.dax.asset.domain.CurrencyExtendAttributes;
import cc.newex.dax.asset.dto.AssetCurrencyDTO;
import cc.newex.dax.asset.service.AssetCurrencyCompressService;
import cc.newex.dax.asset.util.DomainUtil;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lilaizhen
 */
@Slf4j
@RestController
@RequestMapping("/v1/asset/open/{biz}/currency")
public class CurrencyController {

    @Autowired
    private AssetCurrencyCompressService assetCurrencyCompressService;
    @Autowired
    private UsersClient usersClient;

    @RequestMapping("/get/{currency}")
    public ResponseResult getCurrency(@PathVariable("currency") String currency,
                                      @PathVariable("biz") String biz,
                                      HttpServletRequest request) {
        try {
            CurrencyEnum currencyEnum = CurrencyEnum.parseName(currency);
            BizEnum bizEnum = BizEnum.parseBiz(biz);
            ShardTable table = ShardTable.builder().prefix(bizEnum.getName()).build();

            AssetCurrencyCompressExample example = new AssetCurrencyCompressExample();
            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }
            example.createCriteria().andBrokerIdEqualTo(brokerIdResult.getData()).andIdEqualTo(currencyEnum.getIndex());
            AssetCurrencyCompress assetCurrencyCompress = this.assetCurrencyCompressService.getOneByExample(example, table);
            if (ObjectUtils.isEmpty(assetCurrencyCompress)) {
                return ResultUtils.failure(BizErrorCodeEnum.INVALID_CURRENCY);
            }
            CurrencyExtendAttributes extendAttributes = JSONObject.parseObject(assetCurrencyCompress.getExtendAttrs(), CurrencyExtendAttributes.class);
            AssetCurrencyDTO assetCurrencyDTO = AssetCurrencyDTO.builder()
                    .blockBrowser(extendAttributes.getBlockBrowser())
                    .cnWikiUrl(extendAttributes.getCnWikiUrl())
                    .twWikiUrl(extendAttributes.getTwWikiUrl())
                    .usWikiUrl(extendAttributes.getUsWikiUrl())
                    .fullName(extendAttributes.getFullName())
                    .build();
            return ResultUtils.success(assetCurrencyDTO);
        } catch (Exception e) {
            log.error("getCurrency error {}", e);
            return ResultUtils.failure(BizErrorCodeEnum.INVALID_CURRENCY);
        }
    }

    @RequestMapping("/fee")
    public ResponseResult fees(@PathVariable("biz") String biz, HttpServletRequest request) {
        BizEnum bizEnum = BizEnum.parseBiz(biz);
        AssetCurrencyDTO queryParam = AssetCurrencyDTO.builder()
                .online(1)
                .build();

        String domain = DomainUtil.getDomain(request);
        ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
        if (brokerIdResult.getCode() != 0) {
            return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
        }
        queryParam.setBrokerId(brokerIdResult.getData());
        ShardTable table = ShardTable.builder().prefix(bizEnum.getName()).build();
        List<AssetCurrency> allCurrency = this.assetCurrencyCompressService.getByExampleCustom(queryParam, table);
        List<AssetCurrencyDTO> currencyDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        allCurrency.forEach(assetCurrency -> {
            if (assetCurrency.getWithdrawable() == 0 && assetCurrency.getRechargeable() == 0) {
                return;
            }
            AssetCurrencyDTO dto = AssetCurrencyDTO.builder().build();
            modelMapper.map(assetCurrency, dto);
            currencyDTOS.add(dto);
        });

        return ResultUtils.success(currencyDTOS);
    }
}
