package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.DashCommand;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.wallet.AbstractBtcLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.dashj.DashSdk;
import org.dashj.params.MainNetParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author newex-team
 * @data 27/03/2018
 */
@Slf4j
@Component
public class DashWallet extends AbstractBtcLikeWallet implements IWallet {

    @Autowired
    DashCommand command;
    private Bip32Node dashNode;
    @Value("${newex.dash.pubkey}")
    private String dashPubkey;

    @PostConstruct
    public void init() {
        super.setCommand(this.command);

        if (StringUtils.isNotBlank(this.dashPubkey)) {
            this.dashNode = Bip32Node.decode(this.dashPubkey);
        }

    }

    @Override
    public BigDecimal getDecimal() {
        return this.getCurrency().getDecimal();
    }

    @Override
    public Long getBestHeight() {
        return super.getBestHeight();
    }

    public MainNetParams getDashNetworkParameters() {
        return org.dashj.params.MainNetParams.get();
    }

    @Override
    public NetworkParameters getNetworkParameters() {
        return org.bitcoinj.params.MainNetParams.get();
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.DASH;
    }

    @Override
    public Address genNewAddress(final Long userId, final Integer biz) {
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
        final ECKey ecKey = this.dashNode.getChild(44).getChild(currency.getIndex()).getChild(biz).getChild(userId.intValue()).getChild(index).getEcKey();
        final String newAddress = DashSdk.getNewAddress(ecKey.getPubKeyHash());
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
    public boolean checkAddress(final String addressStr) {
        return DashSdk.checkAddress(addressStr);
    }

}
