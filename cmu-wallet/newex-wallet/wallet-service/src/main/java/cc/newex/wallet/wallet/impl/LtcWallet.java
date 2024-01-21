package cc.newex.wallet.wallet.impl;

import cc.newex.wallet.command.LtcCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.rpc.BtcLikeRawTransaction;
import cc.newex.wallet.pojo.rpc.ScriptPubKey;
import cc.newex.wallet.pojo.rpc.TxOutput;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractBtcLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.litecoinj.core.Address;
import org.litecoinj.core.NetworkParameters;
import org.litecoinj.core.Transaction;
import org.litecoinj.core.Utils;
import org.litecoinj.integrations.LitecoinNetParameters;
import org.litecoinj.integrations.LitecoinTestNetParameters;
import org.litecoinj.script.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import sdk.header.LiteMainNetParam;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@Slf4j
@Component
public class LtcWallet extends AbstractBtcLikeWallet implements IWallet {
    @Autowired
    LtcCommand command;

    @PostConstruct
    public void init() {
        super.setCommand(this.command);
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.LTC;
    }

    public NetworkParameters getLiteNetworkParameters() {
        if (this.CONSTANT.NETWORK.equals("test")) {
            return LitecoinTestNetParameters.get();
        }

        return LitecoinNetParameters.get();
    }

    @Override
    public org.bitcoinj.core.NetworkParameters getNetworkParameters() {
        return LiteMainNetParam.get();
        //throw new RuntimeException("Ltc not support this method");
    }

    public List<TransactionDTO> convertFromLtcBjTx(final Transaction transaction) {
        final byte[] data = transaction.bitcoinSerialize();
        final org.bitcoinj.core.Transaction btcTx = new org.bitcoinj.core.Transaction(Constants.NET_PARAMS, data);
        return this.convertFromBjTx(btcTx, 0L);
    }

    @Override
    public BtcLikeRawTransaction getRawTransaction(final String txid) {
        final BtcLikeRawTransaction rawTx = this.command.getRawTransaction(txid, true);
        final List<TxOutput> outs = rawTx.getVout();
        outs.parallelStream().forEach((out) -> {
            try {
                final ScriptPubKey pubKey = out.getScriptPubKey();
                final List<String> addresses = pubKey.getAddresses();
                if (CollectionUtils.isEmpty(addresses)) {
                    return;
                }
                addresses.clear();
                final Script script = new Script(Utils.HEX.decode(out.getScriptPubKey().getHex()));
                final String addr = script.getToAddress(this.getLiteNetworkParameters()).toString();
                addresses.add(addr);
            } catch (final Throwable e) {
                log.error("ScriptException error, txid:{}", txid);
            }

        });
        return rawTx;
        //final Transaction transaction = new Transaction(Constants.NET_PARAMS, Utils.HEX.decode(rawTx));
        //return transaction;
        //return this.command.decodeRawTransactionStr(rawTx);
    }

    @Override
    public boolean checkAddress(final String addressStr) {
        boolean valid = false;
        if (StringUtils.hasText(addressStr)) {
            try {
                final Address address = new Address(this.getLiteNetworkParameters(), addressStr);
                if (!ObjectUtils.isEmpty(address)) {
                    valid = true;
                }
            } catch (final Throwable e) {
                LtcWallet.log.error("{} is not valid", addressStr, e);
            }
        }
        return valid;

    }

}
