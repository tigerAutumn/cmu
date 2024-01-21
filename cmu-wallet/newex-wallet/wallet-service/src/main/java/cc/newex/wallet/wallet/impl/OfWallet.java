package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.OfCommand;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.rpc.EthRawTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractEthLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import com.alibaba.fastjson.JSONObject;
import com.ofbank.Wallet;
import io.netty.util.internal.ConcurrentSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.spongycastle.math.ec.ECPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Slf4j
@Component
public class OfWallet extends AbstractEthLikeWallet implements IWallet {


    @Autowired
    OfCommand command;

    @Autowired
    OrcTokenWallet orcTokenWallet;

    @Value("${newex.of.withdraw.address}")
    private String ofWithdrawAddress;

    @PostConstruct
    public void init() {
        this.RESERVED = new BigDecimal("1");
        super.setCommand(this.command);
        super.setWithdrawAddress(this.ofWithdrawAddress);

    }

    @Override
    public void updateTXConfirmation(final CurrencyEnum currency) {
        super.updateTXConfirmation(CurrencyEnum.OF);
        CurrencyEnum.ORC_TOKEN_SET.parallelStream().forEach(super::updateTXConfirmation);
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.OF;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.OF.getDecimal();
    }

    @Override
    public Address genNewAddress(Long userId, Integer biz) {
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} begin", userId, biz, this.getCurrency().name());

        AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(userId).andBizEqualTo(biz);

        ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        List<Address> addressList = this.addressService.getByExample(example, table);
        int index = 0;
        /**
         * 获取该userId在biz业务线下面已经生成了多少地址
         */
        if (!CollectionUtils.isEmpty(addressList)) {

            Optional<Address> maxAddress = addressList.stream().max(Comparator.comparing(Address::getIndex));
            index = maxAddress.get().getIndex() + 1;
        }

        /*
        hd的公钥推导path: bip44-currency-biz-userId-index
         */
        CurrencyEnum currency = this.getCurrency();
        ECKey ecKey = this.pubKeyConfig.NODE2.getChild(44).getChild(currency.getIndex()).getChild(biz).getChild(userId.intValue()).getChild(index).getEcKey();
        ECPoint point = ecKey.getPubKeyPoint();
        org.ethereum.crypto.ECKey ethEcKey = org.ethereum.crypto.ECKey.fromPublicOnly(point);
        String addressStr = Wallet.GenerateAddressFromPub(ethEcKey.getPubKey());

        Address address = new Address();
        address.setAddress(addressStr);
        address.setBiz(biz);
        address.setCurrency(this.getCurrency().getName());
        address.setUserId(userId);
        address.setIndex(index);
        this.addressService.add(address, table);
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} end", userId, biz, this.getCurrency().name());
        return address;
    }

    @Override
    public List<TransactionDTO> findRelatedTxs(Long height) {
        JSONObject block = this.command.getBlock(height);
        if (ObjectUtils.isEmpty(block)) {
            log.error("findRelatedTxs error, block is null,height:{}", height);
            return null;
        }
        List<EthRawTransaction> ofRawTransactions = block.getJSONArray("transactions").toJavaList(EthRawTransaction.class);
        if (CollectionUtils.isEmpty(ofRawTransactions)) {
            log.error("ofRawTransactions error, transactions is null,height:{}", height);
            return new LinkedList<>();
        }

        Long lastBlockNum = this.getBestHeight();

        //存储参与交易的地址，方便更新地址中的余额
        Set<String> relatedAddress = new ConcurrentSet<>();
        List<AccountTransaction> accountTransactions = ofRawTransactions.parallelStream().map((tx) -> {

            String txId = tx.getHash();
            //先检测是不是我们发出的交易
            this.updateWithdrawTXId(txId, this.getCurrency());

            Address fromAddress = this.searchAddress(tx.getFrom(), this.getCurrency());
            //收集发送地址
            if (!ObjectUtils.isEmpty(fromAddress)) {
                relatedAddress.add(fromAddress.getAddress());
            }

            String addressStr = tx.getTo();
            Address address = this.searchAddress(addressStr, this.getCurrency());
            if (ObjectUtils.isEmpty(address)) {
                return null;
            }
            if (this.checkInternalTransferTx(tx.getFrom(), txId)) {
                return null;
            }

            AccountTransaction accountTransaction = AccountTransaction.builder()
                    .txId(txId)
                    .blockHeight(height)
                    .address(addressStr)
                    .balance(new BigDecimal(tx.getValue()))
                    .confirmNum(lastBlockNum - height + 1)
                    .biz(address.getBiz())
                    .currency(this.getCurrency().getIndex())
                    .status((byte) Constants.WAITING)
                    .createDate(Date.from(Instant.now()))
                    .updateDate(Date.from(Instant.now())).build();

            return accountTransaction;

        }).filter((acTx) -> {
            if (!ObjectUtils.isEmpty(acTx)) {
                //收集接收地址
                relatedAddress.add(acTx.getAddress());
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        //更新币种余额
        OfWallet selfReference = this.getSelf(this.getClass());
        selfReference.updateAddressBalance(relatedAddress);

        List<TransactionDTO> dtos = new LinkedList<>();
        if (!CollectionUtils.isEmpty(accountTransactions)) {
            ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
            this.accountTransactionService.batchAddOnDuplicateKey(accountTransactions, table);
            dtos = accountTransactions.parallelStream().map(this::convertAccountTxToDto).collect(Collectors.toList());
        }

        List<TransactionDTO> orcTokenTransactions = this.orcTokenWallet.findRelatedTxs(height, this.height);
        if (!org.springframework.util.CollectionUtils.isEmpty(orcTokenTransactions)) {
            dtos.addAll(orcTokenTransactions);
        }

        return dtos;
    }

    /**
     * 提现方法
     *
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class, isolation = Isolation.READ_UNCOMMITTED)
    public boolean withdraw(final WithdrawRecord record) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
        if (CurrencyEnum.ORC_TOKEN_SET.contains(currency)) {
            return this.orcTokenWallet.withdraw(record);
        } else {
            return super.withdraw(record);
        }
    }

    @Override
    public boolean checkAddress(String addressStr) {
        boolean valid = false;
        if (StringUtils.hasText(addressStr)) {
            try {
                String rex = "^0x[a-fA-F0-9]{50}$";
                if (addressStr.matches(rex)) {
                    valid = true;
                }
            } catch (AddressFormatException e) {
                log.error("{} is not valid", addressStr, e);
            }
        }
        return valid;

    }

    //更新钱包中的币余额
    @Override
    public void updateTotalCurrencyBalance() {
        super.updateTotalCurrencyBalance();
        this.orcTokenWallet.updateTotalCurrencyBalance();
    }

    @Override
    protected BigDecimal getBalance(String address, CurrencyEnum currency) {
        String tranAmount = this.command.getBalance(address, "latest");
        return new BigDecimal(tranAmount);
    }

    @Override
    protected BigDecimal gas() {
        return new BigDecimal("21000");
    }

    @Override
    protected BigDecimal gasPrice(CurrencyEnum currency) {
        return new BigDecimal("5000000000000");
    }

    @Override
    protected BigDecimal transferGasPrice(CurrencyEnum currency) {
        return new BigDecimal("5000000000000");
    }
}
