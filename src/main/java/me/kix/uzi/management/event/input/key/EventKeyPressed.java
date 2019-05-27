package me.kix.uzi.management.event.input.key;

import me.kix.uzi.api.event.Event;

public class EventKeyPressed extends Event {

    private final int key;

    public EventKeyPressed(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

}
