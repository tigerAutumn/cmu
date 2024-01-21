package cc.newex.dax.users.event;

/**
 * @author newex-team
 * @date 2018-05-15
 */
public class AbstractEvent {
    protected final Object sender;

    protected AbstractEvent(final Object sender) {
        this.sender = sender;
    }

    public Object getSender() {
        return this.sender;
    }
}
