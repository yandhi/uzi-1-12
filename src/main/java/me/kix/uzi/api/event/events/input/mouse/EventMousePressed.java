package me.kix.uzi.api.event.events.input.mouse;

import me.kix.uzi.api.event.Event;

/**
 * An event for mouse presses.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class EventMousePressed extends Event {

    private final int mouseButton;

    public EventMousePressed(int mouseButton) {
        this.mouseButton = mouseButton;
    }

    public int getMouseButton() {
        return mouseButton;
    }
}
