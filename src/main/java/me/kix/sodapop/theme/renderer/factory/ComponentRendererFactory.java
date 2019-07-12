package me.kix.sodapop.theme.renderer.factory;

import me.kix.sodapop.Component;
import me.kix.sodapop.theme.Theme;
import me.kix.sodapop.theme.renderer.fallback.FallbackComponentRenderer;
import me.kix.sodapop.theme.renderer.ComponentRenderer;

/**
 * The factory that provides the component renderers.
 *
 * <p>
 * Follows the factory design pattern.
 * </p>
 *
 * @author Kix
 * @since 6/27/2019
 */
public class ComponentRendererFactory {

    /**
     * The theme that this factory handles.
     */
    private final Theme theme;

    public ComponentRendererFactory(Theme theme) {
        this.theme = theme;
    }

    /**
     * Provides the component renderer instance for the specified component.
     *
     * <p>
     * This function alone is why this class would be considered a factory.
     * </p>
     *
     * <p>
     * If the component renderer is not found, an empty fallback renderer will be automatically provided.
     * </p>
     *
     * @param component The component getting a component renderer.
     * @return The component renderer instance relative to the component provided.
     */
    public ComponentRenderer getComponentRenderer(Component component) {
        for (ComponentRenderer componentRenderer : theme.getComponentRenderers()) {
            if (componentRenderer.getComponent() == component.getClass()) {
                return componentRenderer;
            }
        }
        return new FallbackComponentRenderer();
    }

    public Theme getTheme() {
        return theme;
    }
}
