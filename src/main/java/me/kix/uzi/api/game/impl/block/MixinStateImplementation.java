package me.kix.uzi.api.game.impl.block;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.events.block.EventBoundingBox;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Kix
 * @since 6/27/2019
 */
@Mixin(BlockStateContainer.StateImplementation.class)
public class MixinStateImplementation {

    @Shadow
    @Final
    private Block block;

    public void addCollisionBoxToList(World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185908_6_) {
        EventBoundingBox boundingBox = new EventBoundingBox(worldIn.getBlockState(pos).getBlock(), pos, entityBox, collidingBoxes, entityIn);
        Uzi.INSTANCE.getEventManager().dispatch(boundingBox);

        if (!boundingBox.isCancelled()) {
            this.block.addCollisionBoxToList((BlockStateContainer.StateImplementation) (Object) this, worldIn, pos, boundingBox.getBoundingBox(), collidingBoxes, entityIn, p_185908_6_);
        }
    }
}
