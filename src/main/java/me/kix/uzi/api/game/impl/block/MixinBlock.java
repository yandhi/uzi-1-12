package me.kix.uzi.api.game.impl.block;

import me.kix.uzi.Uzi;
import me.kix.uzi.management.event.block.EventBoundingBox;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(Block.class)
public class MixinBlock {

    private EventBoundingBox bbEvent;

    @Shadow
    @Final
    public static AxisAlignedBB NULL_AABB;

    @Inject(method = "addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V",
            at = @At("HEAD"))
    private void in(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState, CallbackInfo ci) {
        Block block = (Block) (Object) (this);
        bbEvent = new EventBoundingBox(block, pos, block.getCollisionBoundingBox(state, world, pos), collidingBoxes, entity);
        Uzi.INSTANCE.getEventManager().dispatch(bbEvent);
    }

    @Redirect(method = "addCollisionBoxToList(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lnet/minecraft/entity/Entity;Z)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getCollisionBoundingBox(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/AxisAlignedBB;"))
    private AxisAlignedBB getBB(IBlockState state, IBlockAccess world, BlockPos pos) {
        AxisAlignedBB bb = (bbEvent == null) ?
                state.getCollisionBoundingBox(world, pos):
                bbEvent.getBoundingBox();
        bbEvent = null;
        return bb;
    }
}
