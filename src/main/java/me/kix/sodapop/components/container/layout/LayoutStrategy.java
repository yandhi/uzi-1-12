package me.kix.sodapop.components.container.layout;

import me.kix.sodapop.theme.Theme;
import me.kix.sodapop.components.container.ContainerComponent;

/**
 * Handles the strategy for laying out components.
 *
 * @author Kix
 * @since 6/27/2019
 */
public interface LayoutStrategy {

    /**
     * Lays out a container's component based on the given themes.
     *
     * @param container The container being layed out.
     * @param theme     The themes being layed out.
     */
    void layout(ContainerComponent container, Theme theme);
}
