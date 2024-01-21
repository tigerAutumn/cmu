package cc.newex.dax.integration.service.msg.provider.sms;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.service.msg.provider.MessageProvider;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.nio.charset.Charset;
import java.util.Map;

@Slf4j
public class NexmoSmsProvider
        extends AbstractSmsProvider implements MessageProvider {
    private String key;
    private String secret;
    private Map<String, Object> options;
    private NexmoClient client;

    public NexmoSmsProvider() {
    }

    private void build() {
        final AuthMethod auth = new TokenAuthMethod(this.key, this.secret);
        this.client = new NexmoClient(auth);
        this.client.setHttpClient(this.createHttpClient());
    }

    @Override
    public String getName() {
        return "nexmo-sms";
    }

    @Override
    public void setOptions(final Map<String, Object> options) {
        this.options = options;
        this.key = MapUtils.getString(this.options, "key");
        this.secret = MapUtils.getString(this.options, "secret");
        this.build();
    }

    @Override
    public boolean send(final Message message) {
        try {
            final String phoneNumber = StringUtils.join(message.getCountryCode(), message.getPhoneNumber());
            final SmsSubmissionResult[] responses = this.client.getSmsClient().submitMessage(
                    new TextMessage(message.getFromAlias(), phoneNumber, message.getContent())
            );
            for (final SmsSubmissionResult response : responses) {
                if (response.getStatus() != SmsSubmissionResult.STATUS_OK) {
                    log.error("Msg ID:[{}],Sender:{},Template:{},Receiver:{},failure,Error:{}",
                            message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber(), response.toString());
                    return false;
                }
            }
            log.debug("Msg ID:[{}],Sender:{},Template:{},Receiver:{},Success",
                    message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber());
            return true;
        } catch (final Exception e) {
            log.error("Msg ID:[{}],Sender:{},Template:{},Receiver:{},failure,Error:{}",
                    message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber(), e.getMessage());
            return false;
        }
    }

    private HttpClient createHttpClient() {
        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(200);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultConnectionConfig(
                ConnectionConfig.custom().setCharset(Charset.forName("UTF-8")).build());
        connectionManager.setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(true).build());

        final RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .build();

        return HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setUserAgent("nexmo-java/3.4.1")
                .setDefaultRequestConfig(requestConfig)
                .build();
    }
}
