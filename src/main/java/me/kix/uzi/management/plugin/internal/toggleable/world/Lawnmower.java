package me.kix.uzi.management.plugin.internal.toggleable.world;


import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.block.DoublePlant;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

/**
 * @author Kix
 * @since 8/26/18
 */
public class Lawnmower extends ToggleablePlugin {

	public Lawnmower() {
		super("Lawnmower", Category.WORLD);
		setColor(0xFF67BC04);
	}

	@Register
	public void onUpdate(EventUpdate.Pre event) {
		BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
		Block block = mc.world.getBlockState(pos).getBlock();
		if (block instanceof BlockTallGrass || block instanceof BlockDoublePlant && ((DoublePlant) block).getVariant(pos, block.getDefaultState()) == BlockDoublePlant.EnumPlantType.GRASS) {
			mc.player.swingArm(EnumHand.MAIN_HAND);
			event.getViewAngles().setPitch(90);
			mc.playerController.clickBlock(pos, EnumFacing.DOWN);
		}
	}

}
