package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.block.EventBoundingBox;
import me.kix.uzi.management.event.block.EventOpaqueBlock;
import me.kix.uzi.management.event.block.EventPushOutOfBlocks;

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
    public void onBoundingBox(EventBoundingBox boundingBox) {
        if (boundingBox.getBoundingBox() != null && mc.player.isSneaking()) {
            boundingBox.setAabb(null);
        }
    }

    @Register
    public void onPushOutOfBlocks(EventPushOutOfBlocks pushOutOfBlocks) {
        pushOutOfBlocks.setCancelled(true);
    }

}
