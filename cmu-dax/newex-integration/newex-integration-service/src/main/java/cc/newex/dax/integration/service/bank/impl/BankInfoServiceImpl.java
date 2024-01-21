package cc.newex.dax.integration.service.bank.impl;

import cc.newex.dax.integration.domain.bank.BankInfo;
import cc.newex.dax.integration.domain.bank.BankInfoBO;
import cc.newex.dax.integration.service.bank.BankInfoService;
import cc.newex.dax.integration.service.bank.provider.BankInfoProviderFactory;
import cc.newex.dax.integration.service.conf.enums.RegionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BankInfoServiceImpl implements BankInfoService {

    @Autowired
    private BankInfoProviderFactory factory;

    @Override
    public BankInfo getBankInfo(final BankInfoBO bo) {
        return this.factory.getBankInfoProvider(RegionEnum.CHINA.getName()).getBankInfo(bo);
    }

}
