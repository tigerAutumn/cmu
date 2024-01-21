package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import cc.newex.wallet.xmrwallet.crypto.KeyPair;
import cc.newex.wallet.xmrwallet.model.NetworkType;
import cc.newex.wallet.xmrwallet.model.Node;
import cc.newex.wallet.xmrwallet.model.Wallet;
import cc.newex.wallet.xmrwallet.model.WalletManager;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.spongycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import java.io.File;

import static cc.newex.wallet.utils.Constants.SETPEXPORTKEYIMAGES;
import static cc.newex.wallet.utils.Constants.SETPSIGN;

/**
 * @author newex-team
 * @data 2018/12/21
 */
@Component
@Slf4j
public class XmrSecondSignService implements ISignService {

    private final File walletFile = new File("/home/coinmex/xmr/cmx_wallet");
//     private File walletFile = new File("/tmp/cmx_wallet");

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.XMR;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        final String sigStr = transaction.getSignature();
        final JSONObject sigJson = JSONObject.parseObject(sigStr);
        final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
        final String setp = sigJson.getString("setp");
        final String outputs = sigJson.getString("outputs");
        final String unsign = sigJson.getString("unsign");
        final cc.newex.wallet.xmrwallet.model.Address xmrAddress = this.getXmrAddress(address);
        final String spentKey = Hex.toHexString(xmrAddress.getSpentKey().getXPri());
        final String viewKey = Hex.toHexString(xmrAddress.getViewKey().getXPri());

        final Wallet wallet = this.openWallet(address.getAddress(), viewKey, spentKey,
                spentKey, NetworkType.NetworkType_Mainnet);
        if (wallet.getStatus() != Wallet.Status.Status_Ok) {
            log.error("XMR openWallet error {}", wallet.getErrorString());
            wallet.close();
            return "";
        }

        String setpRet = "";
        boolean setpStatus = false;
        switch (setp) {
            case SETPEXPORTKEYIMAGES:
                if (!wallet.importOutputs(outputs)) {
                    log.error("XMR importOutputs error {}", wallet.getErrorString());
                    break;
                }
                setpRet = wallet.exportKeyImages();
                if (!wallet.getErrorString().equals("")) {
                    log.error("XMR exportKeyImages error {}", wallet.getErrorString());
                    break;
                }
                setpStatus = true;
                break;
            case SETPSIGN:
                setpRet = wallet.sign(unsign);
                if (!wallet.getErrorString().equals("")) {
                    log.error("XMR sign error {}", wallet.getErrorString());
                    break;
                }
                setpStatus = true;
                break;
            default:
                log.error("unknown setp id: {}", setp);
                break;
        }

        if (!setpStatus) {
            log.error("XMR signTransaction error");
        }

        wallet.close();
        return setpRet;
    }

    private Wallet openWallet(final String addressString, final String viewKeyString, final String spendKeyString, final String password, final NetworkType networkType) {
        final Node node = Node.builder()
                .address("127.0.0.1:28082")
                .username("")
                .password("")
                .networkType(networkType)
                .build();

        final WalletManager walletManager = WalletManager.getInstance();
        walletManager.setDaemon(node);

        Wallet wallet = null;
        if (this.walletFile.exists()) {
            wallet = walletManager.openWallet(this.walletFile.getAbsolutePath(), password);
        } else {
            wallet = walletManager.createWalletFromKeys(this.walletFile, password, "English", networkType, 0,
                    addressString,
                    viewKeyString, spendKeyString);
        }
        return wallet;
    }

    private cc.newex.wallet.xmrwallet.model.Address getXmrAddress(final Address address) {
        final Bip32Node node = BipNodeUtil.getBipNODE(address);
        final cc.newex.wallet.xmrwallet.model.Address xmrAddress = new cc.newex.wallet.xmrwallet.model.Address(KeyPair.fromPrivateKey(node.getEcKey().getPrivKeyBytes()),
                KeyPair.fromPrivateKey(node.getChild(0).getEcKey().getPrivKeyBytes()));
        return xmrAddress;
    }

}
