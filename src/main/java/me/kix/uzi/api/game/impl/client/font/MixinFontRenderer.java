package me.kix.uzi.api.game.impl.client.font;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.game.accessors.client.font.TextRenderer;
import me.kix.uzi.api.event.events.render.EventRenderString;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer implements TextRenderer {

    @Accessor
    @Override
    public abstract int[] getColorCode();

    @Shadow
    protected abstract int renderString(String text, float x, float y, int color, boolean shadow);

    @Shadow
    protected abstract void resetStyles();

    @Shadow protected abstract void enableAlpha();

    /**
     * Lets us do Names easier.
     *
     * @author Kix
     * @reason To allow us to have friend names hidden.
     */
    @Overwrite
    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        if (Uzi.INSTANCE.getFriendManager() != null)
            text = Uzi.INSTANCE.getFriendManager().getReplacedText(text);
        EventRenderString event = new EventRenderString(text);
        Uzi.INSTANCE.getEventManager().dispatch(event);
        text = event.getText();
        enableAlpha();
        this.resetStyles();
        int i;

        if (dropShadow) {
            i = this.renderString(text, x + 1.0F, y + 1.0F, color, true);
            i = Math.max(i, this.renderString(text, x, y, color, false));
        } else {
            i = this.renderString(text, x, y, color, false);
        }

        return i;
    }

}
