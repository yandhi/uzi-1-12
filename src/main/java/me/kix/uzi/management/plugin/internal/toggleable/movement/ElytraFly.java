package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;

import java.awt.*;

/**
 * @author jackson
 * @since 8/29/18
 */
public class ElytraFly extends ToggleablePlugin {

	public ElytraFly() {
		super("ElytraFly", Category.MOVEMENT);
		setDisplay("Elytra Fly");
		setColor(new Color(0x9F4DF8).getRGB());
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		if (mc.player.isElytraFlying()) {
			mc.player.capabilities.isFlying = true;
		} else {
			mc.player.capabilities.isFlying = false;
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
		mc.player.capabilities.isFlying = false;
	}
}
