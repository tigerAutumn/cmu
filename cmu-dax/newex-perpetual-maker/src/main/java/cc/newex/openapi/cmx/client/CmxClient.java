package cc.newex.openapi.cmx.client;

import cc.newex.openapi.cmx.common.domain.ClientParameter;
import cc.newex.openapi.cmx.common.enums.Content;
import cc.newex.openapi.cmx.perpetual.PerpetualApiFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * CMX exchange rest open api Client
 *
 * @date 2018/04/28
 */
public class CmxClient {
    private final ApiClient apiClient;
    private final ClientParameter configuration;

    private CmxClient(final Builder builder) {
        this.configuration = builder.configuration;
        Validate.notNull(this.configuration, "configuration is null");
        Validate.notNull(this.configuration.getApiKey(), "apiKey is null");
        Validate.notNull(this.configuration.getSecretKey(), "secretKey is null");
        Validate.notNull(this.configuration.getPassphrase(), "passphrase is null");

        this.configuration.setBaseUrl(StringUtils.defaultIfBlank(this.configuration.getBaseUrl(), Content.BASE_URL));
        this.configuration.setTimeout(ObjectUtils.defaultIfNull(this.configuration.getTimeout(), Content.TIME_OUT));

        this.apiClient = new ApiClient(this.configuration);
    }

    public static Builder builder() {
        return new Builder();
    }

    public PerpetualApiFacade perpetual() {
        return new PerpetualApiFacade(this.apiClient);
    }

    public static class Builder {
        private ClientParameter configuration;

        public Builder configuration(final ClientParameter value) {
            this.configuration = value;
            return this;
        }

        public CmxClient build() {
            return new CmxClient(this);
        }
    }
}
