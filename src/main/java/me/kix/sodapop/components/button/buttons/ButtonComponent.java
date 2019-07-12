package me.kix.sodapop.components.button.buttons;

import me.kix.sodapop.AbstractComponent;
import me.kix.sodapop.util.MouseButton;
import me.kix.sodapop.util.MouseUtil;
import me.kix.sodapop.util.Rectangle;
import me.kix.sodapop.components.button.Button;
import me.kix.sodapop.manage.GuiManager;

/**
 * A basic button with no child components.
 *
 * @author Kix
 * @since 6/27/2019
 */
public abstract class ButtonComponent extends AbstractComponent implements Button {

    public ButtonComponent(String name, GuiManager guiManager, Rectangle renderPosition) {
        super(name, guiManager, renderPosition);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        super.updateComponent(mouseX, mouseY);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, MouseButton mouseButton) {
        super.mousePressed(mouseX, mouseY, mouseButton);

        if (MouseUtil.mouseWithinRectangle(mouseX, mouseY, getRenderPosition())) {
            onMousePress(mouseButton);
        }
    }
}
