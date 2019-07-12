package me.kix.sodapop.components.container.layout;

import me.kix.sodapop.Component;
import me.kix.sodapop.theme.Theme;
import me.kix.sodapop.components.container.ContainerComponent;
import me.kix.sodapop.components.frame.FrameContainerComponent;

/**
 * The basic form of {@link LayoutStrategy}.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class BasicLayoutStrategy implements LayoutStrategy {

    @Override
    public void layout(ContainerComponent container, Theme theme) {
        if (!container.getComponents().isEmpty()) {

            int componentY = container.getRenderPosition().getHeight() + theme.getVerticalPadding();
            if (container instanceof FrameContainerComponent) {
                componentY -= ((FrameContainerComponent) container).getScrollY();
            }
            for (Component component : container.getComponents()) {
                if (component instanceof ContainerComponent) {
                    if (!((ContainerComponent) component).isExtended()) {
                        component.updateDimensions(container.getRenderPosition().getWidth() - (theme.getHorizontalPadding() * 2), theme.getComponentHeight());
                    }
                } else {
                    component.updateDimensions(container.getRenderPosition().getWidth() - (theme.getHorizontalPadding() * 2), theme.getComponentHeight());
                }
                component.updatePosition(container.getRenderPosition().getX() + theme.getHorizontalPadding(), container.getRenderPosition().getY() + componentY);

                if (component instanceof ContainerComponent) {
                    if (!((ContainerComponent) component).isExtended() || ((ContainerComponent) component).getComponents().isEmpty()) {
                        componentY += component.getFunctionalPosition().getHeight() + theme.getVerticalPadding();
                    } else {
                        componentY += component.getFunctionalPosition().getHeight();
                    }
                } else {
                    componentY += component.getFunctionalPosition().getHeight() + theme.getVerticalPadding();
                }
            }
            container.getFunctionalPosition().setHeight(componentY);
        }
    }
}
