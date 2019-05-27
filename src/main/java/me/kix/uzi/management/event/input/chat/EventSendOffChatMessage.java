package me.kix.uzi.management.event.input.chat;

import me.kix.uzi.api.event.cancellable.EventCancellable;

public class EventSendOffChatMessage extends EventCancellable {

    private String message;

    public EventSendOffChatMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
