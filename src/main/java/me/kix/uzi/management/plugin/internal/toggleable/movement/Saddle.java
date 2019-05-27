package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

import java.awt.*;

/**
 * Emulates a saddle being on riding entities.
 *
 * @author jackson
 * @since 8/31/18
 */
public class Saddle extends ToggleablePlugin {

	public Saddle() {
		super("Saddle", Category.MOVEMENT);
		setColor(new Color(0xD35700).getRGB());
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		if (mc.player != null) {
			EntityPlayerSP player = mc.player;
			if (player.getRidingEntity() != null) {
				Entity ride = player.getRidingEntity();
				if (mc.player.movementInput.forwardKeyDown) {
					ride.motionX *= 0.7;
					ride.motionZ *= 0.7;
				} else {
					ride.motionX *= 0;
					ride.motionZ *= 0;
				}
			}
		}
	}

}
