package me.kix.uzi.api.game.impl.renderer;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.game.accessors.renderer.GameRenderManager;
import me.kix.uzi.api.event.events.render.EventPostRenderEntity;
import me.kix.uzi.api.event.events.render.EventPreRenderEntity;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderManager.class)
public abstract class MixinRenderManager implements GameRenderManager {


    @Redirect(method = "doRenderEntity", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/entity/Render.doRender(Lnet/minecraft/entity/Entity;DDDFF)V"))
    @SuppressWarnings("unchecked")
    private void doRenderEntity$doRender(Render render, Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        EventPreRenderEntity pre = new EventPreRenderEntity(render, entity, x, y, z, entityYaw, partialTicks);
        Uzi.INSTANCE.getEventManager().dispatch(pre);
        if (!pre.isCancelled()) {
            render.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
        EventPostRenderEntity post = new EventPostRenderEntity(render, entity, x, y, z, entityYaw, partialTicks);
        Uzi.INSTANCE.getEventManager().dispatch(post);
    }


    @Override
    @Accessor
    public abstract double getRenderPosX();

    @Override
    @Accessor
    public abstract double getRenderPosY();

    @Override
    @Accessor
    public abstract double getRenderPosZ();
}
