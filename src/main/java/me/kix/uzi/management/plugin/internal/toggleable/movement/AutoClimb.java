package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;

import me.kix.uzi.api.event.events.entity.EventUpdate;

/**
 * Automatically goes up ladders.
 *
 * @author Kix
 * @since 10/1/18
 */
public class AutoClimb extends ToggleablePlugin {

	public AutoClimb() {
		super("AutoClimb", Category.MOVEMENT);
		setColor(0xFFCFA439);
		setDisplay("Auto Climb");
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		if (mc.player.isOnLadder()) {
			mc.player.motionY = 0.1;
		}
	}

}
