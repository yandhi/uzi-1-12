package me.kix.uzi.management.ui.tab.item.impl;

import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.management.ui.tab.focus.Focusable;
import me.kix.uzi.management.ui.tab.item.TabComponent;
import org.lwjgl.input.Keyboard;

/**
 * An implementation of {@link me.kix.uzi.management.ui.tab.item.Item} that is focusable.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public abstract class FocusTabComponent extends TabComponent implements Focusable {

    /**
     * Whether or not the item is focused.
     */
    private boolean focused;

    public FocusTabComponent(String name, GuiManager guiManager, Rectangle renderPosition) {
        super(name, guiManager, renderPosition);
    }

    @Override
    public void handleKeys(int keyCode) {
        if(isHovered()) {
            if (keyCode == Keyboard.KEY_RIGHT) {
                if (!this.focused) {
                    this.focused = true;
                }
            } else if (keyCode == Keyboard.KEY_LEFT) {
                if (this.focused) {
                    this.focused = false;
                }
            }
        }
    }

    @Override
    public boolean isFocused() {
        return focused;
    }
}
