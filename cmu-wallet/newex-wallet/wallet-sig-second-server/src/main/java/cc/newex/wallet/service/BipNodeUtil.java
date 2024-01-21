package cc.newex.wallet.service;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.signature.KeyConfig;
import com.ofbank.Wallet;
import org.spongycastle.util.encoders.Hex;
import org.springframework.util.ObjectUtils;
import sdk.bip.Bip32Node;

/**
 * @author newex-team
 * @data 13/04/2018
 */

public class BipNodeUtil {
    private static Bip32Node NODE;

    public static Bip32Node getBipNODE(Address address) {
        if (ObjectUtils.isEmpty(BipNodeUtil.NODE)) {
            String mk = KeyConfig.getValue("masterNode");
            //final String dk = SecretConfig.decryptKey(mk);
            BipNodeUtil.NODE = Bip32Node.decode(mk);
        }
        CurrencyEnum currencyEnum = CurrencyEnum.parseName(address.getCurrency());
        Bip32Node node = BipNodeUtil.NODE.getChild(44)
                .getChild(currencyEnum.getIndex())
                .getChild(address.getBiz())
                .getChild(address.getUserId().intValue())
                .getChild(address.getIndex());
        return node;
    }

    public static Bip32Node getMainBipNODE() {
        if (ObjectUtils.isEmpty(BipNodeUtil.NODE)) {
            String mk = KeyConfig.getValue("masterNode");
            BipNodeUtil.NODE = Bip32Node.decode(mk);
        }
        return BipNodeUtil.NODE;
    }

}
