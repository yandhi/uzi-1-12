package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.StringUtils;

/**
 * Greets players that join / leave.
 * 
 * @author Kix
 * @since April 2019
 */
public class Greeter extends ToggleablePlugin {

    public Greeter() {
        super("Greeter", Category.MISCELLANEOUS);
    }

    @Register
    public void onReadPacket(EventPacket.Read read) {
        if (read.getPacket() instanceof SPacketChat) {
            SPacketChat chat = (SPacketChat) read.getPacket();
            String message = StringUtils.stripControlCodes(chat.getChatComponent().getFormattedText());
            String[] splitMessage = message.split(" ");
			String name = splitMessage[0];
			if(message.contains("joined")) {
				mc.player.sendChatMessage("wb " + name);
			}else if(message.contains("left")){
				mc.player.sendChatMessage("cya " + name);
			}
        }
    }
}