package me.kix.uzi.api.event.events.block;

import me.kix.uzi.api.event.Event;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * @author Kix
 * @since 9/30/18
 */
public class EventClickBlock extends Event {

	private final BlockPos pos;
	private final EnumFacing facing;

	public EventClickBlock(BlockPos pos, EnumFacing facing) {
		this.pos = pos;
		this.facing = facing;
	}

	public BlockPos getPos() {
		return pos;
	}

	public EnumFacing getFacing() {
		return facing;
	}
}
