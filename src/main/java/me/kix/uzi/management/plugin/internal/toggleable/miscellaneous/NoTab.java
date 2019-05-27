package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.input.packet.EventPacket;
import net.minecraft.network.play.server.SPacketTabComplete;

import java.awt.*;

/**
 * @author jackson
 * @since 9/1/18
 */
public class NoTab extends ToggleablePlugin {

	public NoTab() {
		super("NoTab", Category.MISCELLANEOUS);
		setColor(new Color(0x814662).getRGB());
		setDisplay("No Tab");
	}

	@Register
	public void onSendPacket(EventPacket.Send event) {
		if(event.getPacket() instanceof SPacketTabComplete){
			event.setCancelled(true);
		}
	}

}
