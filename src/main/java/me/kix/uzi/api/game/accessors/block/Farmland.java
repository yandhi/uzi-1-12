package me.kix.uzi.api.game.accessors.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * An object that can farmed on.
 *
 * @author Kix
 * @since 8/27/18
 */
public interface Farmland {

    /**
     * Whether or not the specified position has been planted in the given world.
     *
     * @param world The world being searched for the piece of farmland.
     * @param pos   The location of the questionable farmland.
     * @return Whether or not that given piece of land is farmland.
     */
    boolean planted(World world, BlockPos pos);
}
