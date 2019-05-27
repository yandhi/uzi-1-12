package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventMotion;

/**
 * @author Kix
 * @since 5/26/2018
 */
public class SafeWalk extends ToggleablePlugin {

    public SafeWalk() {
        super("SafeWalk", Category.MOVEMENT);
        setDisplay("Safe Walk");
        setColor(0xFF7EFF86);
    }

    @Register
    public void onMotion(EventMotion event) {
        if (mc.player.onGround && mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(event.getX(), -1, event.getZ())).isEmpty()) {
            event.setX(0);
            event.setZ(0);
        }
    }

}
