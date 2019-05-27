package me.kix.uzi.management.command.commands;

import me.kix.uzi.api.command.argument.factory.registration.RegisterArgument;
import me.kix.uzi.api.command.commands.ArgumentativeCommand;
import net.minecraft.client.Minecraft;

/**
 * @author jackson
 * @since 9/1/18
 */
public class RotationsCommand extends ArgumentativeCommand {

	private final Minecraft mc = Minecraft.getMinecraft();

	public RotationsCommand() {
		super("Rotations", new String[]{"angles", "view", "yaw", "pitch"}, "Allows us to change the player's rotations through a command.");
	}

	@RegisterArgument({"zero", "origin", "reset"})
	public void reset() {
		mc.player.rotationYaw = 0f;
		mc.player.rotationPitch = 0f;
	}

	@RegisterArgument({"set", "amount"})
	public void set(float yaw, float pitch) {
		mc.player.rotationYaw = yaw;
		mc.player.rotationPitch = pitch;
	}

}
