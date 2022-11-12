package me.kix.uzi.api.game.impl.screen;

import me.kix.uzi.management.ui.alt.screen.GuiAltManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public abstract class MixinGuiMultiplayer extends MixinGuiScreen {

    @Inject(method = "createButtons", at = @At("HEAD"))
    private void addAltButton(CallbackInfo ci) {
        //buttonList.add(new GuiButton(16, 2, 2, 75, 20, "Alts"));
    }

    @Inject(method = "actionPerformed", at = @At("RETURN"))
    private void setupAltButtonPressed(GuiButton button, CallbackInfo ci) {
//        if (button.id == 16) {
//            Minecraft.getMinecraft().displayGuiScreen(new GuiAltManager(((GuiMultiplayer) (Object) this)));
//        }
    }

}
