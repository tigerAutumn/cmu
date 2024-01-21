package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.ZcashCommand;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.wallet.AbstractBtcLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bitcoinj.core.ECKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zcash.sdk.ZCashSdk;
import org.zcash.sdk.crypto.Sha256Hash;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author newex-team
 * @date 2018/11/28
 */
@Component
@Slf4j
public class ZcashWallet extends AbstractBtcLikeWallet implements IWallet {


    @Autowired
    private ZcashCommand zcashCommand;

    /**
     * 获得当前币种
     *
     * @return
     */
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.ZEC;
    }

    @PostConstruct
    public void init() {
        super.setCommand(this.zcashCommand);
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return this.getCurrency().getDecimal();
    }

    /**
     * 生成新地址
     *
     * @param userId 用户id
     * @param biz    业务类型： spot、c2c 等
     * @return
     */
    @Override
    public Address genNewAddress(Long userId, Integer biz) {
        final AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(userId).andBizEqualTo(biz);

        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final List<Address> addressList = this.addressService.getByExample(example, table);
        int index = 0;

        //获取该userId在biz业务线下面已经生成了多少地址
        if (!CollectionUtils.isEmpty(addressList)) {

            final Optional<Address> maxAddress = addressList.stream().max(Comparator.comparing(Address::getIndex));
            index = maxAddress.get().getIndex() + 1;
        }
        /*
         * hd的公钥推导path: bip44-currency-biz-userId-index
         */
        final CurrencyEnum currency = this.getCurrency();
        final ECKey ecKey = this.pubKeyConfig.NODE2.getChild(44).getChild(currency.getIndex()).getChild(biz).getChild(userId.intValue()).getChild(index).getEcKey();
        byte[] prePubKeyHash = Sha256Hash.hash(ecKey.getPubKey());
        final String newAddress = ZCashSdk.getNewAddress(prePubKeyHash);
        final Address address = Address.builder()
                .address(newAddress)
                .biz(biz)
                .currency(this.getCurrency().getName())
                .userId(userId)
                .index(index).build();
        this.addressService.add(address, table);
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} end", userId, biz, this.getCurrency().name());
        return address;
    }

    @Override
    public boolean checkAddress(String addressStr) {
        return ZCashSdk.checkAddress(addressStr);
    }

}
