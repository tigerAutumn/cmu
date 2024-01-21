package cc.newex.dax.integration.service.msg.provider.sms;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.service.msg.provider.MessageProvider;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;

import java.util.Map;

/**
 * 3tong.net短信发送服务
 *
 * @author newex-team
 * @link https://3tong.net/ytx/homePage.jsp
 * @date 2018-05-18
 */
@Slf4j
public class Net3tongSmsProvider
        extends AbstractSmsProvider implements MessageProvider {
    protected static final String SUCCESS_STATUS = "0";
    protected static final String RESULT_FIELD = "result";
    protected String url;
    protected String account;
    protected String password;
    protected String referer;
    protected Map<String, Object> options;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

    public Net3tongSmsProvider() {
    }

    @Override
    public String getName() {
        return "3tong-sms";
    }

    @Override
    public void setOptions(final Map<String, Object> options) {
        this.options = options;
        this.url = MapUtils.getString(this.options, "url");
        this.referer = MapUtils.getString(this.options, "referer");
        this.account = MapUtils.getString(this.options, "account");
        this.password = DigestUtils.md5Hex(MapUtils.getString(this.options, "password"));
    }

    @Override
    public boolean send(final Message message) {
        final Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("account", this.account);
        params.put("password", this.password);
        params.put("content", message.getContent());
        params.put("sign", String.format("【%s】", message.getFromAlias()));
        params.put("phones", StringUtils.join("+", message.getCountryCode(), message.getPhoneNumber()));
        params.put("sendtime", "");
        return this.send(params, message);
    }

    protected boolean send(final Map<String, Object> params, final Message message) {
        Request request = null;
        if (StringUtils.isBlank(this.referer)) {
            request = new Request.Builder().url(this.url)
                    .header("Content-Type", ContentType.APPLICATION_JSON.toString())
                    .post(RequestBody.create(this.mediaType, JSON.toJSONString(params)))
                    .build();
        } else {
            request = new Request.Builder().url(this.url)
                    .header("Content-Type", ContentType.APPLICATION_JSON.toString())
                    .header("referer", this.referer)
                    .post(RequestBody.create(this.mediaType, JSON.toJSONString(params)))
                    .build();
        }

        try (final Response response = this.okHttpClient.newCall(request).execute()) {
            final String bodyStr = response.body().string();

            log.info("message: {}, response: {}", message, bodyStr);

            final JSONObject jsonObject = JSON.parseObject(bodyStr);

            if (jsonObject == null || StringUtil.notEquals(SUCCESS_STATUS, jsonObject.getString(RESULT_FIELD))) {
                log.error("Msg ID:[{}], Sender:{}, Template:{}, Receiver:{}, failure response:{}",
                        message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber(), bodyStr);

                return false;
            }
        } catch (final Exception e) {
            log.error("Msg ID:[{}], Sender:{}, Template:{}, Receiver:{}, failure Error:{}",
                    message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber(), e.getMessage());

            log.error("发送短信失败：" + e.getMessage(), e);

            return false;
        }

        log.debug("Msg ID:[{}], Sender:{}, Template:{}, Receiver:{}, Success",
                message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber());

        return true;
    }

}
