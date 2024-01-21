package cc.newex.wallet.service.impl;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.BipNodeUtil;
import cc.newex.wallet.service.ISignService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jota.IotaAPI;
import jota.dto.response.GetNewAddressResponse;
import jota.model.Input;
import jota.model.Transfer;
import jota.pow.ICurl;
import jota.pow.JCurl;
import jota.pow.SpongeFactory;
import jota.utils.Checksum;
import jota.utils.Converter;
import jota.utils.TrytesConverter;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sdk.bip.Bip32Node;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 2018/6/11
 */
@Component
@Slf4j
public class IotaSecondSignService implements ISignService {
    private final int security = 3;

    private final IotaAPI API = new IotaAPI.Builder().build();

    private String SEED = "";

    private String getSEED() {
        if (!StringUtils.hasText(this.SEED)) {
            final Address address = Address.builder().biz(1)
                    .currency(CurrencyEnum.IOTA.getName())
                    .index(0).userId(0L)
                    .build();

            final Bip32Node node = BipNodeUtil.getBipNODE(address);
            final ECKey ecKey = node.getEcKey();
            this.SEED = this.curlHash(TrytesConverter.toTrytes(ecKey.getPrivateKeyAsHex()));
        }
        return this.SEED;
    }

    @Override
    public CurrencyEnum getCurrency() {
        return CurrencyEnum.IOTA;
    }

    @Override
    public String signTransaction(final WithdrawTransaction transaction) {
        final CurrencyEnum currency = CurrencyEnum.parseValue(transaction.getCurrency());
        log.info("{} second sign begin", currency.getName());
        try {
            final String sigStr = transaction.getSignature();
            final JSONObject sigJson = JSONObject.parseObject(sigStr);

            final List<Address> addresses = sigJson.getJSONArray("addresses").toJavaList(Address.class);
            final List<WithdrawRecord> records = sigJson.getJSONArray("withdraw").toJavaList(WithdrawRecord.class);
            final String changeAddr = sigJson.getString("changeAddress");
            final List<Input> inputs = addresses.parallelStream().map((address) -> {
                Input input = null;
                try {
                    input = new Input(
                            Checksum.removeChecksum(address.getAddress()),
                            address.getBalance().multiply(currency.getDecimal()).longValue(),
                            address.getIndex(), this.security);
                } catch (Throwable e) {
                    log.error("there is a error when build input", e);
                    throw new RuntimeException("there is a error when build input");
                }
                return input;
            }).collect(Collectors.toList());
            final List<Transfer> transfers = records.parallelStream().map((record) -> {
                Transfer transfer = null;
                try {
                    transfer = new Transfer(
                            Checksum.removeChecksum(record.getAddress()),
                            record.getBalance().multiply(currency.getDecimal()).longValue());
                } catch (Throwable e) {
                    log.error("there is a error when build Transfer", e);
                    throw new RuntimeException("there is a error when build Transfer");
                }
                return transfer;

            }).collect(Collectors.toList());

            final List<String> result = this.API.prepareTransfers(this.getSEED(), this.security, transfers, Checksum.removeChecksum(changeAddr), inputs, false);
            log.info("{} second sign end", currency.getName());
            return JSONArray.toJSONString(result);

        } catch (final Throwable e) {
            log.error("There is a error when sign {} tx", currency.getName(), e);
            return "";
        }

    }

    @Override
    public List<String> genrateNeedAddress(final JSONObject param) {

        try {

            final List<String> addresses = new LinkedList<>();
            final int index = param.getIntValue("index");
            GetNewAddressResponse response;
            for (int i = index; i < index + 50; i++) {
                response = this.API.getNewAddress(this.getSEED(), this.security, i, true, 1, true);
                addresses.add(response.getAddresses().get(0) + ":" + i);
            }

            return addresses;
        } catch (final Throwable e) {
            log.error("{} genrateNeedAddress error", this.getCurrency(), e);
            return null;
        }
    }

    private String curlHash(final String s) {

        final ICurl curl = SpongeFactory.create(SpongeFactory.Mode.CURLP81);

        final int[] in_trits = Converter.trits(s);
        final int[] hash_trits = new int[JCurl.HASH_LENGTH];

        curl.absorb(in_trits, 0, in_trits.length);
        curl.squeeze(hash_trits, 0, JCurl.HASH_LENGTH);
        final String hash = Converter.trytes(hash_trits);
        return hash;
    }

}
