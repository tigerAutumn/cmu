package cc.newex.commons.openapi.support.auth;

import cc.newex.commons.openapi.specs.auth.OpenApiAuthToken;
import cc.newex.commons.openapi.specs.enums.ContentTypeEnum;
import cc.newex.commons.openapi.specs.enums.HmacAlgorithmEnum;
import cc.newex.commons.openapi.specs.model.OpenApiHeadersInfo;
import cc.newex.commons.openapi.support.enums.OpenApiErrorCodeEnum;
import cc.newex.commons.openapi.support.utils.DateUtils;
import cc.newex.commons.openapi.support.utils.ExceptionUtils;
import cc.newex.commons.openapi.support.utils.RegularUtils;
import cc.newex.commons.openapi.support.utils.SignatureUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.Date;

/**
 * OpenApiAuthToken Default Impl
 *
 * @author newex-team
 * @date 2018-04-28
 */
@Getter
@Setter
@Slf4j
public class DefaultOpenApiAuthToken
        extends OpenApiHeadersInfo implements OpenApiAuthToken {
    private static final String GET = "GET";
    private String apiMethod;
    private String contentType;
    private String body;
    private String method;
    private String requestPath;
    private String queryString;
    private String ip;
    private String apiSecret;
    private String prefix;
    private int expiredSeconds;
    private boolean availableAtFrozen;
    private String sign;

    /**
     * validate Message Sign
     *
     * @param algorithm sign algorithm (md5,sha256 etc.)
     */
    @Override
    public boolean validateSign(final HmacAlgorithmEnum algorithm) {
        final String sign = SignatureUtils.generate(
                this.accessTimestamp, this.apiMethod, this.requestPath, this.queryString,
                this.body, this.apiSecret, algorithm
        );
        this.setSign(sign);
        log.debug("apiKey:{},sign:{},access sign:{}", this.getAccessKey(), sign, this.getAccessSign());
        return StringUtils.equals(sign, this.getAccessSign());
    }

    /**
     * open api request args check.
     */
    @Override
    public void valid() {
        if (StringUtils.isEmpty(this.getAccessKey())) {
            ExceptionUtils.getFailure(OpenApiErrorCodeEnum.ACCESS_KEY_EMPTY);
        }
        if (this.checkApiKey()) {
            ExceptionUtils.getFailure(OpenApiErrorCodeEnum.INVALID_ACCESS_KEY);
        }
        if (StringUtils.isEmpty(this.getAccessSign())) {
            ExceptionUtils.getFailure(OpenApiErrorCodeEnum.ACCESS_SIGN_EMPTY);
        }
        if (StringUtils.isEmpty(this.getAccessTimestamp())) {
            ExceptionUtils.getFailure(OpenApiErrorCodeEnum.ACCESS_TIMESTAMP_EMPTY);
        }
        final Date clientDate = this.getDateByTimestamp();
        if (clientDate == null) {
            ExceptionUtils.getFailure(OpenApiErrorCodeEnum.INVALID_ACCESS_TIMESTAMP);
        }
        if (this.checkTimestamp(clientDate)) {
            ExceptionUtils.getFailure(OpenApiErrorCodeEnum.ACCESS_TIMESTAMP_EXPIRED);
        }
        if (this.checkContentType()) {
            ExceptionUtils.getFailure(OpenApiErrorCodeEnum.INVALID_CONTENT_TYPE);
        }
    }

    /**
     * check timestamp expired
     *
     * @param clientDate accessTimestamp epoch time
     * @return true  timestamp expired
     */
    private boolean checkTimestamp(final Date clientDate) {
        final long clientTime = clientDate.toInstant().getEpochSecond();
        final long serverTime = Instant.now().getEpochSecond();
        log.info("client:{},server:{},expired:{},sub:{}", clientTime, serverTime, this.expiredSeconds, Math.abs(clientTime - serverTime));
        return Math.abs(clientTime - serverTime) > this.expiredSeconds;
    }

    /**
     * check apiKey
     *
     * @return true Invalid apiKey
     */
    private boolean checkApiKey() {
        return !StringUtils.startsWithIgnoreCase(this.accessKey, this.prefix);
    }

    /**
     * check contentType. <br/>
     * <p>
     * All requests and responses are application/json content type. <br/>
     *
     * @return true Invalid contentType
     */
    private boolean checkContentType() {
        return !GET.equalsIgnoreCase(this.method) && (this.contentType == null ||
                !this.contentType.contains(ContentTypeEnum.APPLICATION_JSON.getContentType()));
    }

    /**
     * get date by timestamp value
     */
    private Date getDateByTimestamp() {
        final String timestamp = this.accessTimestamp;
        if (RegularUtils.isUnixISOTime(timestamp)) {
            return DateUtils.parseUTCTime(timestamp);
        }
        if (RegularUtils.isEpochSecondDecimal(timestamp)) {
            return DateUtils.parseDecimalTime(timestamp);
        }
        if (RegularUtils.isEpochMicroSeconds(timestamp)) {
            return new Date(Long.valueOf(timestamp));
        }
        return null;
    }
}
