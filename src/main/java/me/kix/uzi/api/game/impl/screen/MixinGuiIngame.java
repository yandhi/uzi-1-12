package me.kix.uzi.api.game.impl.screen;

import me.kix.uzi.Uzi;
import me.kix.uzi.management.event.render.EventRender;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.ScoreObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Kix
 * Created in Apr 2019
 */
@Mixin(GuiIngame.class)
public class MixinGuiIngame {

    @Inject(method = "renderPotionEffects", at = @At("HEAD"), cancellable = true)
    private void renderPotionEffects(ScaledResolution resolution, CallbackInfo ci) {
        EventRender.Potions potions = new EventRender.Potions();
        Uzi.INSTANCE.getEventManager().dispatch(potions);

        if(potions.isCancelled()){
            ci.cancel();
        }
    }

    @Inject(method = "renderScoreboard", at = @At("HEAD"), cancellable = true)
    private void renderScoreboard(ScoreObjective objective, ScaledResolution scaledRes, CallbackInfo ci) {
        EventRender.Scoreboard scoreboard = new EventRender.Scoreboard();
        Uzi.INSTANCE.getEventManager().dispatch(scoreboard);

        if(scoreboard.isCancelled()){
            ci.cancel();
        }
    }

    @Inject(method = "renderPumpkinOverlay", at = @At("HEAD"), cancellable = true)
    private void renderPumpkinOverlay(ScaledResolution scaledRes, CallbackInfo ci) {
        EventRender.Pumpkin pumpkin = new EventRender.Pumpkin();
        Uzi.INSTANCE.getEventManager().dispatch(pumpkin);

        if(pumpkin.isCancelled()){
            ci.cancel();
        }
    }

    @Inject(method = "renderPortal", at = @At("HEAD"), cancellable = true)
    private void renderPortal(float timeInPortal, ScaledResolution scaledRes, CallbackInfo ci) {
        EventRender.Portal portal = new EventRender.Portal();
        Uzi.INSTANCE.getEventManager().dispatch(portal);

        if(portal.isCancelled()){
            ci.cancel();
        }
    }
}
