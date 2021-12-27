package me.kix.uzi.management.plugin.internal.toggleable.server;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.input.chat.EventSendOffChatMessage;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;

/**
 * Allows users to go "-" then the alias of their friend in order to say their name.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class Names extends ToggleablePlugin {

    public Names() {
        super("Names", Category.SERVER);
    }

    @Register
    public void onSendOffChatMessage(EventSendOffChatMessage sendOffChatMessage) {
        Uzi.INSTANCE.getFriendManager().getContents()
                .forEach(friend -> sendOffChatMessage.setMessage(sendOffChatMessage.getMessage().replaceAll("-" + friend.getAlias(), friend.getLabel())));
    }
}
