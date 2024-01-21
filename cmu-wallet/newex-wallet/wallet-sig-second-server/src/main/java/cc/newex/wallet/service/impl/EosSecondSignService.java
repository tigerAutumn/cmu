package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import io.eblock.eos4j.Ecc;
import io.eblock.eos4j.Rpc;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sdk.bip.Bip32Node;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Component
@Slf4j
public class EosSecondSignService implements ISignService {
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.EOS;
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
            final String toAddr = sigJson.getString("to");
            final String[] addressAndTag = toAddr.split(":");
            if (addressAndTag.length != 2) {
                log.error("{} signTransaction error,addressAndTag.Len !=2 ", currency);
                return signResult;
            }
            final String to = addressAndTag[0];
            final String memo = addressAndTag[1];

            final BigDecimal value = transaction.getBalance();
            final String quantity = value + " " + currency.name();

            final Long headBlockTime = sigJson.getLong("headBlockTime");
            final Long lastIrreversibleBlockNum = sigJson.getLong("lastIrreversibleBlockNum");
            final Long refBlockPrefix = sigJson.getLong("refBlockPrefix");
            final String chainId = sigJson.getString("chainId");

            final String priKey = this.getKeyByAddress(address);
            final String account = this.getAccount(currency.getContractAddress());
            if (StringUtils.isEmpty(account)) {
                log.error("signTransaction err,currency {},account is null", currency.getName());
                return signResult;
            }
            log.error("buildSignTransaction account:{} from:{} , to:{} , quantity:{} , memo:{} , headBlockTime:{} , lastIrreversibleBlockNum:{} , refBlockPrefix:{} , chainId:{} ",
                    account, from, to, quantity, memo, headBlockTime, lastIrreversibleBlockNum, refBlockPrefix, chainId);
            signResult = Rpc.buildSignTransactionAsset(priKey, account, from, to, quantity, memo
                    , headBlockTime, lastIrreversibleBlockNum, refBlockPrefix, chainId);
        } catch (final Throwable e) {
            log.error("{} signTransaction error", this.getCurrency(), e);
        }
        return signResult;
    }

    private String getKeyByAddress(final Address address) {
        final Bip32Node node = BipNodeUtil.getMainBipNODE();
        final Bip32Node eosActiveNode = node.getChildH(CurrencyEnum.EOS.getIndex())
                .getChild(44)
                //biz
                .getChild(address.getBiz())
                //userid
                .getChild(address.getUserId().intValue())
                .getChild(address.getIndex())
                //0=owner 1=active
                .getChild(1);
        final ECKey eosKey = eosActiveNode.getEcKey();
        return Ecc.seedPrivate(eosKey.getPrivKey().toString());
    }

    private String getAccount(final String contractAddress) {
        final String[] eosContranct = contractAddress.split("-");
        if (eosContranct.length != 3) {
            log.error("{} eosContranct.Len !=3，contractAddress: {} ", this.getCurrency(), contractAddress);
            return "";
        }
        return eosContranct[1];
    }

}
