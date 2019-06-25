package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.block.EventBoundingBox;
import me.kix.uzi.management.event.block.EventPushOutOfBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

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
    public void onPushOutOfBlocks(EventPushOutOfBlocks pushOutOfBlocks) {
        pushOutOfBlocks.setCancelled(true);
    }

    @Register
    public void onBoundingBox(EventBoundingBox boundingBox) {
        if (boundingBox.getBoundingBox() != null && isInsideBlock()) {
            boundingBox.setAabb(null);
        }
    }

    /**
     * @return Whether or not the player is inside of a block.
     */
    private boolean isInsideBlock() {
        for (int x = MathHelper.floor(mc.player.getEntityBoundingBox().minX); x < MathHelper.floor(mc.player.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int y = MathHelper.floor(mc.player.getEntityBoundingBox().minY); y < MathHelper.floor(mc.player.getEntityBoundingBox().maxY) + 1; ++y) {
                for (int z = MathHelper.floor(mc.player.getEntityBoundingBox().minZ); z < MathHelper.floor(mc.player.getEntityBoundingBox().maxZ) + 1; ++z) {
                    Block block = Minecraft.getMinecraft().world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != Blocks.AIR && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.world.getBlockState(new BlockPos(x, y, z)), mc.world, new BlockPos(x, y, z));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if (boundingBox != null && mc.player.getEntityBoundingBox().intersects(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
