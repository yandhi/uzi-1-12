package me.kix.uzi.api.event.cancellable;

import me.kix.uzi.api.event.Event;

public class EventCancellable extends Event implements Cancellable {

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
