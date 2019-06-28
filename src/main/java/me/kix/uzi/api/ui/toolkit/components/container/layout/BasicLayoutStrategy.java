package me.kix.uzi.api.ui.toolkit.components.container.layout;

import me.kix.uzi.api.ui.toolkit.Component;
import me.kix.uzi.api.ui.toolkit.components.container.ContainerComponent;
import me.kix.uzi.api.ui.toolkit.theme.Theme;

/**
 * The basic form of {@link LayoutStrategy}.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class BasicLayoutStrategy implements LayoutStrategy {

    @Override
    public void layout(ContainerComponent container, Theme theme) {
        int componentY = container.getRenderPosition().getY() + container.getRenderPosition().getHeight() + theme.getVerticalPadding();
        for (Component component : container.getComponents()) {
            component.updateDimensions(theme.getWidth() - (theme.getHorizontalPadding() * 2), theme.getComponentHeight());
            component.updatePosition(container.getRenderPosition().getX() + theme.getHorizontalPadding(), componentY);
            componentY += component.getFunctionalPosition().getHeight() + theme.getVerticalPadding();
        }
        container.getFunctionalPosition().setHeight((theme.getComponentHeight() + theme.getVerticalPadding()) * container.getComponents().size() + theme.getVerticalPadding());
    }
}
