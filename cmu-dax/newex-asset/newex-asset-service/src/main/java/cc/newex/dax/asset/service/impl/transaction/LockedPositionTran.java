package cc.newex.dax.asset.service.impl.transaction;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.criteria.AccountExample;
import cc.newex.dax.asset.criteria.LockedPositionExample;
import cc.newex.dax.asset.dao.AccountRepository;
import cc.newex.dax.asset.dao.LockedPositionRepository;
import cc.newex.dax.asset.dao.TransferRecordRepository;
import cc.newex.dax.asset.domain.Account;
import cc.newex.dax.asset.domain.LockedPosition;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.wallet.currency.BizEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author team-newex
 */
@Slf4j
@Component
public class LockedPositionTran {

    @Autowired
    private LockedPositionRepository lockedPositionRepository;

    @Autowired
    private TransferRecordRepository transferRecordRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional(rollbackFor = Exception.class)
    public void lockUserPositionTran(LockedPosition lockedPosition, String tradeNo, String from, String operator) {
        Date now = new Date();
        lockedPosition.setUpdateDate(now);
        lockedPosition.setCreateDate(now);
        int insertPosition = lockedPositionRepository.insert(lockedPosition);
        Assert.isTrue(insertPosition > 0, "锁仓记录locked_position失败userId=" + lockedPosition.getUserId() + ",tradeNo=" + tradeNo);

        //2. 创建解锁计划 --- 转移到回调
//      createUnlockedPlan(userId, tradeNo, releaseDate, lockedPosition);

        TransferRecord transferRecord = new TransferRecord();
        transferRecord.setUserId(lockedPosition.getUserId());
        transferRecord.setFrom(from);
        transferRecord.setTo(String.valueOf(lockedPosition.getId()));
        transferRecord.setAmount(lockedPosition.getAmount());
        transferRecord.setStatus((byte) TransferStatus.WAITING.getCode());
        transferRecord.setConfirmation(0);
        transferRecord.setTransferType(TransferType.LOCKED_POSITION.getCode());
        transferRecord.setCurrency(lockedPosition.getCurrency());
        transferRecord.setCreateDate(now);
        transferRecord.setUpdateDate(now);
        transferRecord.setBiz(BizEnum.SPOT.getIndex());
        transferRecord.setTraderNo(tradeNo);
        transferRecord.setRemark(operator);
        transferRecord.setBrokerId(lockedPosition.getBrokerId());
        int insertRecord = transferRecordRepository.insert(transferRecord);
        Assert.isTrue(insertRecord > 0, "锁仓记录transfer_record失败userId=" + lockedPosition.getUserId() + ",tradeNo=" + tradeNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void unlockUserPositionTran(BigDecimal amount, LockedPosition lockedPosition, String tradeNo, String operator) {
        LockedPosition updatePosition = new LockedPosition();
        updatePosition.setId(lockedPosition.getId());
        updatePosition.setLockAmount(lockedPosition.getLockAmount().subtract(amount));
        LockedPositionExample example = new LockedPositionExample();
        example.createCriteria()
                .andIdEqualTo(lockedPosition.getId())
                .andLockAmountEqualTo(lockedPosition.getLockAmount())
                .andLockAmountGreaterThanOrEqualTo(amount);
        int positionResult = lockedPositionRepository.updateByExample(updatePosition, example);
        Assert.isTrue(positionResult > 0, "解锁更新locked_position失败userId=" + lockedPosition.getUserId() + ",tradeNo=" + tradeNo);

        TransferRecord transferRecord = new TransferRecord();
        transferRecord.setUserId(lockedPosition.getUserId());
        transferRecord.setFrom(String.valueOf(lockedPosition.getId()));
        transferRecord.setTo("spot");
        transferRecord.setAmount(amount);
        transferRecord.setStatus((byte) TransferStatus.SENDING.getCode());
        transferRecord.setConfirmation(0);
        transferRecord.setTransferType(TransferType.UNLOCKED_POSITION.getCode());
        transferRecord.setCurrency(lockedPosition.getCurrency());
        transferRecord.setCreateDate(new Date());
        transferRecord.setUpdateDate(new Date());
        transferRecord.setTraderNo(tradeNo);
        transferRecord.setRemark(operator);
        transferRecord.setBrokerId(lockedPosition.getBrokerId());
        transferRecord.setBiz(BizEnum.SPOT.getIndex());
        int insertRecord = transferRecordRepository.insert(transferRecord);
        Assert.isTrue(insertRecord > 0, "解锁记录transfer_record失败userId=" + lockedPosition.getUserId() + ",tradeNo=" + tradeNo);

        AccountExample accountExample = new AccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();
        criteria.andUserIdEqualTo(lockedPosition.getUserId());
        criteria.andCurrencyEqualTo(lockedPosition.getCurrency());
        criteria.andBrokerIdEqualTo(lockedPosition.getBrokerId());
        criteria.andStatusEqualTo(0);
        Account account = accountRepository.selectAndLockOneByExample(accountExample);
        Assert.isTrue(account.getAccLockPosition().compareTo(amount) >= 0, "解锁account账户余额不足userId=" + lockedPosition.getUserId() + ",tradeNo=" + tradeNo);

        Account account4Update = new Account();
        account4Update.setId(account.getId());
        account4Update.setAccLockPosition(account.getAccLockPosition().subtract(amount));
        int accountResult = accountRepository.updateById(account4Update);
        Assert.isTrue(accountResult > 0, "解锁更新账户account失败userId=" + lockedPosition.getUserId() + ",tradeNo=" + tradeNo);
    }

}
