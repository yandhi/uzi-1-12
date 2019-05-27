package me.kix.uzi.api.game.impl.block;

import me.kix.uzi.api.game.accessors.block.Farmland;
import net.minecraft.block.BlockFarmland;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author jackson
 * @since 8/27/18
 */
@Mixin(BlockFarmland.class)
public abstract class MixinBlockFarmland implements Farmland {

	@Shadow protected abstract boolean hasCrops(World worldIn, BlockPos pos);

	@Override
	public boolean planted(World world, BlockPos pos) {
		return hasCrops(world, pos);
	}
}
