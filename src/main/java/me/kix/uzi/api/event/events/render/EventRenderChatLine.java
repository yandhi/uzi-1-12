package me.kix.uzi.api.event.events.render;

import me.kix.uzi.api.event.cancellable.EventCancellable;

/**
 * Called when the game renders a chat line.
 *
 * @author jackson
 * @since 11/26/2021
 */
public class EventRenderChatLine extends EventCancellable {

    /**
     * The message being recieved.
     */
    private final String message;

    /**
     * The height of the chat line.
     */
    private final int height;

    /**
     * The width of the chat line.
     */
    private final int width;

    public EventRenderChatLine(String message, int height, int width) {
        this.message = message;
        this.height = height;
        this.width = width;
    }

    public String getMessage() {
        return message;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
