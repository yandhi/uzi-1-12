package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.entity.EventApplyEntityCollision;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.block.EventBoundingBox;
import me.kix.uzi.api.event.events.block.EventOpaqueBlock;
import me.kix.uzi.api.event.events.block.EventPushOutOfBlocks;

/**
 * No-clip for the player.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class Phase extends ToggleablePlugin {

    public Phase() {
        super("Phase", Category.WORLD);
    }

    @Register
    public void onOpaqueBlock(EventOpaqueBlock opaqueBlock) {
        opaqueBlock.setCancelled(true);
    }

    @Register
    public void onApplyEntityCollision(EventApplyEntityCollision applyEntityCollision){
        applyEntityCollision.setCancelled(true);
    }

    @Register
    public void onPushOutOfBlocks(EventPushOutOfBlocks pushOutOfBlocks) {
        pushOutOfBlocks.setCancelled(true);
    }

    @Register
    public void onBoundingBox(EventBoundingBox boundingBox) {
        if (mc.player.getRidingEntity() != null && boundingBox.getEntity() == mc.player.getRidingEntity()) {
            if (mc.gameSettings.keyBindJump.isKeyDown() && boundingBox.getPos().getY() >= mc.player.getRidingEntity().posY) {
                boundingBox.setCancelled(true);
            }
            if (boundingBox.getPos().getY() >= mc.player.getRidingEntity().posY) {
                boundingBox.setCancelled(true);
            }
        } else if (boundingBox.getEntity() == mc.player) {
            if (mc.gameSettings.keyBindJump.isKeyDown() && boundingBox.getPos().getY() >= mc.player.posY) {
                boundingBox.setCancelled(true);
            }
            if (boundingBox.getPos().getY() >= mc.player.posY) {
                boundingBox.setCancelled(true);
            }
        }
    }
}
