package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;

public class Speed extends ToggleablePlugin {

    /**
     * The tick that the player is actually going to speed up on.
     * We use this because essentially this is a yPort where the user will only quicken on a certain tick.
     */
    private boolean speedTick;

    public Speed() {
        super("Speed", Category.MOVEMENT);
        setColor(-6558464);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (mc.player.onGround && !mc.player.movementInput.jump) {
            speedTick = !speedTick;
            if (!speedTick) {
                boolean isUnderBlocks = !mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0, 1, 0)).isEmpty();
                event.setPosY(event.getPosY() + (isUnderBlocks ? 0.2 : 0.4));
            }
            mc.player.motionX *= (speedTick ? 2D : 0.701);
            mc.player.motionZ *= (speedTick ? 2D : 0.701);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        speedTick = true;
    }
}
