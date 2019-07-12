package me.kix.sodapop.theme;

import me.kix.sodapop.theme.renderer.ComponentRenderer;
import me.kix.sodapop.theme.renderer.factory.ComponentRendererFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * The abstracted form of {@link Theme}.
 *
 * @author Kix
 * @since 6/27/2019
 */
public abstract class AbstractTheme implements Theme {

    /**
     * The name of the theme.
     */
    private final String name;

    /**
     * The dimensions of the frame.
     */
    private final int width, height;

    /**
     * The height of components in the menu.
     */
    private final int componentHeight;

    /**
     * The amount of padding between components.
     */
    private final int verticalPadding, horizontalPadding;

    /**
     * The collection of renderers for the theme.
     */
    private final Set<ComponentRenderer> componentRenderers = new HashSet<>();

    /**
     * The factory for component rendering.
     */
    private final ComponentRendererFactory factory;

    public AbstractTheme(String name, int width, int height, int componentHeight, int verticalPadding, int horizontalPadding) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.componentHeight = componentHeight;
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;

        initTheme();
        factory = new ComponentRendererFactory(this);
    }

    @Override
    public Set<ComponentRenderer> getComponentRenderers() {
        return componentRenderers;
    }

    @Override
    public ComponentRendererFactory getFactory() {
        return factory;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getComponentHeight() {
        return componentHeight;
    }

    @Override
    public int getVerticalPadding() {
        return verticalPadding;
    }

    @Override
    public int getHorizontalPadding() {
        return horizontalPadding;
    }
}
