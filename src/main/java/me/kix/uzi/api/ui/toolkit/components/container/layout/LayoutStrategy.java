package me.kix.uzi.api.ui.toolkit.components.container.layout;

import me.kix.uzi.api.ui.toolkit.components.container.ContainerComponent;
import me.kix.uzi.api.ui.toolkit.theme.Theme;

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
