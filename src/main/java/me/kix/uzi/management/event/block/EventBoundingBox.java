package me.kix.uzi.management.event.block;

import me.kix.uzi.api.event.cancellable.EventCancellable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public class EventBoundingBox extends EventCancellable {
    private final Block block;
    private final BlockPos pos;
    private AxisAlignedBB aabb;
    private final List<AxisAlignedBB> collidingBoxes;
    private final Entity entity;

    public EventBoundingBox(Block block, BlockPos pos, AxisAlignedBB aabb, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity) {
        this.block = block;
        this.pos = pos;
        this.aabb = aabb;
        this.collidingBoxes = collidingBoxes;
        this.entity = entity;
    }

    public void setAabb(AxisAlignedBB aabb) {
        this.aabb = aabb;
    }

    public Block getBlock() {
        return this.block;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.aabb;
    }

    public List<AxisAlignedBB> getCollidingBoxes() {
        return this.collidingBoxes;
    }

    public Entity getEntity() {
        return this.entity;
    }
}
