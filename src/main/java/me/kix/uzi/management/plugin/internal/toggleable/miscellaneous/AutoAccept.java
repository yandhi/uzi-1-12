package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.input.packet.EventPacket;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;

/**
 * Automatically accepts teleport requests from friends.
 *
 * @author jackson
 * @since 9/1/18
 */
public class AutoAccept extends ToggleablePlugin {

	public AutoAccept() {
		super("AutoAccept", Category.MISCELLANEOUS);
		setColor(new Color(0xDFC15B).getRGB());
		setDisplay("Auto Accept");
	}

	@Register
	public void onReadPacket(EventPacket.Read event) {
		if (event.getPacket() instanceof SPacketChat) {
			SPacketChat chatPacket = (SPacketChat) event.getPacket();
			ITextComponent component = chatPacket.getChatComponent();
			String text = component.getFormattedText();
			Uzi.INSTANCE.getFriendManager().getContents().stream()
					.filter(friend -> text.contains(friend.getLabel()))
					.filter(friend -> text.contains("has requested to teleport to you"))
					.filter(friend -> text.contains("to teleport"))
					.forEach(friend -> mc.player.sendChatMessage("/tpaccept"));
		}
	}

}
