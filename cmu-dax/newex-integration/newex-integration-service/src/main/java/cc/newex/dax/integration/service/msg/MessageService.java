package cc.newex.dax.integration.service.msg;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.integration.criteria.msg.MessageExample;
import cc.newex.dax.integration.domain.msg.Message;

import java.util.List;
import java.util.Queue;

public interface MessageService
        extends CrudService<Message, MessageExample, Long> {

    Queue<Message> getMessageQueue();

    void sendSmsAsync(String templateCode, String locale, String countryCode, String mobile, String params, Integer brokerId);

    void sendEmailAsync(String templateCode, String locale, String countryCode, String toAddress, String params, Integer brokerId);

    void send(Message message);

    List<Message> getOutboxMessages(int offset, int length);
}
