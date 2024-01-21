package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.common.enums.ContractStatusEnum;
import cc.newex.dax.perpetual.criteria.ContractExample;
import cc.newex.dax.perpetual.data.ContractRepository;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import cc.newex.dax.perpetual.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 合约表 服务实现
 *
 * @author newex-team
 * @date 2018-11-01 09:30:28
 */
@Slf4j
@Service
public class ContractServiceImpl
        extends AbstractCrudService<ContractRepository, Contract, ContractExample, Long>
        implements ContractService {

    private volatile Map<String, Contract> contractMap = new ConcurrentHashMap<>();

    @Autowired
    private ContractRepository contractRepository;

    @Override
    protected ContractExample getPageExample(final String fieldName, final String keyword) {
        final ContractExample example = new ContractExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @PostConstruct
    public void init() {
        this.loadDb();
    }

    @Override
    @Scheduled(fixedDelay = 500, initialDelay = 1000)
    public void loadDb() {
        final ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andExpiredEqualTo(0);
        final List<Contract> contractList = this.contractRepository.selectByExample(contractExample);
        final Map<String, Contract> tmpMap = new ConcurrentHashMap<>();
        if (!CollectionUtils.isEmpty(contractList)) {
            for (final Contract contract : contractList) {
                tmpMap.put(contract.getContractCode(), contract);
            }
        }
        synchronized (this.contractMap) {
            this.contractMap = tmpMap;
        }
    }

    @Override
    public List<Contract> getUnExpiredContract() {
        final ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andExpiredEqualTo(0);
        return this.contractRepository.selectByExample(contractExample);
    }

    @Override
    public List<Contract> getUnExpiredPairCodeContract(final List<String> pairCodeList) {
        if (pairCodeList == null || pairCodeList.size() == 0) {
            return new ArrayList<>();
        }
        final ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andExpiredEqualTo(0).andPairCodeIn(pairCodeList);
        return this.contractRepository.selectByExample(contractExample);
    }

    @Override
    public Contract getContractWithNoCache(final String contractCode) {
        final ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andContractCodeEqualTo(contractCode).andExpiredEqualTo(0);
        return this.getOneByExample(contractExample);
    }

    @Override
    public Contract getContractWithNoCacheByPairCode(final String pairCode) {
        final ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andPairCodeEqualTo(pairCode).andExpiredEqualTo(0);
        return this.getOneByExample(contractExample);
    }

    @Override
    public Contract getContract(final String contractCode) {
        final String key = contractCode.toLowerCase();
        if (!this.contractMap.containsKey(key)) {
            synchronized (this.contractMap) {
                if (!this.contractMap.containsKey(contractCode)) {
                    final Contract contract = this.getContractWithNoCache(contractCode);
                    if (ObjectUtils.allNotNull(contract)) {
                        this.contractMap.put(key, contract);
                    }
                }
            }
        }
        final Contract contract = this.contractMap.get(key);
        return contract;
    }

    @Override
    public List<Contract> listLiquidationContract() {
        final ContractExample contractExample = new ContractExample();
        contractExample.createCriteria().andExpiredEqualTo(0)
                .andLiquidationDateLessThanOrEqualTo(new Date());
        return this.getByExample(contractExample);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createNextContract(final Contract contract, final CurrencyPair currencyPair) {
        final Contract record = this.getById(contract.getId());

        final Date nowTime = new Date();
        record.setExpired(1);
        record.setModifyDate(nowTime);
        record.setStatus(ContractStatusEnum.CLEAR_FINISH.getCode());
        final Contract next = new Contract();
        BeanUtils.copyProperties(contract, next);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(contract.getLiquidationDate());
        calendar.add(Calendar.HOUR_OF_DAY, currencyPair.getLiquidationHour());
        next.setId(null);
        next.setExpired(0);
        next.setLiquidationDate(calendar.getTime());
        next.setModifyDate(nowTime);
        next.setCreatedDate(nowTime);
        next.setStatus(ContractStatusEnum.NORMAL.getCode());
        next.setMarketPrice(null);
        final List<Contract> contractList = Arrays.asList(record, next);
        this.batchAddOnDuplicateKey(contractList);
    }

    @Override
    public boolean lockContract(final Contract contract) {
        final Contract record = new Contract();
        record.setId(contract.getId());
        record.setStatus(ContractStatusEnum.CLEARING.getCode());
        record.setModifyDate(new Date());
        record.setMarketPrice(contract.getMarketPrice());
        final ContractExample contractExample = new ContractExample();
        final ContractExample.Criteria criteria = contractExample.createCriteria();
        criteria.andIdEqualTo(contract.getId());
        criteria.andStatusEqualTo(ContractStatusEnum.NORMAL.getCode());
        this.editByExample(record, contractExample);
        return true;
    }

    @Override
    public boolean isLockedContract(final String contractCode) {
        final Contract contract = this.getContract(contractCode);
        if (contract == null) {
            ContractServiceImpl.log.error("not found contract, pairCode : {}", contractCode);
            return true;
        }
        final boolean result =
                ContractStatusEnum.fromInteger(contract.getStatus()) != ContractStatusEnum.NORMAL;
        return result;
    }

    @Override
    public boolean isMatchStoped(final Contract contract) {
        final Contract record = this.getContractWithNoCache(contract.getContractCode());
        if (record == null) {
            ContractServiceImpl.log.error("not found contract, pairCode : {}", contract.getContractCode());
            return false;
        }
        return ContractStatusEnum.fromInteger(record.getStatus()) == ContractStatusEnum.MATCHING_STOP;
    }
}
