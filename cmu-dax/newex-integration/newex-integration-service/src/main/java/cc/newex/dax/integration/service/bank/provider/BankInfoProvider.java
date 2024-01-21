package cc.newex.dax.integration.service.bank.provider;

import cc.newex.dax.integration.domain.bank.BankInfo;
import cc.newex.dax.integration.domain.bank.BankInfoBO;

import java.util.Map;

/**
 * @author newex-team
 * @date 2018-06-05
 */
public interface BankInfoProvider {

    /**
     * 获取当前提供者的名称
     *
     * @return provider name
     */
    String getName();

    /**
     * 设置provider配置选项
     *
     * @param options 配置选项集合
     */
    void setOptions(Map<String, Object> options);

    /**
     * @param bo
     * @return
     */
    BankInfo getBankInfo(BankInfoBO bo);
}
