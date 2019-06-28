package me.kix.uzi.api.ui.toolkit.components.frame;

import me.kix.uzi.api.ui.toolkit.components.container.ContainerComponent;
import me.kix.uzi.api.ui.toolkit.components.container.layout.LayoutStrategy;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.util.MouseButton;
import me.kix.uzi.api.ui.toolkit.util.MouseUtil;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;

/**
 * A "window"-like container component.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class FrameContainerComponent extends ContainerComponent {

    /**
     * Whether or not the frame is currently dragging.
     */
    private boolean dragging;

    /**
     * The position that the x and y is dropped at.
     */
    private int droppedX, droppedY;

    public FrameContainerComponent(String name, Theme theme, Rectangle renderPosition, LayoutStrategy layout) {
        super(name, theme, renderPosition, layout);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        if (dragging) {
            updatePosition(mouseX + droppedX, mouseY + droppedY);
        }
        super.updateComponent(mouseX, mouseY);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, MouseButton mouseButton) {
        if (MouseUtil.mouseWithinRectangle(mouseX, mouseY, getRenderPosition())) {
            if (mouseButton == MouseButton.LEFT) {
                this.dragging = true;
                this.droppedX = getRenderPosition().getX() - mouseX;
                this.droppedY = getRenderPosition().getY() - mouseY;
            }
        }
        super.mousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, MouseButton mouseButton) {
        this.dragging = false;
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    public boolean isDragging() {
        return dragging;
    }
}
