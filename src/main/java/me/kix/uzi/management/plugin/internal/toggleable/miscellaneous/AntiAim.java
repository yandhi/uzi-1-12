package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;

import java.awt.*;

/**
 * @author Kix
 * @since 9/1/18
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
		event.getViewAngles().setYaw(180);
		mc.player.rotationYawHead = 180;
	}

}
