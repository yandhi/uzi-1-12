package me.kix.uzi.api.game.accessors.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author jackson
 * @since 8/27/18
 */
public interface Farmland {

	boolean planted(World world, BlockPos pos);
}
