package me.kix.uzi.management.event.block;

import me.kix.uzi.api.event.Event;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EventDamageBlock extends Event {

    private final BlockPos pos;
    private final EnumFacing facing;

    public EventDamageBlock(BlockPos pos, EnumFacing facing) {
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
