package cc.newex.dax.integration.service.bank.provider;

import cc.newex.commons.lang.math.WeightRandom;
import cc.newex.dax.integration.domain.conf.ProviderConf;
import cc.newex.dax.integration.service.conf.ProviderConfService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @date 2018-06-05
 */
@Slf4j
@Component
public class BankInfoProviderFactory {

    private static final Map<String, WeightRandom<BankInfoProvider, Double>> BANK_PROVIDER_MAP = Maps.newHashMapWithExpectedSize(2);

    private final ProviderConfService providerConfService;

    public BankInfoProviderFactory(final AliyunBankInfoProvider aliyunBankInfoProvider, final ProviderConfService providerConfService) {
        this.providerConfService = providerConfService;
        final List<BankInfoProvider> providers = Arrays.asList(aliyunBankInfoProvider);

        this.initProvidersWithWeight(providers);
    }

    public BankInfoProvider getBankInfoProvider(final String countryCode) {
        return MapUtils.getObject(BANK_PROVIDER_MAP, countryCode).random();
    }

    private void initProvidersWithWeight(final List<BankInfoProvider> providers) {
        final Map<String, ProviderConf> providerConfMap = this.providerConfService.getProviderConfMap();

        final Map<String, List<Pair<BankInfoProvider, Double>>> groupByRegionMap = this.providerConfService.newRegionProviderMap();

        for (final BankInfoProvider provider : providers) {
            final String key = provider.getName().toLowerCase();
            if (providerConfMap.containsKey(key)) {
                final ProviderConf conf = providerConfMap.get(key);
                provider.setOptions(JSON.parseObject(conf.getOptions()));
                this.providerConfService.putRegionProviderMap(provider, conf, groupByRegionMap);
            }
        }

        for (final String key : groupByRegionMap.keySet()) {
            BANK_PROVIDER_MAP.put(key, new WeightRandom<>(groupByRegionMap.get(key)));
        }
    }
}
