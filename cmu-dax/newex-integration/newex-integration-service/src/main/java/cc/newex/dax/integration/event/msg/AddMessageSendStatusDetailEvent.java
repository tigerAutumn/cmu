package cc.newex.dax.integration.event.msg;

import cc.newex.dax.integration.domain.msg.MessageSendStatusDetail;
import cc.newex.dax.integration.event.AbstractEvent;

/**
 * @author newex-team
 * @date 2018-05-17
 */
public class AddMessageSendStatusDetailEvent extends AbstractEvent {
    private final MessageSendStatusDetail messageSendStatusDetail;

    public AddMessageSendStatusDetailEvent(final Object sender,
                                           final MessageSendStatusDetail messageSendStatusDetail) {
        super(sender);
        this.messageSendStatusDetail = messageSendStatusDetail;
    }

    public MessageSendStatusDetail getMessageSendStatusDetail() {
        return this.messageSendStatusDetail;
    }
}
