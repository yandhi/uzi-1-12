package me.kix.uzi.api.game.impl.block;

import me.kix.uzi.api.game.accessors.block.DoublePlant;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author Kix
 * @since 8/26/18
 */
@Mixin(BlockDoublePlant.class)
public abstract class MixinBlockDoublePlant implements DoublePlant {

	@Shadow
	protected abstract BlockDoublePlant.EnumPlantType getType(IBlockAccess p_getType_1_, BlockPos p_getType_2_, IBlockState p_getType_3_);

	@Override
	public BlockDoublePlant.EnumPlantType getVariant(BlockPos pos, IBlockState state) {
		return getType(Minecraft.getMinecraft().world, pos, state);
	}
}
