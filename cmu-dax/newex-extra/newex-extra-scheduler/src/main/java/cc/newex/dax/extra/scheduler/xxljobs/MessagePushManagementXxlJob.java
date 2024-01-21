package cc.newex.dax.extra.scheduler.xxljobs;

import cc.newex.dax.extra.scheduler.service.MessagePushManagementService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息推送管理的定时任务
 * <p>
 * 定时查询数据库中消息推送管理记录
 * 根据其推送时间向redis推送消息
 *
 * @author better
 * @date create in 2018/11/9 4:21 PM
 */
@Slf4j
@JobHandler(value = "MessagePushManagementXxlJob")
@Component
public class MessagePushManagementXxlJob extends IJobHandler {

    private final MessagePushManagementService messagePushManagementService;

    /**
     * Instantiates a new Message push management xxl job.
     *
     * @param messagePushManagementService the message push management service
     */
    @Autowired
    public MessagePushManagementXxlJob(MessagePushManagementService messagePushManagementService) {
        this.messagePushManagementService = messagePushManagementService;
    }

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        messagePushManagementService.sendMessage();
        return ReturnT.SUCCESS;
    }
}
