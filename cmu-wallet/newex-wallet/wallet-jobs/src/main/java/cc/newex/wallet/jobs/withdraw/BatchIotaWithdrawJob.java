package cc.newex.wallet.jobs.withdraw;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.IWallet;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 下线
 *
 * @author newex-team
 * @data 10/04/2018
 */
@Slf4j
// @Component
// @ElasticJobExtConfig(cron = "10 1/1 * * * ?")
public class BatchIotaWithdrawJob extends AbstractBatchWithdrawJob implements SimpleJob {

    @Autowired
    protected ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        this.currency = CurrencyEnum.IOTA;
    }

    @Override
    protected WithdrawTransaction buildTransaction(final List<WithdrawRecord> records) {
        log.info("{} buildTransaction begin", this.currency);
        final int size = 1;
        WithdrawTransaction transaction = null;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (final WithdrawRecord record : records) {
            totalAmount = totalAmount.add(record.getBalance());
        }
        final ShardTable table = ShardTable.builder().prefix(this.currency.getName()).build();
        final AddressExample example = new AddressExample();
        example.createCriteria().andStatusEqualTo((byte) Constants.SIGNING).andBalanceGreaterThan(BigDecimal.ZERO);
        final PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(size);
        pageInfo.setStartIndex(0);
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);

        //选取utxo
        final LinkedList<Address> addresses = new LinkedList<>();
        BigDecimal walletAmount = BigDecimal.ZERO;
        while (true) {
            final List<Address> tmps = this.addressService.getByPage(pageInfo, example, table);
            if (CollectionUtils.isEmpty(tmps)) {
                log.error("{} buildTransaction failed , wallet balance is not enough", this.currency);
                return null;
            }
            addresses.addAll(tmps);
            //因为page size 为 1，所以查询结果中只有一条数据
            final Address address = tmps.get(0);
            walletAmount = walletAmount.add(address.getBalance());
            log.error("iota buildTransaction,balanceCompareTo {} walletAmount:{},totalAmount:{}", this.currency, walletAmount, totalAmount);
            if (walletAmount.compareTo(totalAmount) >= 0) {
                break;
            }
            pageInfo.setStartIndex(pageInfo.getStartIndex() + size);
        }

        final JSONObject signature = new JSONObject();
        final IWallet wallet = this.walletContext.getWallet(this.currency);
        final Address changeAddress = wallet.genNewAddress(Constants.USER_ID, Constants.BIZ);
        signature.put("addresses", addresses);
        signature.put("withdraw", records);
        signature.put("changeAddress", changeAddress.getAddress());

        transaction = WithdrawTransaction.builder()
                .balance(walletAmount)
                .currency(this.currency.getIndex())
                .status(Constants.SIGNING)
                .txId("singing")
                .signature(signature.toJSONString())
                .build();
        this.transactionService.add(transaction, table);

        final Integer transactionId = transaction.getId();

        //更新utxo和WithdrawRecord的status
        final BatchIotaWithdrawJob self = this.applicationContext.getBean(this.getClass());
        addresses.parallelStream().forEach((address) -> {
            //复用 nonce ，把地址和交易关联
            address.setNonce(transactionId);
            self.updateAddrStatus(address);
        });

        records.parallelStream().forEach((record) -> {
            record.setStatus((byte) Constants.SIGNING);
            record.setTxId(transactionId.toString());
            record.setUpdateDate(Date.from(Instant.now()));
        });
        this.recordService.batchEdit(records, table);

        log.info("{} buildTransaction end", this.currency);
        return transaction;

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAddrStatus(final Address address) {

        final ShardTable table = ShardTable.builder().prefix(this.currency.getName()).build();
        final AddressExample addressExample = new AddressExample();
        addressExample.createCriteria().andAddressEqualTo(address.getAddress()).andUserIdGreaterThan(-1L).andStatusEqualTo((byte) Constants.SIGNING);

        final Address latest = this.addressService.getAndLockOneByExample(addressExample, table);
        if (ObjectUtils.isEmpty(latest)) {
            log.error("Can not find address:{} in table", address.getAddress());
            throw new RuntimeException("Can not find " + address.getAddress() + " in table");
        } else {
            address.setBalance(latest.getBalance());
            address.setStatus((byte) Constants.SENT);
            address.setUpdateDate(java.util.Date.from(Instant.now()));
            this.addressService.editById(address, table);
        }
    }
}
