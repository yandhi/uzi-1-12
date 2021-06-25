package me.kix.uzi.management.ui.tab.item.impl;

import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.ui.tab.item.AbstractItem;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * An implementation of {@link me.kix.uzi.management.ui.tab.item.Item} that executes an action.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public abstract class ButtonItem extends AbstractItem {

    /**
     * The state of the button.
     */
    private boolean state;

    public ButtonItem(String label) {
        super(label);
    }

    @Override
    public void draw(int x, int y, int width, int height, int foreground, int background) {
        RenderUtil.drawRect(x, y, x + width, y + height, isHovered() ? foreground : background);
        if (state) {
            RenderUtil.drawRect(x + width - 5, y, x + width, y + height, new Color(foreground).darker().getRGB());
        }
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(getLabel(), x + 2, y + ((height - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) / 2f), 0xFFFFFFFF);
    }

    /**
     * Executes the action for the button.
     */
    protected void executeAction() {
        state = !state;
    }

    @Override
    public void handleKeys(int keyCode) {
        if (isHovered()) {
            if (keyCode == Keyboard.KEY_RETURN) {
                executeAction();
            }
        }
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
