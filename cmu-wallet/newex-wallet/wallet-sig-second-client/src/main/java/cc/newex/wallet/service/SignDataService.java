package cc.newex.wallet.service;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.wallet.pojo.WithdrawTransaction;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.bitcoinj.core.ECKey;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import sdk.bip.Bip32Node;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @data 16/04/2018
 */
@Component
@Slf4j
public class SignDataService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${newex.asset.url}")
    private String assetUrl;

    @Value("${newex.net.signKey}")
    private String signKeyPri;

    private ECKey signKey;

    private final Long FIVE_MINUTES = 5 * 60 * 1000L;

    @PostConstruct
    public void init() {
        this.signKey = Bip32Node.decode(this.signKeyPri).getEcKey();
    }

    public List<WithdrawTransaction> getWithdrawTx() {
        //final Long fiveMinutes = 5 * 60 * 1000L;
        final Long now = System.currentTimeMillis() + this.FIVE_MINUTES;
        List<WithdrawTransaction> result = null;
        try {
            String signature = this.signKey.signMessage(now.toString());
            signature = new String(Hex.encode(signature.getBytes()));
            final URIBuilder uriBuilder = new URIBuilder(this.assetUrl);
            uriBuilder.addParameter("time", now.toString());
            uriBuilder.addParameter("signature", signature);

            log.info("getWithdrawTx, assetUrl: {}, signature: {}", this.assetUrl, signature);

            final ResponseResult<?> response = this.restTemplate.getForObject(uriBuilder.build().toString(), ResponseResult.class);

            if (response.getCode() == 0) {
                final List<String> resultStr = (List<String>) response.getData();
                result = resultStr.parallelStream().filter((str) -> !StringUtils.isEmpty(str)).map((str) -> {
                    final JSONObject txJson = JSONObject.parseObject(str);
                    final WithdrawTransaction transaction = txJson.toJavaObject(WithdrawTransaction.class);
                    return transaction;
                }).filter((tx) -> !ObjectUtils.isEmpty(tx)).collect(Collectors.toList());
            } else {
                log.error("getWithdrawTx error", response.getMsg());
            }
        } catch (final Throwable e) {
            log.error("getWithdrawTx error", e);
        }
        return result;
    }

    public JSONObject getNeedAddress(final String currency) {
        //final Long fiveMinutes = 5 * 60 * 1000L;
        final Long now = System.currentTimeMillis() + this.FIVE_MINUTES;
        try {
            String signature = this.signKey.signMessage(now.toString());
            signature = new String(Hex.encode(signature.getBytes()));
            final URIBuilder uriBuilder = new URIBuilder(this.assetUrl + "/address/" + currency);
            uriBuilder.addParameter("time", now.toString());
            uriBuilder.addParameter("signature", signature);
            final ResponseResult<?> response = this.restTemplate.getForObject(uriBuilder.build().toString(), ResponseResult.class);
            if (response.getCode() == 0) {
                final JSONObject result = new JSONObject();
                result.putAll((Map) response.getData());
                return result;
            } else {
                log.error("getNeedAddress error", response.getMsg());
                return null;
            }
        } catch (final Throwable e) {
            log.error("getNeedAddress error", e);
        }

        return null;
    }

    public void postNeedAddress(final String currency, final List<String> addresses) {
        log.info("postNeedAddress begin");
        //final Long fiveMinutes = 5 * 60 * 1000L;
        final Long now = System.currentTimeMillis() + this.FIVE_MINUTES;
        try {
            String signature = this.signKey.signMessage(now.toString());
            signature = new String(Hex.encode(signature.getBytes()));

            final JSONObject signData = new JSONObject();
            signData.put("time", now.toString());
            signData.put("signature", signature);
            signData.put("signData", addresses);

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
            final String url = this.assetUrl + "/address/" + currency;
            final HttpEntity<String> strEntity = new HttpEntity<>(signData.toJSONString(), headers);
            final ResponseResult response = this.restTemplate.postForObject(url, strEntity, ResponseResult.class);

            if (response.getCode() == 0) {
                log.info("postNeedAddress success");
            } else {
                log.error("postNeedAddress error", response.getMsg());
            }
        } catch (final Throwable e) {
            log.error("postNeedAddress error", e);
        }
    }

    public void postWithdrawTx(final List<WithdrawTransaction> withdrawTransactions) {
        log.info("postWithdrawTx begin");
        //final Long fiveMinutes = 5 * 60 * 1000L;
        final Long now = System.currentTimeMillis() + this.FIVE_MINUTES;
        try {
            String signature = this.signKey.signMessage(now.toString());
            signature = new String(Hex.encode(signature.getBytes()));

            final JSONObject signData = new JSONObject();
            signData.put("time", now.toString());
            signData.put("signature", signature);
            signData.put("signData", withdrawTransactions);

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
            final HttpEntity<String> strEntity = new HttpEntity<>(signData.toJSONString(), headers);
            final ResponseResult response = this.restTemplate.postForObject(this.assetUrl, strEntity, ResponseResult.class);

            if (response.getCode() == 0) {
                log.info("postWithdrawTx success");
            } else {
                log.error("getWithdrawTx error", response.getMsg());
            }
        } catch (final Throwable e) {
            log.error("getWithdrawTx error", e);
        }
    }
}
