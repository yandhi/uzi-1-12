package me.kix.uzi.api.game.impl.renderer;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.events.render.EventRenderBlockModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Kix
 * @since 6/28/2019
 */
@Mixin(BlockModelRenderer.class)
public class MixinBlockModelRenderer {

    @Inject(method = "renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/BufferBuilder;ZJ)Z", at = @At("HEAD"), cancellable = true)
    private void renderModel(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand, CallbackInfoReturnable<Boolean> cir) {
        EventRenderBlockModel renderBlockModel = new EventRenderBlockModel(stateIn, posIn);
        Uzi.INSTANCE.getEventManager().dispatch(renderBlockModel);

        if(renderBlockModel.isCancelled()){
            cir.cancel();
        }
    }

    @Inject(method = "renderModelSmooth", at = @At("HEAD"))
    private void renderModelSmooth(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand, CallbackInfoReturnable<Boolean> cir) {
        EventRenderBlockModel renderBlockModel = new EventRenderBlockModel(stateIn, posIn);
        Uzi.INSTANCE.getEventManager().dispatch(renderBlockModel);

        if(renderBlockModel.isCancelled()){
            cir.cancel();
        }
    }

    @Inject(method = "renderModelFlat", at = @At("HEAD"))
    private void renderModelFlat(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn, BufferBuilder buffer, boolean checkSides, long rand, CallbackInfoReturnable<Boolean> cir) {
        EventRenderBlockModel renderBlockModel = new EventRenderBlockModel(stateIn, posIn);
        Uzi.INSTANCE.getEventManager().dispatch(renderBlockModel);

        if(renderBlockModel.isCancelled()){
            cir.cancel();
        }
    }
}
