package me.kix.uzi.api.game.impl.screen;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.events.render.EventRenderTextBox;
import me.kix.uzi.api.util.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * The box where we type's mixin :)
 *
 * @author jackson
 * @since 11/27/2021
 */
@Mixin(GuiTextField.class)
public abstract class MixinGuiTextField {

    @Shadow
    public abstract boolean getVisible();

    @Shadow
    public abstract boolean getEnableBackgroundDrawing();

    @Shadow
    public int x;

    @Shadow
    public int y;

    @Shadow
    public int width;

    @Shadow
    public int height;

    @Shadow
    private boolean isEnabled;

    @Shadow
    private int enabledColor;

    @Shadow
    private int disabledColor;

    @Shadow
    private int lineScrollOffset;

    @Shadow
    private int cursorPosition;

    @Shadow
    private int selectionEnd;

    @Shadow
    @Final
    private FontRenderer fontRenderer;

    @Shadow
    private String text;

    @Shadow
    public abstract int getWidth();

    @Shadow
    private int cursorCounter;

    @Shadow
    private boolean isFocused;

    @Shadow
    private boolean enableBackgroundDrawing;

    @Shadow
    public abstract int getMaxStringLength();

    @Shadow
    protected abstract void drawSelectionBox(int startX, int startY, int endX, int endY);

    /**
     * @author jackson
     * @reason The chat selection field
     */
    @Overwrite
    public void drawTextBox() {
        if (this.getVisible()) {
            if (this.getEnableBackgroundDrawing()) {
                drawRectangle(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, -6250336);
                drawRectangle(this.x, this.y, this.x + this.width, this.y + this.height, -16777216);
            }

            int i = this.isEnabled ? this.enabledColor : this.disabledColor;
            int j = this.cursorPosition - this.lineScrollOffset;
            int k = this.selectionEnd - this.lineScrollOffset;
            String s = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag;
            int l = this.enableBackgroundDrawing ? this.x + 4 : this.x;
            int i1 = this.enableBackgroundDrawing ? this.y + (this.height - 8) / 2 : this.y;
            int j1 = l;

            if (k > s.length()) {
                k = s.length();
            }

            if (!s.isEmpty()) {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = drawStringWithShadow(s1, (float) l, (float) i1, i);
            }

            boolean flag2 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            int k1 = j1;

            if (!flag) {
                k1 = j > 0 ? l + this.width : l;
            } else if (flag2) {
                k1 = j1 - 1;
                --j1;
            }

            if (!s.isEmpty() && flag && j < s.length()) {
                j1 = drawStringWithShadow(s.substring(j), (float) j1, (float) i1, i);
            }

            if (flag1) {
                if (flag2) {
                    drawRectangle(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
                } else {
                    drawStringWithShadow("_", (float) k1, (float) i1, i);
                }
            }

            if (k != j) {
                int l1 = l + this.fontRenderer.getStringWidth(s.substring(0, k));
                this.drawSelectionBox(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRenderer.FONT_HEIGHT);
            }
        }
    }

    /**
     * Makes life tremendously easier.
     */
    private int drawStringWithShadow(String text, float x, float y, int color) {
        EventRenderTextBox.Text textEvent = new EventRenderTextBox.Text((int) x, (int) y, text, color);
        Uzi.INSTANCE.getEventManager().dispatch(textEvent);

        if (!textEvent.isCancelled()) {
            return this.fontRenderer.drawStringWithShadow(text, x, y, color);
        }

        return textEvent.getCallbackReturn();
    }

    /**
     * Also makes life a lot easier.
     */
    private void drawRectangle(int x, int y, int x2, int y2, int color) {
        EventRenderTextBox.Rectangle rectangle = new EventRenderTextBox.Rectangle(x, y, color);
        Uzi.INSTANCE.getEventManager().dispatch(rectangle);

        if (!rectangle.isCancelled()) {
            RenderUtil.drawRect(x, y, x2, y2, color);
        }
    }

}
