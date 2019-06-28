package me.kix.uzi.api.ui.toolkit.theme;

import me.kix.uzi.api.ui.toolkit.theme.renderer.ComponentRenderer;
import me.kix.uzi.api.ui.toolkit.theme.renderer.factory.ComponentRendererFactory;

import java.util.Set;

/**
 * An interface that describes a "mode" for the ui.
 *
 * <p>
 * This allows the gui to easily be changed on the fly.
 * </p>
 *
 * @author Kix
 * @since 6/27/2019
 */
public interface Theme {

    /**
     * Initializes the theme.
     */
    void initTheme();

    /**
     * @return The collection of component renderers that the themes contains.
     */
    Set<ComponentRenderer> getComponentRenderers();

    /**
     * @return The instance of the themes's component rendering factory.
     */
    ComponentRendererFactory getFactory();

    /**
     * @return The name of the themes.
     */
    String getName();

    /**
     * @return The amount of vertical padding between components.
     */
    int getVerticalPadding();

    /**
     * @return The amount of horizontal padding between components.
     */
    int getHorizontalPadding();

    /**
     * @return The width of the frame component.
     */
    int getWidth();

    /**
     * @return The height of the frame component.
     */
    int getHeight();

    /**
     * @return The height of the components.
     */
    int getComponentHeight();
}
