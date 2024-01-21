package cc.newex.wallet.service.impl;

import NEO.Core.SignatureContext;
import NEO.Core.Transaction;
import NEO.Core.TransactionAttribute;
import NEO.Core.TransactionInput;
import NEO.Core.TransactionOutput;
import NEO.Core.TransferTransaction;
import NEO.Cryptography.bip32.Deserializer;
import NEO.Cryptography.bip32.ExtendedPrivateKey;
import NEO.Fixed8;
import NEO.Helper;
import NEO.UInt160;
import NEO.UInt256;
import NEO.Wallets.Account;
import NEO.Wallets.Contract;
import NEO.Wallets.Wallet;
import NEO.sdk.SmartContractTx;
import NEO.sdk.abi.Parameter;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.ISignService;
import cc.newex.wallet.signature.KeyConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static NEO.sdk.SmartContractTx.createCodeParamsScript;

/**
 * @author newex-team
 * @data 2018/6/11
 */
@Component
@Slf4j
public class NeoSecondSignService implements ISignService {

    private ExtendedPrivateKey PRIVATE_KEY;

    private final BigDecimal minGasFee = new BigDecimal("0.00000001");

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.NEO;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        log.info("{} second sign begin", currency.getName());
        if (CurrencyEnum.NEO_ASSET.contains(currency)) {
            return this.signNeoAsset(transaction, currency);
        } else if (CurrencyEnum.NEP5_SET.contains(currency)) {
            return this.signNep5Asset(transaction, currency);
        } else {
            log.error("NeoSecondSignService not support :{}", currency);
            return "";
        }
    }

    private String signNeoAsset(final WithdrawTransaction transaction, final CurrencyEnum currency) {
        try {
            final String sigStr = transaction.getSignature();
            final JSONObject sigJson = JSONObject.parseObject(sigStr);

            final List<Address> addresses = sigJson.getJSONArray("addresses").toJavaList(Address.class);
            final List<WithdrawRecord> records = sigJson.getJSONArray("withdraw").toJavaList(WithdrawRecord.class);

            final List<UtxoTransaction> utxos = sigJson.getJSONArray("utxos").toJavaList(UtxoTransaction.class);

            final String changeAddr = sigJson.getString("changeAddress");
            final TransferTransaction transferTransaction = new TransferTransaction();
            transferTransaction.attributes = new TransactionAttribute[0];
            final Map<UInt160, Account> accountMap = new HashMap<>();
            final Map<UInt160, Contract> contractHashMap = new HashMap<>();

            if (currency == CurrencyEnum.GAS) {
                //GAS 不需要单独设置手续费
                transferTransaction.inputs = new TransactionInput[utxos.size()];
                transferTransaction.outputs = new TransactionOutput[records.size() + 1];
            } else {
                //出了GAS之外，需要单独设置一个gas input作为手续费,增加一个output作为手续费找零
                transferTransaction.inputs = new TransactionInput[utxos.size() + 1];
                transferTransaction.outputs = new TransactionOutput[records.size() + 2];

            }
            int inputIndex = 0;
            for (; inputIndex < utxos.size(); inputIndex++) {
                this.addInput(accountMap, contractHashMap, addresses.get(inputIndex), utxos.get(inputIndex), transferTransaction, inputIndex);
            }

            if (sigJson.containsKey("gasInput")) {
                final UtxoTransaction gasUtxo = sigJson.getJSONObject("gasInput").toJavaObject(UtxoTransaction.class);
                final Address gasAddress = sigJson.getJSONObject("gasAddress").toJavaObject(Address.class);
                this.addInput(accountMap, contractHashMap, gasAddress, gasUtxo, transferTransaction, inputIndex++);
            }

            //transferTransaction.outputs = new TransactionOutput[records.size() + 1];
            BigDecimal totalSent = BigDecimal.ZERO;
            int outputIndex = 0;

            for (; outputIndex < records.size(); outputIndex++) {
                final WithdrawRecord withdrawRecord = records.get(outputIndex);
                totalSent = totalSent.add(withdrawRecord.getBalance());
                this.addOutput(withdrawRecord.getAddress(), withdrawRecord.getBalance(), currency.getContractAddress(), transferTransaction, outputIndex);
            }
            final BigDecimal walletBalance = transaction.getBalance();

            final BigDecimal change = walletBalance.subtract(totalSent);
            this.addOutput(changeAddr, change, currency.getContractAddress(), transferTransaction, outputIndex++);
            if (currency != CurrencyEnum.GAS) {
                //设置手续费找零
                final UtxoTransaction gasChangeUtxo = sigJson.getJSONObject("gasInput").toJavaObject(UtxoTransaction.class);

                final String gasChangeAddress = sigJson.getString("gasChange");
                this.addOutput(gasChangeAddress, gasChangeUtxo.getBalance().subtract(this.minGasFee), CurrencyEnum.GAS.getContractAddress(), transferTransaction, outputIndex++);
            }

            final SignatureContext context = new SignatureContext(transferTransaction);
            for (final UInt160 scriptHash : context.scriptHashes) {
                final Account account = accountMap.get(scriptHash);
                final Contract contract = contractHashMap.get(scriptHash);
                final byte[] signature = context.signable.sign(account);
                context.add(contract, account.publicKey, signature);
            }

            if (context.isCompleted()) {
                transferTransaction.scripts = context.getScripts();
                final String txHex = Helper.toHexString(transferTransaction.toArray());
                return txHex;
            }
        } catch (final Throwable e) {
            log.error("There is a error when sign {} tx", currency.getName(), e);
        }
        return "";
    }

    private String signNep5Asset(final WithdrawTransaction transaction, final CurrencyEnum currency) {
        try {
            final String sigStr = transaction.getSignature();
            final JSONObject sigJson = JSONObject.parseObject(sigStr);

            final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);
            final String receiverAddress = sigJson.getString("to");
            final BigDecimal value = sigJson.getBigDecimal("value").multiply(currency.getDecimal());

            String contractAddress = currency.getContractAddress().replace("0x", "");
            contractAddress = Helper.reverse(contractAddress);

            final List list = new ArrayList<>();
            list.add("transfer".getBytes());
            final List tmp = new ArrayList<>();

            final Parameter sender = new Parameter();
            sender.type = "ByteArray";
            sender.name = "from";
            sender.setValue(Wallet.toScriptHash(address.getAddress()).toArray());

            final Parameter receiver = new Parameter();
            receiver.name = "to";
            receiver.type = "ByteArray";
            receiver.setValue(Wallet.toScriptHash(receiverAddress).toArray());

            final Parameter amount = new Parameter();
            amount.type = "Integer";
            amount.name = "value";
            amount.setValue(value.longValue());

            tmp.add(JSON.parseObject(sender.getValue(), byte[].class));
            tmp.add(JSON.parseObject(receiver.getValue(), byte[].class));
            tmp.add(JSON.parseObject(amount.getValue(), Long.class));

            if (list.size() > 0) {
                list.add(tmp);
            }
            byte[] params = createCodeParamsScript(list);
            params = Helper.addBytes(params, new byte[]{0x67});
            params = Helper.addBytes(params, Helper.hexToBytes(contractAddress));
            final Account account = this.getAccountByAddress(address);

            final Map<UInt160, Account> accountMap = new HashMap<>();
            final Map<UInt160, Contract> contractHashMap = new HashMap<>();
            final Transaction tx = SmartContractTx.makeInvocationTransaction(params, account.publicKey);
            tx.inputs = new TransactionInput[1];
            final UtxoTransaction gasUtxo = sigJson.getJSONObject("gasInput").toJavaObject(UtxoTransaction.class);
            final Address gasAddress = sigJson.getJSONObject("gasAddress").toJavaObject(Address.class);
            this.addInput(accountMap, contractHashMap, gasAddress, gasUtxo, tx, 0);

            tx.outputs = new TransactionOutput[1];
            final String gasChangeAddress = sigJson.getString("gasChange");
            this.addOutput(gasChangeAddress, gasUtxo.getBalance().subtract(this.minGasFee), CurrencyEnum.GAS.getContractAddress(), tx, 0);

            final HashSet<UInt160> hashSet = new HashSet<>();
            hashSet.add(Wallet.toScriptHash(gasAddress.getAddress()));
            hashSet.add(Wallet.toScriptHash(address.getAddress()));

            final UInt160[] hashArray = hashSet.stream().sorted().toArray(UInt160[]::new);

            final SignatureContext context = new SignatureContext(tx, hashArray);
            final Contract contract = Contract.createSignatureContract(account.publicKey);

            byte[] signature = context.signable.sign(account);
            context.add(contract, account.publicKey, signature);

            final Account gasAccount = this.getAccountByAddress(gasAddress);
            final Contract gasContract = Contract.createSignatureContract(gasAccount.publicKey);

            signature = context.signable.sign(gasAccount);
            context.add(gasContract, gasAccount.publicKey, signature);

            if (context.isCompleted()) {
                tx.scripts = context.getScripts();
                final String txHex = Helper.toHexString(tx.toArray());
                return txHex;
            }

        } catch (final Throwable e) {
            log.error("There is a error when sign {} tx", currency.getName(), e);
        }
        return "";

    }

    private TransactionOutput addOutput(final String address, final BigDecimal amount, final String asset, final Transaction tx, final int index) {
        final TransactionOutput output = new TransactionOutput();
        output.assetId = UInt256.parse(asset);
        output.value = Fixed8.fromDecimal(amount);
        output.scriptHash = Wallet.toScriptHash(address);
        tx.outputs[index] = output;
        return output;
    }

    private TransactionInput addInput(final Map accountMap, final Map contractHashMap, final Address address, final UtxoTransaction utxo, final Transaction tx, final int index) {
        //UtxoTransaction utxo = utxos.get(i);
        final TransactionInput input = new TransactionInput();
        input.prevHash = UInt256.parse(utxo.getTxId());
        input.prevIndex = utxo.getSeq();
        final Account account = this.getAccountByAddress(address);

        final Contract contract = Contract.createSignatureContract(account.publicKey);
        input.scriptHash = contract.scriptHash();
        tx.inputs[index] = input;
        accountMap.put(input.scriptHash, account);
        contractHashMap.put(input.scriptHash, contract);

        return input;

    }

    private Account getAccountByAddress(final Address address) {
        if (ObjectUtils.isEmpty(this.PRIVATE_KEY)) {
            final Deserializer privateKeyDeserializer = ExtendedPrivateKey.deserializer();
            final String mk = KeyConfig.getValue("masterNode");
            this.PRIVATE_KEY = (ExtendedPrivateKey) privateKeyDeserializer.deserialize(mk);
        }
        final CurrencyEnum currencyEnum = CurrencyEnum.parseName(address.getCurrency());
        final ExtendedPrivateKey privateKey = this.PRIVATE_KEY
                .cKDpriv(44)
                .cKDpriv(currencyEnum.getIndex())
                .cKDpriv(address.getBiz())
                .cKDpriv(address.getUserId().intValue())
                .cKDpriv(address.getIndex());

        final Account account = new Account(privateKey.hdKey.key);
        return account;

    }

}
