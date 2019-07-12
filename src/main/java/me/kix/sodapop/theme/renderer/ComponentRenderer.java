package me.kix.sodapop.theme.renderer;

import me.kix.sodapop.Component;

/**
 * A class that renders a component.
 *
 * @author Kix
 * @since 6/26/2019
 */
public interface ComponentRenderer<C extends Component> {

    /**
     * Renders the component.
     *
     * @param component The component to be rendered.
     */
    void renderComponent(C component);

    /**
     * Tells whether or not the class can renderer the determined component.
     *
     * @param component The component being questioned to renderer.
     * @return Whether or not said component can be rendered by this renderer.
     */
    boolean canRender(Component component);

    /**
     * @return The class of the component that this renderer handles.
     */
    Class<C> getComponent();
}
