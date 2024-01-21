package cc.newex.dax.integration.service.msg.provider.sms;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.service.msg.provider.MessageProvider;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 只为中国区域短信发送服务类
 *
 * @author newex-team
 * @link https://zz.253.com/index.html
 * @date 2018-05-18
 */
@Slf4j
public class ZZ253ZhSmsProvider
        extends ZZ253SmsProvider implements MessageProvider {

    public ZZ253ZhSmsProvider() {
        super();
    }

    @Override
    public String getName() {
        return "zz253zh-sms";
    }

    @Override
    public boolean send(final Message message) {
        final Map<String, Object> params = Maps.newHashMapWithExpectedSize(4);
        params.put("account", this.key);
        params.put("password", this.secret);
        params.put("msg", String.format("【%s】%s", message.getFromAlias(), message.getContent()));
        params.put("phone", message.getPhoneNumber());
        return this.send(params, message);
    }
}
