package me.kix.uzi.api.ui.toolkit.components.container;

import me.kix.uzi.api.ui.toolkit.Component;
import me.kix.uzi.api.ui.toolkit.components.container.layout.LayoutStrategy;

import java.util.Set;

/**
 * An instance of {@link Component} that can hold other components.
 *
 * @author Kix
 * @since 6/27/2019
 */
public interface Container {

    /**
     * @return The components that this container holds.
     */
    Set<Component> getComponents();

    /**
     * @return The strategy for placing components.
     */
    LayoutStrategy getLayout();
}
