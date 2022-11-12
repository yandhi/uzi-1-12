package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ITextComponent;

/**
 * Pings the user once they are mentioned in chat.
 * 
 * @author Kix
 * @since April 2019
 */
public class Mentions extends ToggleablePlugin {

    public Mentions() {
        super("Mentions", Category.QOL);
    }

    @Register
    public void onReadPacket(EventPacket.Read read) {
        if (read.getPacket() instanceof SPacketChat) {
            SPacketChat chatPacket = (SPacketChat) read.getPacket();
            ITextComponent chatComponent = chatPacket.getChatComponent();
            String chatText = chatComponent.getFormattedText();
            if(chatText.contains(mc.player.getName())){
                chatComponent.getStyle().setBold(true);
                chatComponent.getStyle().setItalic(true);
            }
        }
    }

}