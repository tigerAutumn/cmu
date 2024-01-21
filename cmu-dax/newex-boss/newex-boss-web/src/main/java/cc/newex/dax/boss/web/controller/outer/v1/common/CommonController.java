package cc.newex.dax.boss.web.controller.outer.v1.common;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.web.common.enums.CurrencyBizEnum;
import cc.newex.dax.extra.client.ExtraCmsI18nAdminClient;
import cc.newex.dax.perpetual.client.CurrencyPairAdminClient;
import cc.newex.dax.spot.client.SpotCurrencyPairServiceClient;
import cc.newex.dax.spot.dto.ccex.CurrencyPairBaseInfoDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 获取币种接口，币对接口
 *
 * @author gi
 * @date 8/15/18
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/common")
public class CommonController {

    @Autowired
    private AdminServiceClient adminServiceClient;

    @Autowired
    private SpotCurrencyPairServiceClient spotClient;

    @Autowired
    private ExtraCmsI18nAdminClient extraCmsI18nAdminClient;

    @Autowired
    private CurrencyPairAdminClient perpetualClient;

    @GetMapping(value = "/spot/currency")
    @OpLog(name = "获取币币-币种信息")
    public JSONArray spotCurrency(@CurrentUser final User loginUser) {
        final ResponseResult source = this.adminServiceClient.getAllCurrencies(CurrencyBizEnum.SPOT.getName(), loginUser.getLoginBrokerId());
        if (source.getCode() == 0) {
            return JSONObject.parseArray(source.getData().toString());
        }
        return null;
    }

    @GetMapping(value = "/perpetual/currency")
    @OpLog(name = "获取合约-币种信息")
    public JSONArray perpetualCurrency(@CurrentUser final User loginUser) {
        final ResponseResult source = this.adminServiceClient.getAllCurrencies(CurrencyBizEnum.PERPETUAL.getName(), loginUser.getLoginBrokerId());
        if (source.getCode() == 0) {
            return JSONObject.parseArray(source.getData().toString());
        }
        return null;
    }

    @GetMapping(value = "/c2c/currency")
    @OpLog(name = "获取法币-币种信息")
    public JSONArray c2cCurrency(@CurrentUser final User loginUser) {
        final ResponseResult source = this.adminServiceClient.getAllCurrencies(CurrencyBizEnum.C2C.getName(), loginUser.getLoginBrokerId());
        if (source.getCode() == 0) {
            return JSONObject.parseArray(source.getData().toString());
        }
        return null;
    }

    @GetMapping(value = "/portfolio/currency")
    @OpLog(name = "获取组合-币种信息")
    public JSONArray portfolioCurrency(@CurrentUser final User loginUser) {
        final ResponseResult source = this.adminServiceClient.getAllCurrencies(CurrencyBizEnum.PORTFOLIO.getName(), loginUser.getLoginBrokerId());
        if (source.getCode() == 0) {
            return JSONObject.parseArray(source.getData().toString());
        }
        return null;
    }

    @OpLog(name = "获取全部币对")
    @GetMapping(value = "/product")
    public JSONArray getCurrency() {
        final ResponseResult<List<CurrencyPairBaseInfoDTO>> responseResult = this.spotClient.getCurrencyPairs();

        if (responseResult == null || responseResult.getCode() != 0) {
            return null;
        }

        final List<CurrencyPairBaseInfoDTO> currencyPairBaseInfoDTOList = responseResult.getData();
        if (CollectionUtils.isEmpty(currencyPairBaseInfoDTOList)) {
            return null;
        }

        final JSONArray jsonArray = new JSONArray();

        currencyPairBaseInfoDTOList.forEach(v -> {
            final JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", v.getId());
            jsonObject.put("code", v.getCode().toUpperCase());

            jsonArray.add(jsonObject);
        });

        return jsonArray;
    }

    @OpLog(name = "获取合约全部币对的PairCode")
    @GetMapping(value = "/perpetual/currencyPair/pairCodes")
    public JSONArray listPerpetualPairCode() {
        final List<String> queryData = Optional.ofNullable(this.perpetualClient.listCurrencyPairCode().getData()).orElse(Collections.emptyList());

        final List<JSONObject> data = queryData.stream()
                .map(item -> {
                    final JSONObject json = new JSONObject();
                    json.put("pairCode", item);
                    return json;
                })
                .collect(Collectors.toList());
        return JSON.parseArray(JSON.toJSONString(data));
    }

    @OpLog(name = "获取合约全部币对的BaseCode")
    @GetMapping(value = "/perpetual/currencyPair/baseCodes")
    public JSONArray listPerpetualPairBaseCode() {
        final List<String> queryData = Optional.ofNullable(this.perpetualClient.listCurrencyBaseCode().getData()).orElse(Collections.emptyList());

        final List<JSONObject> data = queryData.stream()
                .distinct()
                .map(item -> {
                    final JSONObject json = new JSONObject();
                    json.put("baseCode", item);
                    return json;
                })
                .collect(Collectors.toList());
        return JSON.parseArray(JSON.toJSONString(data));
    }


    @OpLog(name = "获取多语言下拉列表")
    @GetMapping(value = "/lang")
    public JSONArray getLang() {
        final ResponseResult result = this.extraCmsI18nAdminClient.listAllI18nLanguage();
        if (result.getCode() == 0 && result.getData() != null) {
            return JSONArray.parseArray(result.getData().toString());
        }
        return null;
    }

}
