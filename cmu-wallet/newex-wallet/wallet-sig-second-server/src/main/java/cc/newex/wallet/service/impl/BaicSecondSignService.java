package cc.newex.wallet.service.impl;

import cc.newex.wallet.baicwallet.model.Transaction;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import io.eblock.eos4j.Ecc;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sdk.bip.Bip32Node;

/**
 * @author newex-team
 * @data 2019/1/11
 */
@Component
@Slf4j
public class BaicSecondSignService implements ISignService {
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BAIC;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        String signResult = "";
        try {
            final String sigStr = transaction.getSignature();
            final JSONObject sigJson = JSONObject.parseObject(sigStr);
            final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
            final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);

            final String from = address.getAddress();
            final String action_data = sigJson.getString("abiJsonToBinData");
            final String headBlockTime = sigJson.getString("headBlockTime");

            final Long lastIrreversibleBlockNum = sigJson.getLong("lastIrreversibleBlockNum");
            final Long refBlockPrefix = sigJson.getLong("refBlockPrefix");
            final String chainId = sigJson.getString("chainId");
            final String action_name = sigJson.getString("action_name");

            final ECKey ecKey = this.getKeyByAddress(address);
            final String priKey = Ecc.seedPrivate(ecKey.getPrivKey().toString());
            final String pubKey = Ecc.privateToPublic(Ecc.seedPrivate(ecKey.getPrivKey().toString())).replace("EOS", "BAIC");
            final String account = this.getAccount(currency.getContractAddress());
            if (StringUtils.isEmpty(account)) {
                log.error("signTransaction err,currency {},account is null", currency.getName());
                return signResult;
            }
            final Transaction transaction1 = Transaction.buildTransaction(account, action_name, action_data, from,
                    lastIrreversibleBlockNum, refBlockPrefix, headBlockTime);
            signResult = transaction1.trySignDiges(chainId, pubKey, priKey);

        } catch (final Throwable e) {
            log.error("{} signTransaction error", this.getCurrency(), e);
        }
        return signResult;
    }

    private ECKey getKeyByAddress(final Address address) {
        final Bip32Node node = BipNodeUtil.getMainBipNODE();
        final Bip32Node dusdActiveNode = node.getChildH(CurrencyEnum.BAIC.getIndex())
                .getChild(44)
                //biz
                .getChild(address.getBiz())
                //userid
                .getChild(address.getUserId().intValue())
                .getChild(address.getIndex())
                //0=owner 1=active
                .getChild(1);
        final ECKey dusdKey = dusdActiveNode.getEcKey();
        return dusdKey;
    }

    private String getAccount(final String contractAddress) {
        final String[] dustContranct = contractAddress.split("-");
        if (dustContranct.length != 3) {
            log.error("{} dustContranct.Len !=3，contractAddress: {} ", this.getCurrency(), contractAddress);
            return "";
        }
        return dustContranct[1];
    }

}
