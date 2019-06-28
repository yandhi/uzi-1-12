package me.kix.uzi.api.ui.toolkit.components.button.buttons;

import me.kix.uzi.api.ui.toolkit.AbstractComponent;
import me.kix.uzi.api.ui.toolkit.components.button.Button;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.util.MouseButton;
import me.kix.uzi.api.ui.toolkit.util.MouseUtil;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;

/**
 * A basic button with no child components.
 *
 * @author Kix
 * @since 6/27/2019
 */
public abstract class ButtonComponent extends AbstractComponent implements Button {

    public ButtonComponent(String name, Theme theme, Rectangle renderPosition) {
        super(name, theme, renderPosition);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, MouseButton mouseButton) {
        super.mousePressed(mouseX, mouseY, mouseButton);

        if (MouseUtil.mouseWithinRectangle(mouseX, mouseY, getRenderPosition())) {
            onMousePress(mouseButton);
        }
    }
}
