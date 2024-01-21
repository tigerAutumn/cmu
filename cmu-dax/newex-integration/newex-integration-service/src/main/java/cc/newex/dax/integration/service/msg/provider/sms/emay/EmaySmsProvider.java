package cc.newex.dax.integration.service.msg.provider.sms.emay;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.service.msg.provider.MessageProvider;
import cc.newex.dax.integration.service.msg.provider.sms.AbstractSmsProvider;
import cc.newex.dax.integration.service.msg.provider.sms.emay.request.SmsSingleRequest;
import cc.newex.dax.integration.service.msg.provider.sms.emay.util.AES;
import cc.newex.dax.integration.service.msg.provider.sms.emay.util.GZIPUtils;
import cc.newex.dax.integration.service.msg.provider.sms.emay.util.JsonHelper;
import cc.newex.dax.integration.service.msg.provider.sms.emay.util.http.EmayHttpClient;
import cc.newex.dax.integration.service.msg.provider.sms.emay.util.http.EmayHttpRequestBytes;
import cc.newex.dax.integration.service.msg.provider.sms.emay.util.http.EmayHttpResponseBytes;
import cc.newex.dax.integration.service.msg.provider.sms.emay.util.http.EmayHttpResponseBytesPraser;
import cc.newex.dax.integration.service.msg.provider.sms.emay.util.http.EmayHttpResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * author: lingqing.wan
 * date: 2018-04-08 下午1:57
 */
@Slf4j
public class EmaySmsProvider
        extends AbstractSmsProvider implements MessageProvider {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String ENCODING = "UTF-8";
    private String url;
    private String appId;
    private String secretKey;
    private Map<String, Object> options;

    @Override
    public String getName() {
        return "emay-sms";
    }

    @Override
    public void setOptions(final Map<String, Object> options) {
        this.options = options;
        this.url = MapUtils.getString(options, "url");
        this.appId = MapUtils.getString(options, "appId");
        this.secretKey = MapUtils.getString(options, "secretKey");
    }

    @Override
    public boolean send(final Message message) {
        try {
            final String content = String.format("【%s】%s", message.getFromAlias(), message.getContent());
            this.send(content, message.getPhoneNumber());
            log.debug("Msg ID:[{}],Sender:{},Template:{},Receiver:{},Success",
                    message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber());
            return true;
        } catch (final Exception e) {
            log.error("Msg ID:[{}],Sender:{},Template:{},Receiver:{},failure,Error:{}",
                    message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber(), e.getMessage());
            return false;
        }
    }

    private void send(final String content, final String mobile) throws Exception {
        final SmsSingleRequest params = new SmsSingleRequest();
        params.setContent(content);
        params.setCustomSmsId(null);
        params.setExtendedCode(null);
        params.setMobile(mobile);
        this.send(params);
    }

    private void send(final Object params) throws Exception {
        final Map<String, String> headers = new HashMap<>(4);
        final EmayHttpRequestBytes request;
        headers.put("appId", this.appId);
        headers.put("encode", EmaySmsProvider.ENCODING);

        final String requestJson = JsonHelper.toJsonString(params);
        byte[] bytes = requestJson.getBytes(EmaySmsProvider.ENCODING);
        headers.put("gzip", "on");
        bytes = GZIPUtils.compress(bytes);
        final byte[] encryptBytes = AES.encrypt(bytes, this.secretKey.getBytes(), EmaySmsProvider.ALGORITHM);
        request = new EmayHttpRequestBytes(this.url, EmaySmsProvider.ENCODING, "POST", headers, null, encryptBytes);

        final EmayHttpClient client = new EmayHttpClient();
        final EmayHttpResponseBytes res = client.service(request, new EmayHttpResponseBytesPraser());
        if (res == null || !res.getResultCode().equals(EmayHttpResultCode.SUCCESS)) {
            throw new RuntimeException("Emay send sms failure!");
        }
    }
}
