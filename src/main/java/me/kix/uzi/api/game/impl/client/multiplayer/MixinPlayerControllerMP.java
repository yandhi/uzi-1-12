package me.kix.uzi.api.game.impl.client.multiplayer;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.game.accessors.client.multiplayer.IPlayerControllerMP;
import me.kix.uzi.management.event.block.EventClickBlock;
import me.kix.uzi.management.event.block.EventDamageBlock;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP implements IPlayerControllerMP {
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



}
