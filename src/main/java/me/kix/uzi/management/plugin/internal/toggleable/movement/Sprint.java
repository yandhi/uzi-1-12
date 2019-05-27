package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;

public class Sprint extends ToggleablePlugin {

    public Sprint() {
        super("Sprint", Category.MOVEMENT);
        setHidden(true);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (!mc.player.isSprinting() && !mc.player.isSneaking() && !mc.player.isCollidedHorizontally && mc.player.getFoodStats().getFoodLevel() > 6f && mc.player.movementInput.forwardKeyDown)
            mc.player.setSprinting(true);
    }

}
