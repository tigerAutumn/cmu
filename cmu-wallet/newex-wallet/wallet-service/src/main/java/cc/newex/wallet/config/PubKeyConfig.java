package cc.newex.wallet.config;

import cc.newex.wallet.utils.Constants;
import org.bitcoinj.core.ECKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;
import sdk.core.MultiSignAddressGenerator;

import javax.annotation.PostConstruct;

/**
 * @author newex-team
 * @data 28/03/2018
 */
@Component
public class PubKeyConfig {

    public Bip32Node NODE1;
    public Bip32Node NODE2;
    public Bip32Node NODE3;
    @Value("${newex.wallet.pubKey1}")
    private String pub1;
    @Value("${newex.wallet.pubKey2}")
    private String pub2;
    @Value("${newex.wallet.pubKey3}")
    private String pub3;

    @PostConstruct
    public void init() {
        this.NODE1 = Bip32Node.decode(this.pub1);
        this.NODE2 = Bip32Node.decode(this.pub2);
        this.NODE3 = Bip32Node.decode(this.pub3);
    }

    //hd的公钥推导path: bip44-currency-biz-userId-index
    public String genThree_TwoAddress(final int currency, final int userId, final int biz, final int index) {
        final MultiSignAddressGenerator generator = new MultiSignAddressGenerator();
        final ECKey ecKey1 = this.NODE1.getChild(44).getChild(currency).getChild(biz).getChild(userId).getChild(index).getEcKey();
        final ECKey ecKey2 = this.NODE2.getChild(44).getChild(currency).getChild(biz).getChild(userId).getChild(index).getEcKey();
        final ECKey ecKey3 = this.NODE3.getChild(44).getChild(currency).getChild(biz).getChild(userId).getChild(index).getEcKey();
        generator.addECKey(ecKey1);
        generator.addECKey(ecKey2);
        generator.addECKey(ecKey3);
        final String address = generator.generateAddress(Constants.NET_PARAMS, 2);
        return address;
    }

    /**
     * 生成redeem script
     *
     * @param currency
     * @param address
     * @return
     */
//    public String genScript(final int currency, final Address address) {
//        final MultiSignAddressGenerator generator = new MultiSignAddressGenerator();
//        final ECKey ecKey1 = this.NODE1.getChild(44).getChild(currency).getChild(address.getBiz()).getChild(address.getUserId().intValue()).getChild(address.getIndex()).getEcKey();
//        final ECKey ecKey2 = this.NODE2.getChild(44).getChild(currency).getChild(address.getBiz()).getChild(address.getUserId().intValue()).getChild(address.getIndex()).getEcKey();
//        final ECKey ecKey3 = this.NODE3.getChild(44).getChild(currency).getChild(address.getBiz()).getChild(address.getUserId().intValue()).getChild(address.getIndex()).getEcKey();
//        generator.addECKey(ecKey1);
//        generator.addECKey(ecKey2);
//        generator.addECKey(ecKey3);
//        generator.generateAddress(Constants.NET_PARAMS, 2);
//        final String scriptStr = generator.getScriptStr();
//        return scriptStr;
//    }

}
