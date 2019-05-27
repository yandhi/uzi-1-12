package me.kix.uzi.api.event.cancellable;

public interface Cancellable {

    boolean isCancelled();
    void setCancelled(boolean cancelled);

}
