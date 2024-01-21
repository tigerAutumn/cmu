package cc.newex.wallet.config;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.utils.Constants;
import lombok.Data;
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
@Data
public class PubKeyConfig {
    @Value("${newex.wallet.pubKey1}")
    private String pub1;
    @Value("${newex.wallet.pubKey2}")
    private String pub2;
    @Value("${newex.wallet.pubKey3}")
    private String pub3;

    private Bip32Node NODE1;
    private Bip32Node NODE2;
    private Bip32Node NODE3;

    @PostConstruct
    public void init() {
        this.NODE1 = Bip32Node.decode(this.pub1);
        this.NODE2 = Bip32Node.decode(this.pub2);
        this.NODE3 = Bip32Node.decode(this.pub3);
    }

    public String genScript(final Address address) {
        final CurrencyEnum currencyEnum = CurrencyEnum.parseName(address.getCurrency());
        final MultiSignAddressGenerator generator = new MultiSignAddressGenerator();
        final ECKey ecKey1 = this.NODE1.getChild(44).getChild(currencyEnum.getIndex()).getChild(address.getBiz()).getChild(address.getUserId().intValue()).getChild(address.getIndex()).getEcKey();
        final ECKey ecKey2 = this.NODE2.getChild(44).getChild(currencyEnum.getIndex()).getChild(address.getBiz()).getChild(address.getUserId().intValue()).getChild(address.getIndex()).getEcKey();
        final ECKey ecKey3 = this.NODE3.getChild(44).getChild(currencyEnum.getIndex()).getChild(address.getBiz()).getChild(address.getUserId().intValue()).getChild(address.getIndex()).getEcKey();
        generator.addECKey(ecKey1);
        generator.addECKey(ecKey2);
        generator.addECKey(ecKey3);
        final String addressGen = generator.generateAddress(Constants.NET_PARAMS, 2);
        final String scriptStr = generator.getScriptStr();
        return scriptStr;
    }

}
