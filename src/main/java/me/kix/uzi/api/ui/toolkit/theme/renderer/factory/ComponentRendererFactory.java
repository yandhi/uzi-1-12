package me.kix.uzi.api.ui.toolkit.theme.renderer.factory;

import me.kix.uzi.api.ui.toolkit.Component;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.theme.renderer.ComponentRenderer;
import me.kix.uzi.api.ui.toolkit.theme.renderer.fallback.FallbackComponentRenderer;

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
