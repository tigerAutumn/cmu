package cc.newex.wallet.wallet.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.wallet.command.LuckyWinCommand;
import cc.newex.wallet.criteria.AddressExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.wallet.AbstractEthLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import com.chain.web3j.sign.SignManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.ECKey;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Slf4j
@Component
public class LuckyWinWallet extends AbstractEthLikeWallet implements IWallet {
    @Autowired
    LuckyWinCommand command;
    @Value("${newex.luckywin.server}")
    private String serverUrl;

    @Value("${newex.luckywin.withdraw.address}")
    private String luckywinWithdrawAddress;
    @Value("${newex.luckywin.pubkey}")
    private String luckywinPubkey;

    private Bip32Node node;

    @PostConstruct
    public void init() {
        this.RESERVED = new BigDecimal("0.0");
        super.setCommand(this.command);

        if (StringUtils.isNotBlank(this.luckywinPubkey)) {
            this.node = Bip32Node.decode(this.luckywinPubkey);
        }

        super.setWithdrawAddress(this.luckywinWithdrawAddress);
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.LUCKYWIN;
    }

    /**
     * 获得当前币种的精度，用于精度转换
     *
     * @return
     */
    @Override
    public BigDecimal getDecimal() {
        return CurrencyEnum.LUCKYWIN.getDecimal();
    }

    @Override
    protected BigDecimal gasPrice(final CurrencyEnum currency) {
        try {
            final BigInteger gasPrice = SignManager.getGasPrice(this.serverUrl, false);
            return BigDecimal.valueOf(gasPrice.longValue());
        } catch (final Exception e) {
            e.printStackTrace();
            log.error("gasPrice get error {}", e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * 生成新地址
     *
     * @param userId 用户id
     * @param biz    业务类型： spot、c2c 等
     * @return
     */
    @Override
    public Address genNewAddress(final Long userId, final Integer biz) {
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} begin", userId, biz, this.getCurrency().name());
        final AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(userId).andBizEqualTo(biz);

        final ShardTable table = ShardTable.builder().prefix(this.getCurrency().getName()).build();
        final List<Address> addressList = this.addressService.getByExample(example, table);
        int index = 0;
        /**
         * 获取该userId在biz业务线下面已经生成了多少地址
         */
        if (!org.apache.commons.collections4.CollectionUtils.isEmpty(addressList)) {

            final Optional<Address> maxAddress = addressList.stream().max(Comparator.comparing(Address::getIndex));
            index = maxAddress.get().getIndex() + 1;
        }

        /*
        hd的公钥推导path: bip44-currency-biz-userId-index
         */
        final CurrencyEnum currency = this.getCurrency();
        final ECKey ecKey = this.node.getChild(44).getChild(currency.getIndex()).getChild(biz).getChild(userId.intValue()).getChild(index).getEcKey();
        final ECPoint point = ecKey.getPubKeyPoint();
        final org.ethereum.crypto.ECKey ethEcKey = org.ethereum.crypto.ECKey.fromPublicOnly(point);
        final String addressStr = Hex.toHexString(ethEcKey.getAddress());

        final Address address = new Address();
        //luckwin 的地址前缀对外显示 lw ，但是内部使用需要使用0x
        address.setAddress(AbstractEthLikeWallet.ETH_ADDRESS_PREFIX + addressStr);
        address.setBiz(biz);
        address.setCurrency(this.getCurrency().getName());
        address.setUserId(userId);
        address.setIndex(index);
        this.addressService.add(address, table);
//        //luckwin 的地址前缀对外显示 lw ，但是内部使用需要使用0x
//        address.setAddress(LUCKWIN_ADDRESS_PREFIX + addressStr);
        log.info("genNewAddress, userId:{}, biz:{}, currency:{} end", userId, biz, this.getCurrency().name());
        return address;
    }

    @Override
    protected BigDecimal gas() {
        return BigDecimal.valueOf(60000L);
    }

}
