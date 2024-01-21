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
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;

import java.util.Map;

/**
 * 国际(包话中国区）短信发送服务类
 *
 * @author newex-team
 * @link https://zz.253.com/index.html
 * @date 2018-05-18
 */
@Slf4j
public class ZZ253SmsProvider
        extends AbstractSmsProvider implements MessageProvider {
    protected static final String SUCCESS = "0";
    protected static final String SUCCESS_FIELD = "code";
    protected String url;
    protected String referer;
    protected String key;
    protected String secret;
    protected Map<String, Object> options;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

    public ZZ253SmsProvider() {

    }

    @Override
    public String getName() {
        return "zz253-sms";
    }

    @Override
    public void setOptions(final Map<String, Object> options) {
        this.options = options;
        this.url = MapUtils.getString(this.options, "url");
        this.referer = MapUtils.getString(this.options, "referer");
        this.key = MapUtils.getString(this.options, "key");
        this.secret = MapUtils.getString(this.options, "secret");
    }

    @Override
    public boolean send(final Message message) {
        final Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("account", this.key);
        params.put("password", this.secret);
        params.put("msg", String.format("【%s】%s", message.getFromAlias(), message.getContent()));
        params.put("mobile", StringUtils.join(message.getCountryCode(), message.getPhoneNumber()));

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

            final JSONObject jsonObject = JSON.parseObject(bodyStr);

            if (jsonObject == null || StringUtil.notEquals(SUCCESS, jsonObject.getString(SUCCESS_FIELD))) {
                log.error("Msg ID:[{}],Sender:{},Template:{},Receiver:{},failure,response:{}",
                        message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber(), bodyStr);

                return false;
            }
        } catch (final Exception e) {
            log.error("Msg ID:[{}],Sender:{},Template:{},Receiver:{},failure,Error:{}",
                    message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber(), e.getMessage());

            log.error("发送短信失败：" + e.getMessage(), e);

            return false;
        }

        log.debug("Msg ID:[{}],Sender:{},Template:{},Receiver:{},Success", message.getId(), this.getName(), message.getTemplateCode(), message.getPhoneNumber());
        return true;
    }
}
