package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.nem.core.crypto.KeyPair;
import org.nem.core.crypto.PrivateKey;
import org.nem.core.messages.PlainMessage;
import org.nem.core.model.Account;
import org.nem.core.model.NetworkInfos;
import org.nem.core.model.TransferTransaction;
import org.nem.core.model.TransferTransactionAttachment;
import org.nem.core.model.primitive.Amount;
import org.nem.core.serialization.BinarySerializer;
import org.nem.core.time.SystemTimeProvider;
import org.nem.core.time.TimeInstant;
import org.nem.core.utils.HexEncoder;
import org.springframework.stereotype.Component;
import sdk.bip.Bip32Node;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author newex-team
 * @data 2018/8/27
 */
@Component
@Slf4j
public class XemSecondSignService implements ISignService {
    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.XEM;
    }

    static {
        NetworkInfos.setDefault(NetworkInfos.getMainNetworkInfo());
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        String signResult = "";
        try {
            final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());

            final String sigStr = transaction.getSignature();
            final JSONObject sigJson = JSONObject.parseObject(sigStr);
            final Address address = sigJson.getJSONObject("address").toJavaObject(Address.class);

            final String toAddr = sigJson.getString("to");
            final String[] addressAndMemo = toAddr.split(":");
            if (addressAndMemo.length != 2) {
                log.error("{} signTransaction error,addressAndMemo.Len !=2 ", currency);
                return null;
            }
            final String to = addressAndMemo[0];
            final String memo = addressAndMemo[1];

            final BigDecimal balance = transaction.getBalance().multiply(this.getCurrency().getDecimal());

            final Account sender = this.getAccountByAddress(address);
            final Account recipient = new Account(org.nem.core.model.Address.fromEncoded(to));
            final Amount amount = new Amount(balance.longValue());

            final TimeInstant timeInstant = new SystemTimeProvider().getCurrentTime();
            final TransferTransactionAttachment attachment = new TransferTransactionAttachment();
            attachment.setMessage(new PlainMessage(memo.getBytes()));

            final TransferTransaction transferTransaction = new TransferTransaction(timeInstant, sender, recipient, amount,
                    attachment);
            BigDecimal feeDouble = BigDecimal.valueOf((amount.getNumNem() / 10_000) * 0.05);
            if (feeDouble.doubleValue() < 0.05) {
                feeDouble = BigDecimal.valueOf(0.05);
            } else if (feeDouble.doubleValue() > 1.25) {
                feeDouble = BigDecimal.valueOf(1.25);
            }

            BigDecimal messageFee = BigDecimal.ZERO;
            if (attachment.getMessage() != null) {
                messageFee = BigDecimal.valueOf((attachment.getMessage().getEncodedPayload().length / 32 + 1) * 0.05);
            }
            feeDouble = feeDouble.add(messageFee);

            final Amount fee = new Amount(feeDouble.multiply(CurrencyEnum.XEM.getDecimal()).longValue());
            transferTransaction.setFee(fee);
            transferTransaction.setDeadline(timeInstant.addHours(24));
            transferTransaction.sign();

            final byte[] data = BinarySerializer.serializeToBytes(transferTransaction.asNonVerifiable());
            final JSONObject params = new JSONObject();
            params.put("data", HexEncoder.getString(data));
            params.put("signature", transferTransaction.getSignature().toString());
            signResult = params.toString();
        } catch (final Exception e) {
            log.error("{} signTransaction error", this.getCurrency(), e);
        }
        return signResult;
    }

    private Account getAccountByAddress(final Address address) {
        final Bip32Node node = BipNodeUtil.getMainBipNODE();
        final Bip32Node bip32Node = node.getChildH(this.getCurrency().getIndex())
                .getChild(44)
                //biz
                .getChild(address.getBiz())
                //userid
                .getChild(address.getUserId().intValue())
                //index
                .getChild(address.getIndex());
        final ECKey ecKey = bip32Node.getEcKey();
        final PrivateKey privateKey = new PrivateKey(ecKey.getPrivKey());

        final KeyPair keyPair = new KeyPair(privateKey);
        final Account account = new Account(keyPair);
        return account;
    }

    private JSONObject curlGet(String url) {
        try {
            final String httpStart = "http";
            if (!url.startsWith(httpStart)) {
                url = httpStart + "://" + url;
            }

            final String command = "curl " + url;
            //command = "curl -H Referer:https://btc.com http://innerproxy.cmx.com/service/fees/distribution";
            final Process p = Runtime.getRuntime().exec(command);
            String line;
            String res = "";
            final BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null) {
                res = res + line;
            }
            br.close();
            return JSONObject.parseObject(res);
        } catch (final Throwable e) {
            log.error("curlGet {} error", url, e);
        }
        return null;
    }

    private JSONObject curlPost(final String url, final String jsonBody) {
        try {
            final HttpURLConnection httpcon = (HttpURLConnection) ((new URL(url).openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Accept", "application/json");
            httpcon.setRequestMethod("POST");
            httpcon.connect();

            final byte[] outputBytes = jsonBody.getBytes("UTF-8");
            final OutputStream os = httpcon.getOutputStream();
            os.write(outputBytes);

            String line;
            String res = "";
            final BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            while ((line = br.readLine()) != null) {
                res = res + line;
            }

            os.close();

            return JSONObject.parseObject(res);
        } catch (final Throwable e) {
            log.error("curlGet {} error", url, e);
        }
        return null;
    }

}
