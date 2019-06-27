package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.network.play.client.CPacketPlayer;

import java.awt.*;

/**
 * @author Kix
 * @since 10/1/18
 */
public class PacketFly extends ToggleablePlugin {

	public PacketFly() {
		super("PacketFly", Category.MOVEMENT);
		setDisplay("Packet Fly");
		setColor(new Color(0x629147).getRGB());
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + mc.player.motionX * 11,
				mc.player.posY + (mc.gameSettings.keyBindJump.isKeyDown() ? 0.0625 : mc.gameSettings.keyBindSneak.isKeyDown() ? -0.0625 : 0),
				mc.player.posZ + mc.player.motionZ * 11, false));
		mc.player.setPosition(mc.player.posX + mc.player.motionX * 11,
				mc.player.posY + (mc.gameSettings.keyBindJump.isKeyDown() ? 0.0625 : mc.gameSettings.keyBindSneak.isKeyDown() ? -0.0625 : 0),
				mc.player.posZ + mc.player.motionZ * 11);

		mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + mc.player.motionX * 11,
				mc.player.posY + 250,
				mc.player.posZ + mc.player.motionZ * 11, false));
		mc.player.setPosition(mc.player.posX + mc.player.motionX * 11,
				mc.player.posY + 250,
				mc.player.posZ + mc.player.motionZ * 11);
	}
}
