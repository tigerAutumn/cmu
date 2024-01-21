package cc.newex.wallet.wallet.impl;

import cc.newex.wallet.command.BchCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.rpc.BtcLikeRawTransaction;
import cc.newex.wallet.pojo.rpc.ScriptPubKey;
import cc.newex.wallet.pojo.rpc.TxOutput;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractBtcLikeWallet;
import cc.newex.wallet.wallet.IWallet;
import lombok.extern.slf4j.Slf4j;
import org.bitcoincashj.core.*;
import org.bitcoincashj.params.MainNetParams;
import org.bitcoincashj.params.TestNet3Params;
import org.bitcoincashj.script.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;

//import sdk.bitcoincashj.core.MultiSignAddressGenerator;
//import sdk.core.MultiSignAddressGenerator;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@Slf4j
@Component
public class BchWallet extends AbstractBtcLikeWallet implements IWallet {
    @Autowired
    BchCommand bchCommand;

    @PostConstruct
    public void init() {
        super.setCommand(this.bchCommand);
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.BCH;
    }

    public NetworkParameters getBchNetworkParameters() {
        if (this.CONSTANT.NETWORK.equals("test")) {
            return TestNet3Params.get();
        }

        return MainNetParams.get();
    }

    @Override
    public org.bitcoinj.core.NetworkParameters getNetworkParameters() {
        return org.bitcoinj.params.MainNetParams.get();
        //throw new RuntimeException("Bch not support this method");
    }

    public List<TransactionDTO> convertFromBchBjTx(final Transaction transaction) {
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
                final String addr = script.getToAddress(this.getBchNetworkParameters()).toBase58();
                addresses.add(addr);
            } catch (final ScriptException e) {
                BchWallet.log.error("ScriptException error, txid:{}", txid);
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
                final String address = BitcoinCashAddressConverter.convertLegacy2NewFormatAddrStr(this.getBchNetworkParameters(), addressStr);
                if (!ObjectUtils.isEmpty(address)) {
                    valid = true;
                }
            } catch (final Throwable e) {
                BchWallet.log.error("{} is not valid", addressStr, e);
            }
        }
        return valid;

    }


}
