package me.kix.uzi.api.game.impl.entity;

import me.kix.uzi.api.game.accessors.entity.IEntityLivingBase;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends MixinEntity implements IEntityLivingBase {

    @Shadow protected boolean isJumping;

    @Accessor
    @Override
    public abstract boolean getIsJumping();

}
