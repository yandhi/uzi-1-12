package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.input.chat.EventSendOffChatMessage;

import java.util.HashMap;
import java.util.Map;

public class ChatEmotes extends ToggleablePlugin {

    private final Map<String, String> emotes = new HashMap<>();

    public ChatEmotes() {
        super("ChatEmotes", Category.MISCELLANEOUS);
        setDisplay("Chat Emotes");
        setColor(0xFF9CFCFF);
        emotes.put("lenny", "( ͡° ͜ʖ ͡°)");
        emotes.put("gangster", "̿̿ ̿̿ ̿̿ ̿'̿'\\̵͇̿̿\\з= ( ▀ ͜͞ʖ▀) =ε/̵͇̿̿/’̿’̿ ̿ ̿̿ ̿̿ ̿̿");
        emotes.put("shrug", "¯\\_(ツ)_/¯");
        emotes.put("meh", "ಠ_ಠ");
        emotes.put("cash", "[̲̅$̲̅(̲̅5̲̅)̲̅$̲̅]");
        emotes.put("kawaii", "(ᵔᴥᵔ)");
        emotes.put("koala", "ʕ•ᴥ•ʔ");
        emotes.put("arrows", ">_>");
        emotes.put("uzi", "⌐╦╦═─");
    }

    @Register
    public void onMessageSendOff(EventSendOffChatMessage event) {
        for (String identifier : emotes.keySet()) {
            if (event.getMessage().contains("=" + identifier)) {
                event.setMessage(event.getMessage().replaceAll("=" + identifier, emotes.get(identifier)));
            }
        }
    }

}
