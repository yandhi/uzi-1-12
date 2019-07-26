package me.kix.uzi.api.game.impl.entity;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.events.entity.EventEntityDismount;
import me.kix.uzi.api.game.accessors.entity.LivingEntity;
import me.kix.uzi.api.event.events.entity.EventEntityCanBePushed;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends MixinEntity implements LivingEntity {

    @Shadow
    protected boolean isJumping;

    @Shadow
    public abstract boolean isEntityAlive();

    @Shadow
    public abstract boolean isOnLadder();

    @Accessor
    @Override
    public abstract boolean getIsJumping();

    @Inject(method = "dismountEntity", at = @At("HEAD"), cancellable = true)
    private void dismountEntity(Entity entityIn, CallbackInfo ci) {
        EventEntityDismount entityDismount = new EventEntityDismount((EntityLivingBase) (Object) this, entityIn);
        Uzi.INSTANCE.getEventManager().dispatch(entityDismount);

        if (entityDismount.isCancelled()) {
            ci.cancel();
        }
    }

    /**
     * @author Kix
     * @reason To prevent being pushed.
     */
    @Overwrite
    public boolean canBePushed() {
        EventEntityCanBePushed eventEntityCanBePushed = new EventEntityCanBePushed();
        Uzi.INSTANCE.getEventManager().dispatch(eventEntityCanBePushed);
        return !eventEntityCanBePushed.isCancelled() && (this.isEntityAlive() && !this.isOnLadder());
    }
}
