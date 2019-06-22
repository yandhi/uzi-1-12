package me.kix.uzi.api.game.impl.renderer;

import me.kix.uzi.Uzi;
import me.kix.uzi.management.event.render.EventRenderNameplate;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public class MixinRender {

    @Inject(method = "renderLivingLabel", at = @At("HEAD"), cancellable = true)
    private void renderLivingLabel(Entity entityIn, String str, double x, double y, double z, int maxDistance, CallbackInfo ci) {
        EventRenderNameplate event = new EventRenderNameplate();
        Uzi.INSTANCE.getEventManager().dispatch(event);
        if (event.isCancelled())
            ci.cancel();
    }
}
