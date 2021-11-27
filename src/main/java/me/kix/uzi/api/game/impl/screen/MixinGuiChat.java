package me.kix.uzi.api.game.impl.screen;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.events.render.EventRenderTextBox;
import me.kix.uzi.api.util.render.RenderUtil;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * The game's little rectangle at the bottom I guess?
 *
 * @author jackson
 * @since 11/27/2021
 */
@Mixin(GuiChat.class)
public abstract class MixinGuiChat extends MixinGuiScreen {

    @Shadow
    protected GuiTextField inputField;

    /**
     * @author jackson
     * @reason no more ugly bar.
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        EventRenderTextBox.Rectangle textBox = new EventRenderTextBox.Rectangle(2, this.height - 14, Integer.MIN_VALUE);
        Uzi.INSTANCE.getEventManager().dispatch(textBox);

        if(!textBox.isCancelled()) {
            RenderUtil.drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        }
        this.inputField.drawTextBox();
        ITextComponent itextcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

        if (itextcomponent != null && itextcomponent.getStyle().getHoverEvent() != null) {
            this.handleComponentHover(itextcomponent, mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
