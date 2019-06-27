package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;

public class Flight extends ToggleablePlugin {

	public Flight() {
		super("Flight", Category.MOVEMENT);
		setColor(0xFF1CE81E);
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		double offset = 0.00000000052;
		if (mc.player.ticksExisted % 2 == 0) {
			if (mc.player.onGround) {
				offset = 0.0;
			}
			offset = -0.00000000052;
		}
		mc.player.motionY = 0.0;
		mc.player.setPosition(mc.player.posX, mc.player.posY + offset, mc.player.posZ);
		mc.player.cameraYaw = 0.15f;
	}

}
