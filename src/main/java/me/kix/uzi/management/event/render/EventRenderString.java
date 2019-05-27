package me.kix.uzi.management.event.render;

import me.kix.uzi.api.event.Event;

/**
 * @author Kix
 * @since 5/25/2018
 */
public class EventRenderString extends Event{

    private String text;

    public EventRenderString(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
