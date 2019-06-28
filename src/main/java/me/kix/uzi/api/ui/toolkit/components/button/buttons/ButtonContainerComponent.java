package me.kix.uzi.api.ui.toolkit.components.button.buttons;

import me.kix.uzi.api.ui.toolkit.components.button.Button;
import me.kix.uzi.api.ui.toolkit.components.container.ContainerComponent;
import me.kix.uzi.api.ui.toolkit.components.container.layout.LayoutStrategy;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.util.MouseButton;
import me.kix.uzi.api.ui.toolkit.util.MouseUtil;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;

/**
 * A button that also acts as a container with child components.
 *
 * @author Kix
 * @since 6/27/2019
 */
public abstract class ButtonContainerComponent extends ContainerComponent implements Button {

    public ButtonContainerComponent(String name, Theme theme, Rectangle renderPosition, LayoutStrategy layout) {
        super(name, theme, renderPosition, layout);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, MouseButton mouseButton) {
        super.mousePressed(mouseX, mouseY, mouseButton);

        if (MouseUtil.mouseWithinRectangle(mouseX, mouseY, getRenderPosition())) {
            onMousePress(mouseButton);
        }
    }
}
