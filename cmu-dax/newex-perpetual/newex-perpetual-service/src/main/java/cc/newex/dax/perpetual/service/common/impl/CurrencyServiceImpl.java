package cc.newex.dax.perpetual.service.common.impl;

import cc.newex.commons.dictionary.enums.BizEnum;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.asset.dto.AssetCurrencyDTO;
import cc.newex.dax.perpetual.common.config.PerpetualConfig;
import cc.newex.dax.perpetual.domain.bean.Currency;
import cc.newex.dax.perpetual.service.common.CurrencyService;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {
    final Map<String, Currency> nameAllMaps = Maps.newConcurrentMap();
    final Map<String, Currency> nameMaps = Maps.newConcurrentMap();
    final Map<Integer, Currency> idMaps = Maps.newConcurrentMap();
    @Resource
    private PerpetualConfig perpetualConfig;
    @Resource
    private AdminServiceClient adminServiceClient;
    private List<AssetCurrencyDTO> list = Lists.newCopyOnWriteArrayList();
    private List<Currency> currencyList = Lists.newCopyOnWriteArrayList();

    @PostConstruct
    public void init() {
        try {
            this.cacheCurrencyAll();
        } catch (final Exception e) {
            CurrencyServiceImpl.log.error("init currency failed", e);
        }
    }

    @Override
    @Scheduled(fixedDelay = 60_000, initialDelay = 1000)
    public List<Currency> cacheCurrencyAll() {
        if (this.perpetualConfig.getModule() == null || this.perpetualConfig.getModule() != 1 && this.perpetualConfig.getModule() != 2) {
            return null;
        }
        final List tempList = Lists.newCopyOnWriteArrayList();
        synchronized (this) {
            final ResponseResult responseResult =
                    this.adminServiceClient.getAllCurrencies(BizEnum.PERPETUAL.getName(), 1);
            final JSONArray resultData = (JSONArray) responseResult.getData();

            if (CollectionUtils.isEmpty(resultData)) {
                return null;
            }

            this.list = resultData.toJavaList(AssetCurrencyDTO.class).stream()
                    .sorted(Comparator.comparing(AssetCurrencyDTO::getSort)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(this.list)) {
                CurrencyServiceImpl.log.error("获取资金币种列表为空");
                return null;
            }
            tempList.clear();
        }
        for (final AssetCurrencyDTO assetCurrencyDTO : this.list) {
            Currency currency = Currency.builder().build();
            currency = this.convertCurrency(currency, assetCurrencyDTO);
            this.nameAllMaps.put(assetCurrencyDTO.getSymbol().toLowerCase(), currency);
            if (assetCurrencyDTO.getOnline().intValue() == 0) {
                continue;
            }
            this.nameMaps.put(currency.getSymbol().toLowerCase(), currency);
            this.idMaps.put(currency.getId(), currency);
            tempList.add(currency);
        }
        if (CollectionUtils.isNotEmpty(tempList)) {
            this.currencyList.clear();
            this.currencyList.addAll(tempList);
        }
        return this.currencyList;
    }

    @Override
    public Currency getCurrencyByCode(final String currencyCode) {
        return this.nameMaps.get(currencyCode.toLowerCase());
    }

    @Override
    public Currency getCurrencyById(final long id) {
        return this.idMaps.get(id);
    }

    @Override
    public List<Currency> listCurrencies() {
        if (CollectionUtils.isEmpty(this.currencyList)) {
            this.currencyList = this.cacheCurrencyAll();
        }
        return this.currencyList;
    }

    private Currency convertCurrency(final Currency currency,
                                     final AssetCurrencyDTO assetCurrencyDTO) {
        currency.setId(assetCurrencyDTO.getId());
        currency.setSymbol(assetCurrencyDTO.getSymbol());
        currency.setSign(assetCurrencyDTO.getSign());
        currency.setWithdrawable(assetCurrencyDTO.getWithdrawable() == 1 ? true : false);
        currency.setRechargeable(assetCurrencyDTO.getRechargeable() == 1 ? true : false);
        currency.setExchange(assetCurrencyDTO.getExchange() == 1 ? true : false);
        currency.setReceive(assetCurrencyDTO.getReceive() == 1 ? true : false);
        currency.setTransfer(assetCurrencyDTO.getTransfer());
        currency.setSort(assetCurrencyDTO.getSort());
        currency.setOnline(assetCurrencyDTO.getOnline().byteValue());
        currency.setCurrencyId(assetCurrencyDTO.getId());
        currency.setExpireDate(assetCurrencyDTO.getExpireDate());
        currency.setBrokerId(assetCurrencyDTO.getBrokerId());
        return currency;
    }
}
