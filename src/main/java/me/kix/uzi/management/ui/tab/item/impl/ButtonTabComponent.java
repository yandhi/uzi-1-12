package me.kix.uzi.management.ui.tab.item.impl;

import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.ui.tab.item.TabComponent;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * An implementation of {@link me.kix.uzi.management.ui.tab.item.Item} that executes an action.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public abstract class ButtonTabComponent extends TabComponent {

    /**
     * The state of the button.
     */
    private boolean state;

    public ButtonTabComponent(String name, GuiManager guiManager, Rectangle renderPosition) {
        super(name, guiManager, renderPosition);
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
