package me.kix.uzi.management.click.component.buttons;

import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.ui.toolkit.components.button.buttons.ButtonContainerComponent;
import me.kix.uzi.api.ui.toolkit.components.container.layout.LayoutStrategy;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.util.MouseButton;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;

/**
 * The {@link me.kix.uzi.api.ui.toolkit.components.button.buttons.ButtonContainerComponent} for plugins.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class PluginButtonContainerComponent extends ButtonContainerComponent {

    /**
     * The plugin that this button utilizes.
     */
    private final ToggleablePlugin plugin;

    public PluginButtonContainerComponent(String name, Theme theme, Rectangle renderPosition, LayoutStrategy layout, ToggleablePlugin plugin) {
        super(name, theme, renderPosition, layout);
        this.plugin = plugin;
    }

    @Override
    public void onMousePress(MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            plugin.toggle();
        }
    }

    public ToggleablePlugin getPlugin() {
        return plugin;
    }
}
