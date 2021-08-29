package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;

import java.awt.*;

/**
 * Makes the player's server angles appear to be different than they actually are.
 *
 * <p>
 * This plugin is from CSGO cheats.
 * </p>
 *
 * @author Kix
 * @since 9/1/18 (Updated 9/24/21)
 */
public class AntiAim extends ToggleablePlugin {

	public AntiAim() {
		super("AntiAim", Category.MISCELLANEOUS);
		setDisplay("Anti Aim");
		setColor(new Color(0x67C360).getRGB());
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		event.getViewAngles().setPitch(180);
		event.getViewAngles().setYaw(mc.player.ticksExisted % 2 == 0 ? 180 : 0);
		mc.player.rotationYawHead = mc.player.ticksExisted % 2 == 0 ? 180 : 0;
	}
}
