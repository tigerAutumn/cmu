package cc.newex.dax.integration.event;

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
