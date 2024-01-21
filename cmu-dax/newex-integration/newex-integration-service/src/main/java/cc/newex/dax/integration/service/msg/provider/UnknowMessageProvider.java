package cc.newex.dax.integration.service.msg.provider;

import cc.newex.dax.integration.domain.msg.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class UnknowMessageProvider extends AbstractMessageProvider implements MessageProvider {
    @Override
    public String getName() {
        return "Unknow";
    }

    @Override
    public void setOptions(final Map<String, Object> options) {
    }

    @Override
    public boolean send(final Message message) {
        log.warn("Unknow Message :{}", message.getId());
        return false;
    }
}
