package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.ContractExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;

import java.util.List;

/**
 * 合约表 服务接口
 *
 * @author newex-team
 * @date 2018-11-01 09:30:28
 */
public interface ContractService extends CrudService<Contract, ContractExample, Long> {

    void loadDb();

    /**
     * 获取合约列表
     *
     * @return
     */
    List<Contract> getUnExpiredContract();

    /**
     * 查询该券商下的合约
     *
     * @param pairCodeList
     * @return
     */
    List<Contract> getUnExpiredPairCodeContract(List<String> pairCodeList);

    /**
     * 获取合约信息（未过期）
     *
     * @param contractCode 合约 code
     * @return
     */
    Contract getContractWithNoCache(String contractCode);

    /**
     * 获取合约信息（未过期）
     * @param pairCode
     * @return
     */
    Contract getContractWithNoCacheByPairCode(String pairCode);

    /**
     * 获取合约信息（未过期）
     *
     * @param contractCode 合约 code
     * @return
     */
    Contract getContract(String contractCode);

    /**
     * 获取清算合约
     *
     * @return
     */
    List<Contract> listLiquidationContract();

    /**
     * 生成下一个合约
     *
     * @param contract
     * @param currencyPair
     */
    void createNextContract(Contract contract, CurrencyPair currencyPair);


    /**
     * 锁住合约、开始清算
     *
     * @param contract
     * @return
     */
    boolean lockContract(Contract contract);


    /**
     * 判断合约是否已经加锁
     *
     * @param contractCode
     * @return
     */
    boolean isLockedContract(String contractCode);

    /**
     * 判断撮合是否停止
     *
     * @param contract
     * @return
     */
    boolean isMatchStoped(Contract contract);


}
