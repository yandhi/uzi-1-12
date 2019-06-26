package me.kix.uzi.api.game.impl.entity;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.game.accessors.entity.LivingEntity;
import me.kix.uzi.management.event.entity.EventEntityCanBePushed;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

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

    /**
     * @author Kix
     */
    @Overwrite
    public boolean canBePushed() {
        EventEntityCanBePushed eventEntityCanBePushed = new EventEntityCanBePushed();
        Uzi.INSTANCE.getEventManager().dispatch(eventEntityCanBePushed);
        return !eventEntityCanBePushed.isCancelled() && (this.isEntityAlive() && !this.isOnLadder());
    }

}
