package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventMotion;

import java.awt.*;

/**
 * @author Kix
 * @since 9/1/18
 */
public class NoSlow extends ToggleablePlugin {

	public NoSlow() {
		super("NoSlow", Category.MOVEMENT);
		setColor(new Color(0x5B9958).getRGB());
		setDisplay("No Slow");
	}

	@Register
	public void onMotionUpdate(EventMotion event) {
		if (mc.player.isHandActive()) {
			event.setX(event.getX() * 5);
			event.setZ(event.getZ() * 5);
		}
	}

}
