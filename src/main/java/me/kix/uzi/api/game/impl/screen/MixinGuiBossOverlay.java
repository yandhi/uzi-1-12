package me.kix.uzi.api.game.impl.screen;

import me.kix.uzi.Uzi;
import me.kix.uzi.management.event.render.EventRender;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Kix
 * Created in Apr 2019
 */
@Mixin(GuiBossOverlay.class)
public class MixinGuiBossOverlay {

    @Inject(method = "renderBossHealth", at = @At("HEAD"), cancellable = true)
    private void renderBossHealth(CallbackInfo ci) {
        EventRender.Bossbar bossbar = new EventRender.Bossbar();
        Uzi.INSTANCE.getEventManager().dispatch(bossbar);

        if(bossbar.isCancelled()){
            ci.cancel();
        }
    }

}
