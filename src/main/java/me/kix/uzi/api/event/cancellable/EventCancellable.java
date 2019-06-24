package me.kix.uzi.api.event.cancellable;

import me.kix.uzi.api.event.Event;

/**
 * An implementation of {@link Event} that can be cancelled.
 *
 * @author Kix
 * @since April 2018.
 */
public class EventCancellable extends Event implements Cancellable {

    /**
     * Whether or not the event is cancelled.
     */
    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
