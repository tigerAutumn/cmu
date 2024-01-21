package cc.newex.dax.integration.service.bank;

import cc.newex.dax.integration.domain.bank.BankInfo;
import cc.newex.dax.integration.domain.bank.BankInfoBO;

public interface BankInfoService {
    /**
     * 验证银行卡三要素
     *
     * @param bo
     */
    BankInfo getBankInfo(BankInfoBO bo);
}
