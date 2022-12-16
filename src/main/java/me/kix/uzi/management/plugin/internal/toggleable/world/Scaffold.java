package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventMotion;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * @author Kix
 * @since 5/26/2018
 */
public class Scaffold extends ToggleablePlugin {

    public Scaffold() {
        super("Scaffold", Category.WORLD);
        setColor(0xFFFF9BE8);
    }

    @Register
    public void onMotion(EventMotion event) {
        if (mc.player.onGround && mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(event.getX(), -1, event.getZ())).isEmpty()) {
            event.setX(0);
            event.setZ(0);
        }
        BlockPos pos = new BlockPos(mc.player.posX + event.getX(), mc.player.posY - 1, mc.player.posZ + event.getZ());
        int slot = mc.player.inventory.currentItem;
        for (int i = 8; i <= 9; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                ItemBlock iBlock = (ItemBlock) mc.player.inventory.getStackInSlot(i).getItem();
                if (isBlockAbleToBeWalkedOn(iBlock.getBlock().getDefaultState())) {
                    for (EnumFacing facing : EnumFacing.values()) {
                        if (facing == EnumFacing.UP) {
                            continue;
                        }
                        if (isBlockAbleToBeWalkedOn(mc.world.getBlockState(pos.offset(facing)))) {
                            mc.getConnection().sendPacket(new CPacketHeldItemChange(i));
                            mc.player.inventory.currentItem = i;
                            lookAtBlockSide(pos.offset(facing), facing);
                            place(pos.offset(facing), facing);
                            mc.getConnection().sendPacket(new CPacketHeldItemChange(slot));
                            mc.player.inventory.currentItem = slot;
                            return;
                        }
                    }
                }
            }
        }
    }

    private boolean isBlockAbleToBeWalkedOn(IBlockState state) {
        AxisAlignedBB bb = state.getCollisionBoundingBox(mc.world, new BlockPos(0, 0, 0));
        return bb != null && bb.equals(Block.FULL_BLOCK_AABB);
    }

    private void place(BlockPos pos, EnumFacing facing) {
        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getMaterial() == Material.AIR) {
            if (mc.world.getBlockState(pos).getMaterial() != Material.AIR) {
                ItemStack stack = mc.player.getHeldItem(EnumHand.MAIN_HAND);
                int i = stack.getCount();
                EnumActionResult enumactionresult = mc.playerController.processRightClickBlock(mc.player, mc.world, pos, facing.getOpposite(), new Vec3d(0, 0, 0), EnumHand.MAIN_HAND);
                if (enumactionresult == EnumActionResult.SUCCESS) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    if (!stack.isEmpty() && (stack.getCount() != i || mc.playerController.isInCreativeMode())) {
                        mc.entityRenderer.itemRenderer.resetEquippedProgress(EnumHand.MAIN_HAND);
                    }
                }
            }
        }
    }

    private void lookAtBlockSide(BlockPos pos, EnumFacing facing) {
        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getMaterial() == Material.AIR) {
            double d0 = pos.getX() + 0.5 + (float) facing.getOpposite().getXOffset() / 2 - mc.player.posX;
            double d1 = pos.getY() + 0.5 + (float) facing.getOpposite().getYOffset() / 2 - (mc.player.posY + (double) mc.player.getEyeHeight());
            double d2 = pos.getZ() + 0.5 + (float) facing.getOpposite().getZOffset() / 2 - mc.player.posZ;
            double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
            float f = (float) (MathHelper.atan2(d2, d0) * 57.29577951308232D) - 90.0F;
            float f1 = (float) (-(MathHelper.atan2(d1, d3) * 57.29577951308232D));
            mc.getConnection().sendPacket(new CPacketPlayer.Rotation(f, f1, true));
        }
    }

}
