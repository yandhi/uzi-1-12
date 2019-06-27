package me.kix.uzi.api.game.impl.block;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.events.block.EventBoundingBox;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(Block.class)
public abstract class MixinBlock {

    @Shadow
    @Final
    public static AxisAlignedBB NULL_AABB;

    @Shadow
    protected static void addCollisionBoxToList(BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable AxisAlignedBB blockBox) {
    }

    /**
     * Adds a collision box to list.
     *
     * @author kix
     */
    @Overwrite
    @Deprecated
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        Block block = (Block) (Object) (this);
        AxisAlignedBB bb = state.getCollisionBoundingBox(worldIn, pos);

        EventBoundingBox bbEvent = new EventBoundingBox(block, pos, bb, collidingBoxes, entityIn);
        Uzi.INSTANCE.getEventManager().dispatch(bbEvent);

        addCollisionBoxToList(pos, entityBox, collidingBoxes, bbEvent.getBoundingBox());
    }
}
