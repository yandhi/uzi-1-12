package me.kix.uzi.api.game.impl.entity;

import me.kix.uzi.Uzi;
import me.kix.uzi.management.event.block.EventOpaqueBlock;
import me.kix.uzi.management.event.entity.EventEntityPushedByWater;
import me.kix.uzi.management.event.entity.EventPlayerDeath;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Kix
 * Created in 06 2019.
 */
@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends MixinEntityLivingBase {

    @Shadow
    public PlayerCapabilities capabilities;

    /**
     * @author Kix
     */
    @Overwrite
    public boolean isPushedByWater() {
        EventEntityPushedByWater pushedByWater = new EventEntityPushedByWater();
        Uzi.INSTANCE.getEventManager().dispatch(pushedByWater);
        return !pushedByWater.isCancelled() && !this.capabilities.isFlying;
    }

    /**
     * @author Kix
     */
    @Overwrite
    public boolean isEntityInsideOpaqueBlock() {
        EventOpaqueBlock opaqueBlock = new EventOpaqueBlock();
        Uzi.INSTANCE.getEventManager().dispatch(opaqueBlock);
        return !opaqueBlock.isCancelled() && super.isEntityInsideOpaqueBlock();
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(DamageSource cause, CallbackInfo ci) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventPlayerDeath((EntityPlayer) (Object) this, cause));
    }
}
