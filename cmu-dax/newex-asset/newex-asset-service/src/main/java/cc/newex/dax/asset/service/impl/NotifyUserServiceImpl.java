package cc.newex.dax.asset.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.redis.REDIS;
import cc.newex.dax.asset.common.consts.RedisKeyCons;
import cc.newex.dax.asset.criteria.NotifyUserExample;
import cc.newex.dax.asset.dao.NotifyUserRepository;
import cc.newex.dax.asset.domain.NotifyUser;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.service.NotifyUserService;
import cc.newex.wallet.currency.CurrencyEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 充值、提现通知 服务实现
 *
 * @author newex-team
 * @date 2018-08-01 11:17:17
 */
@Slf4j
@Service
public class NotifyUserServiceImpl extends AbstractCrudService<NotifyUserRepository, NotifyUser, NotifyUserExample, Integer> implements NotifyUserService {
    public String ACCESS_SIGN = "ACCESS-SIGN";
    public String ACCESS_TIMESTAMP = "ACCESS-TIMESTAMP";
    public String ACCESS_KEY = "ACCESS-KEY";
    public String CONTENT_TYPE = "Content-Type";
    public String HMAC_SHA256 = "HmacSHA256";
    public String CHARSET = "UTF-8";
    public Mac MAC;
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        try {
            this.MAC = Mac.getInstance(this.HMAC_SHA256);
            final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(5000);
            requestFactory.setReadTimeout(5000);
            this.restTemplate = new RestTemplate(requestFactory);

        } catch (final Throwable e) {
            log.error("Init NotifyUserService error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected NotifyUserExample getPageExample(final String fieldName, final String keyword) {
        final NotifyUserExample example = new NotifyUserExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public NotifyUser getByUserId(final Long userId) {
        final NotifyUserExample example = new NotifyUserExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(0);
        return this.getOneByExample(example);
    }

    @Override
    public void notifyDeposit(final TransferRecord record) {
        try {
            final Long userId = record.getUserId();
            final NotifyUser notifyUser = this.getByUserId(userId);
            if (!ObjectUtils.isEmpty(notifyUser)) {
                final JSONObject auth = JSONObject.parseObject(notifyUser.getAuth());
                final String secret = auth.getString("secret");
                final String key = auth.getString("key");
                final JSONObject url = JSONObject.parseObject(notifyUser.getUrlPath());

                final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
                final NotifyDepositParam notifyDepositParam = NotifyDepositParam.builder()
                        .address(record.getTo())
                        .amount(record.getAmount().toPlainString())
                        .confirm(record.getConfirmation())
                        .currency(currency.getName())
                        .txId(record.getFrom())
                        .build();
                final String body = JSONObject.toJSONString(notifyDepositParam);
                final HttpHeaders requestHeaders = this.generateHeader(body, secret, key);
                final HttpEntity<String> entity = new HttpEntity<>(body, requestHeaders);
                this.restTemplate.postForObject(url.getString("deposit"), entity, String.class);
            }
        } catch (final Throwable e) {
            log.error("notifyDeposit error,record:{}", record, e);
            this.putRetryQueue(record);
        }

    }

    @Override
    public void notifyWithdraw(final TransferRecord record) {
        try {
            final Long userId = record.getUserId();
            final NotifyUser notifyUser = this.getByUserId(userId);
            if (!ObjectUtils.isEmpty(notifyUser)) {
                final JSONObject auth = JSONObject.parseObject(notifyUser.getAuth());
                final String secret = auth.getString("secret");
                final String key = auth.getString("key");
                final JSONObject url = JSONObject.parseObject(notifyUser.getUrlPath());

                final CurrencyEnum currency = CurrencyEnum.parseValue(record.getCurrency());
                final NotifyWithdrawParam notifyDepositParam = NotifyWithdrawParam.builder()
                        .address(record.getTo())
                        .amount(record.getAmount().toPlainString())
                        .fee(record.getFee().toPlainString())
                        .tradeNo(record.getTraderNo())
                        .confirm(record.getConfirmation())
                        .currency(currency.getName())
                        .txId(record.getFrom())
                        .build();
                final String body = JSONObject.toJSONString(notifyDepositParam);
                final HttpHeaders requestHeaders = this.generateHeader(body, secret, key);
                final HttpEntity<String> entity = new HttpEntity<>(body, requestHeaders);
                this.restTemplate.postForObject(url.getString("withdraw"), entity, String.class);
            }
        } catch (final Throwable e) {
            log.error("notifyWithdraw error,record:{}", record, e);
            this.putRetryQueue(record);
        }
    }

    private void putRetryQueue(final TransferRecord record) {
        try {
            if (ObjectUtils.isEmpty(record.getRetryTimes())) {
                record.setRetryTimes(0);
            } else {
                if (record.getRetryTimes() >= 3) {
                    return;
                }
                record.setRetryTimes(record.getRetryTimes() + 1);
            }
            final String str = JSONObject.toJSONString(record);
            final long now = System.currentTimeMillis();
            REDIS.zAdd(RedisKeyCons.NOTIFY_DEPOSIT_WITHDRAW_FAILED, now, str);
        } catch (final Exception e) {
            log.info("putRetryQueue error", e);
        }
    }

    public HttpHeaders generateHeader(String body, final String secretKey, final String apiKey) {

        try {
            final HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.set("Accept-Charset", "utf-8");
            requestHeaders.set("Content-type", "application/json; charset=utf-8");

            final String now = String.valueOf(System.currentTimeMillis());

            requestHeaders.set(this.ACCESS_KEY, apiKey);
            requestHeaders.set(this.CONTENT_TYPE, "application/json; charset=utf-8");
            requestHeaders.set(this.ACCESS_TIMESTAMP, now);

            body = StringUtils.defaultIfBlank(body, StringUtils.EMPTY);

            final String preHash = now + body;
            final byte[] secretKeyBytes = secretKey.getBytes(this.CHARSET);
            final SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, this.HMAC_SHA256);
            final Mac mac = (Mac) this.MAC.clone();
            mac.init(secretKeySpec);
            final String sign = Base64.getEncoder().encodeToString(mac.doFinal(preHash.getBytes(this.CHARSET)));
            requestHeaders.set(this.ACCESS_SIGN, sign);

            return requestHeaders;
        } catch (final Throwable e) {
            log.error("generateHeader error", e);
            return null;
        }
    }

    @Data
    @Builder
    private static class NotifyWithdrawParam {
        private String txId;
        private String currency;
        private String amount;
        private String fee;
        private int confirm;
        private String address;
        private String tradeNo;

    }

    @Data
    @Builder
    private static class NotifyDepositParam {
        private String txId;
        private String currency;
        private String amount;
        private int confirm;
        private String address;

    }
}