package me.kix.uzi.api.event.events.render;

import me.kix.uzi.api.event.cancellable.EventCancellable;

/**
 * Called when the chat box is rendered.
 *
 * @author jackson
 * @since 11/27/2021
 */
public class EventRenderTextBox extends EventCancellable {

    /**
     * The location of the text box.
     */
    private final int x, y;

    /**
     * Color of the object.
     */
    private final int color;

    public EventRenderTextBox(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    /**
     * The box around the text.
     */
    public static class Rectangle extends EventRenderTextBox {

        public Rectangle(int x, int y, int color) {
            super(x, y, color);
        }
    }

    /**
     * The text.
     */
    public static class Text extends EventRenderTextBox {
        /* What's being rendered */
        private final String text;

        /* for mixins. */
        private int callbackReturn;

        public Text(int x, int y, String text, int color) {
            super(x, y, color);
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public int getCallbackReturn() {
            return callbackReturn;
        }

        public void setCallbackReturn(int callbackReturn) {
            this.callbackReturn = callbackReturn;
        }
    }

}
