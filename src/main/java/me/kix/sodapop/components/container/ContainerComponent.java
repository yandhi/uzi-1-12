package me.kix.sodapop.components.container;

import me.kix.sodapop.AbstractComponent;
import me.kix.sodapop.Component;
import me.kix.sodapop.components.container.layout.LayoutStrategy;
import me.kix.sodapop.util.MouseButton;
import me.kix.sodapop.util.MouseUtil;
import me.kix.sodapop.util.Rectangle;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.theme.renderer.ComponentRenderer;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The {@link Component} for {@link Container}.
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
     *
     * <p>
     * A linked hash set is used here to maintain insertion order.
     * </p>
     */
    private final Set<Component> components = new LinkedHashSet<>();

    /**
     * Whether or not the container is open.
     */
    private boolean extended;

    public ContainerComponent(String name, GuiManager guiManager, Rectangle renderPosition, LayoutStrategy layout) {
        super(name, guiManager, renderPosition);
        this.layout = layout;
    }

    @Override
    public void drawComponent(ComponentRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.drawComponent(renderer, mouseX, mouseY, partialTicks);

        if (extended) {
            drawComponents(mouseX, mouseY, partialTicks);
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
        layout.layout(this, getGuiManager().getTheme());
    }

    @Override
    public void drawComponents(int mouseX, int mouseY, float partialTicks) {
        components.forEach(component -> component.drawComponent(getGuiManager().getTheme().getFactory().getComponentRenderer(component), mouseX, mouseY, partialTicks));
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
