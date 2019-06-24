package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;

import java.awt.*;

/**
 * @author Kix
 * @since 8/28/18
 */
public class NoFall extends ToggleablePlugin {

	public NoFall() {
		super("NoFall", Category.MOVEMENT);
		setDisplay("No Fall");
		setColor(new Color(0x1B6B13).getRGB());
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		if (mc.player.fallDistance > 3) {
			event.setOnGround(true);
		}
	}

}
