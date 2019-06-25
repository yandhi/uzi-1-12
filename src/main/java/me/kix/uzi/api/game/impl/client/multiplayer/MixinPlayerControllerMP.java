package me.kix.uzi.api.game.impl.client.multiplayer;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.game.accessors.client.multiplayer.PlayerController;
import me.kix.uzi.management.event.block.EventClickBlock;
import me.kix.uzi.management.event.block.EventDamageBlock;
import me.kix.uzi.management.event.block.EventRelativeBlockHardness;
import me.kix.uzi.management.event.entity.EventPostAttack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP implements PlayerController {
    @Accessor
    @Override
    public abstract void setBlockHitDelay(int blockHitDelay);

    @Accessor
    @Override
    public abstract void setCurBlockDamageMP(float curBlockDamageMP);

    @Accessor
    @Override
    public abstract float getCurBlockDamageMP();

    @Inject(method = "onPlayerDamageBlock", at = @At("HEAD"))
    private void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventDamageBlock(posBlock, directionFacing));
    }

    @Inject(method = "clickBlock", at = @At("HEAD"))
    private void clickBlock(BlockPos loc, EnumFacing face, CallbackInfoReturnable<Boolean> cir) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventClickBlock(loc, face));
    }

    @Inject(method = "attackEntity", at = @At("RETURN"))
    private void attackEntity(EntityPlayer playerIn, Entity targetEntity, CallbackInfo ci) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventPostAttack(targetEntity));
    }

    @Redirect(method = "onPlayerDamageBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"))
    private float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos) {
        EventRelativeBlockHardness relativeBlockHardness = new EventRelativeBlockHardness(1f);
        Uzi.INSTANCE.getEventManager().dispatch(relativeBlockHardness);

        return state.getPlayerRelativeBlockHardness(player, worldIn, pos) * relativeBlockHardness.getHardness();
    }

}
