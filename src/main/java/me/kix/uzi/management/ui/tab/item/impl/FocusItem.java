package me.kix.uzi.management.ui.tab.item.impl;

import me.kix.uzi.management.ui.tab.focus.Focusable;
import me.kix.uzi.management.ui.tab.item.AbstractItem;
import me.kix.uzi.management.ui.tab.util.TabUtil;
import org.lwjgl.input.Keyboard;

/**
 * An implementation of {@link me.kix.uzi.management.ui.tab.item.Item} that is focusable.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public abstract class FocusItem extends AbstractItem implements Focusable {

    /**
     * Whether or not the item is focused.
     */
    private boolean focused;

    public FocusItem(String label) {
        super(label);
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
