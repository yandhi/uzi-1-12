package me.kix.sodapop.components.button.buttons;

import me.kix.sodapop.components.container.ContainerComponent;
import me.kix.sodapop.components.container.layout.LayoutStrategy;
import me.kix.sodapop.util.MouseButton;
import me.kix.sodapop.util.MouseUtil;
import me.kix.sodapop.util.Rectangle;
import me.kix.sodapop.components.button.Button;
import me.kix.sodapop.manage.GuiManager;

/**
 * A button that also acts as a container with child components.
 *
 * @author Kix
 * @since 6/27/2019
 */
public abstract class ButtonContainerComponent extends ContainerComponent implements Button {

    public ButtonContainerComponent(String name, GuiManager guiManager, Rectangle renderPosition, LayoutStrategy layout) {
        super(name, guiManager, renderPosition, layout);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, MouseButton mouseButton) {
        super.mousePressed(mouseX, mouseY, mouseButton);

        if (MouseUtil.mouseWithinRectangle(mouseX, mouseY, getRenderPosition())) {
            onMousePress(mouseButton);
        }
    }
}
