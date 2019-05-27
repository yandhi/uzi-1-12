package me.kix.uzi.api.game.accessors.block;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

/**
 * @author jackson
 * @since 8/26/18
 */
public interface DoublePlant {

	BlockDoublePlant.EnumPlantType getVariant(BlockPos pos, IBlockState state);

}
