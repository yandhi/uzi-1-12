package me.kix.sodapop.components.container;

import me.kix.sodapop.Component;
import me.kix.sodapop.components.container.layout.LayoutStrategy;

import java.util.Set;

/**
 * An instance of {@link Component} that can hold other components.
 *
 * @author Kix
 * @since 6/27/2019
 */
public interface Container {

    /**
     * Draws the child components.
     */
    void drawComponents(int mouseX, int mouseY, float partialTicks);

    /**
     * @return The components that this container holds.
     */
    Set<Component> getComponents();

    /**
     * @return The strategy for placing components.
     */
    LayoutStrategy getLayout();
}
