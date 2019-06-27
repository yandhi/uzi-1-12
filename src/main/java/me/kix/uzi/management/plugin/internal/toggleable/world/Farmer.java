package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.block.Farmland;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.IPlantable;

import java.awt.*;

/**
 * @author Kix
 * @since 8/27/18
 */
public class Farmer extends ToggleablePlugin {

	public Farmer() {
		super("Farmer", Category.WORLD);
		setColor(new Color(0xD70051).getRGB());
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		int slot = mc.player.inventory.currentItem;
		BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		Block block = mc.world.getBlockState(pos).getBlock();
		// If conditions are met for planting.
		if (block == Blocks.FARMLAND && mc.player.inventory.getStackInSlot(slot).getItem() instanceof IPlantable) {
			// We plant.
			BlockFarmland farmland = (BlockFarmland) block;
			Farmland iFarmland = (Farmland) farmland;
			if (iFarmland.planted(mc.world, pos)) {
				event.getViewAngles().setPitch(90);
				if (mc.playerController.processRightClickBlock(mc.player, mc.world, pos, EnumFacing.DOWN, new Vec3d(0.0, 0.0, 0.0), EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS) {
					mc.player.swingArm(EnumHand.MAIN_HAND);
				}
			}
		}
		if (isHarvestable(block, pos)) {
			event.getViewAngles().setPitch(90);
			mc.player.swingArm(EnumHand.MAIN_HAND);
			mc.playerController.clickBlock(pos, EnumFacing.UP);
		}
	}

	private boolean isHarvestable(Block block, BlockPos pos) {
		if (block instanceof BlockCrops) {
			return block.getMetaFromState(mc.world.getBlockState(pos)) == 7;
		} else if (block instanceof BlockNetherWart) {
			return block.getMetaFromState(mc.world.getBlockState(pos)) == 3;
		}
		return block instanceof BlockReed;
	}


}
