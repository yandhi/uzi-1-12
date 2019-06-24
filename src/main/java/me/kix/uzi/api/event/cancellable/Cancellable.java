package me.kix.uzi.api.event.cancellable;

/**
 * A mutable interface that allows for an object to be cancelled.
 *
 * @author Kix
 * @since April 2018.
 */
public interface Cancellable {

    /**
     * @return Whether or not the object is cancelled.
     */
    boolean isCancelled();

    /**
     * Changes the state of an object.
     *
     * @param cancelled The new cancelled state.
     */
    void setCancelled(boolean cancelled);
}
