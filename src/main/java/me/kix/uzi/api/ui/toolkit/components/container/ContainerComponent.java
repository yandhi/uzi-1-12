package me.kix.uzi.api.ui.toolkit.components.container;

import me.kix.uzi.api.ui.toolkit.AbstractComponent;
import me.kix.uzi.api.ui.toolkit.Component;
import me.kix.uzi.api.ui.toolkit.components.container.layout.LayoutStrategy;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.theme.renderer.ComponentRenderer;
import me.kix.uzi.api.ui.toolkit.util.MouseButton;
import me.kix.uzi.api.ui.toolkit.util.MouseUtil;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@link me.kix.uzi.api.ui.toolkit.Component} for {@link Container}.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class ContainerComponent extends AbstractComponent implements Container {

    /**
     * The strategy for laying out components.
     */
    private final LayoutStrategy layout;

    /**
     * The collection of components for the container.
     */
    private final Set<Component> components = new HashSet<>();

    /**
     * Whether or not the container is open.
     */
    private boolean extended;

    public ContainerComponent(String name, Theme theme, Rectangle renderPosition, LayoutStrategy layout) {
        super(name, theme, renderPosition);
        this.layout = layout;
    }

    @Override
    public void drawComponent(ComponentRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.drawComponent(renderer, mouseX, mouseY, partialTicks);

        if(extended) {
            components.forEach(component -> component.drawComponent(getTheme().getFactory().getComponentRenderer(component), mouseX, mouseY, partialTicks));
        }
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, MouseButton mouseButton) {
        super.mousePressed(mouseX, mouseY, mouseButton);

        if (MouseUtil.mouseWithinRectangle(mouseX, mouseY, getRenderPosition())) {
            if (mouseButton == MouseButton.RIGHT) {
                this.extended = !extended;
            }
        }

        if (extended) {
            components.forEach(component -> component.mousePressed(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, MouseButton mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);

        if (extended) {
            components.forEach(component -> component.mouseReleased(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        super.updateComponent(mouseX, mouseY);
        layout.layout(this, getTheme());
    }

    @Override
    public Set<Component> getComponents() {
        return components;
    }

    @Override
    public LayoutStrategy getLayout() {
        return layout;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }
}
