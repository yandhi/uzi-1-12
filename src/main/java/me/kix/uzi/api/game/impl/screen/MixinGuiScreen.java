package me.kix.uzi.api.game.impl.screen;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {

    @Shadow
    protected List<GuiButton> buttonList;

}
