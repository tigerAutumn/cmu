package cc.newex.dax.integration.task;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.service.msg.MessageService;
import cc.newex.dax.integration.service.msg.provider.MessageProviderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 发送的消息(邮件/短信等)定时任务类
 *
 * @author newex-team
 * @date 2018-05-10
 */
@Slf4j
@Component
public class MessageTask {

    final int nThreads = 64;

    @Resource
    private MessageService messageService;

    @Resource
    private MessageProviderFactory messageProviderFactory;

    @PostConstruct
    public void init() {

        log.info("message init ...");
        for (int i = 0; i < this.nThreads; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            final Message message = MessageTask.this.messageService.getMessageQueue().poll();
                            if (message == null) {
                                Thread.sleep(1000);
                                continue;
                            }
                            MessageTask.log.debug("MessageQueue size : {}, message : {}",
                                    MessageTask.this.messageService.getMessageQueue().size(), message);
                            MessageTask.this.messageService.send(message);
                        } catch (final Exception ex) {
                            MessageTask.log.error("send message error", ex);
                        }
                    }
                }
            }).start();
        }
    }

}
