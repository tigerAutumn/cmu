package cc.newex.dax.integration.event.msg;

import cc.newex.dax.integration.domain.msg.Message;
import cc.newex.dax.integration.event.AbstractEvent;

/**
 * @author newex-team
 * @date 2018-05-15
 */
public class AddMessageEvent extends AbstractEvent {
    private final Message message;

    public AddMessageEvent(final Object sender, final Message message) {
        super(sender);
        this.message = message;
    }

    public Message getMessage() {
        return this.message;
    }
}
