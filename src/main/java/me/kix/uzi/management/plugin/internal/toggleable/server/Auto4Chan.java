package me.kix.uzi.management.plugin.internal.toggleable.server;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.input.chat.EventSendOffChatMessage;

/**
 * Module for any server that contains the 4chan green text plugin.
 * Alerithe came up with the title for this plugin.
 *
 * @author Kix
 */
public class Auto4Chan extends ToggleablePlugin {

    public Auto4Chan() {
        super("Auto4chan", Category.MISCELLANEOUS);
        setDisplay("Auto 4chan");
        setColor(0xFF10B41C);
    }

    @Register
    public void onSendMessage(EventSendOffChatMessage event) {
        if (!event.getMessage().startsWith(".") && !event.getMessage().startsWith("/") && !event.getMessage().startsWith(">"))
            event.setMessage("> " + event.getMessage());
    }

}
