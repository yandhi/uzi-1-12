package me.kix.uzi.api.game.accessors.block;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

/**
 * An object that can considered a plant with a 2 block height.
 *
 * @author Kix
 * @since 8/26/18
 */
public interface DoublePlant {

    /**
     * Tells the type of plant that the object is.
     *
     * @param pos   The position of the plant.
     * @param state The block state of the plant.
     * @return The given type of the plant.
     */
    BlockDoublePlant.EnumPlantType getVariant(BlockPos pos, IBlockState state);
}
