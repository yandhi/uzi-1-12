package me.kix.uzi.management.plugin.internal.toggleable.world;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemHoe;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * @author Kix
 * @since 8/26/18
 */
public class Tractor extends ToggleablePlugin {

	public Tractor() {
		super("Tractor", Category.WORLD);
		setColor(0xFFB05B54);
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ);
		Block block = mc.world.getBlockState(pos).getBlock();
		if ((block instanceof BlockGrass || block instanceof BlockDirt) && mc.player.getHeldItemMainhand().getItem() != Items.AIR && mc.player.getHeldItemMainhand().getItem() instanceof ItemHoe) {
			if (block != Blocks.FARMLAND) {
				event.getViewAngles().setPitch(90);
				if (mc.playerController.processRightClickBlock(mc.player, mc.world, pos, EnumFacing.UP, new Vec3d(0.0, 0.0, 0.0), EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS) {
					mc.player.swingArm(EnumHand.MAIN_HAND);
				}
			}
		}
	}
}
