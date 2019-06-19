package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;

/**
 * A flight through capabilities.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class CapabilityFly extends ToggleablePlugin {

    public CapabilityFly() {
        super("CapabilityFly", Category.MOVEMENT);
        setDisplay("Capability Fly");
    }

    @Register
    public void onUpdate(EventUpdate.Pre pre) {
        mc.player.capabilities.allowFlying = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.player.capabilities.allowFlying = false;
    }
}
