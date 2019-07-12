package me.kix.uzi.management.click.component.buttons;

import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.sodapop.components.button.buttons.ButtonContainerComponent;
import me.kix.sodapop.components.container.layout.LayoutStrategy;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.util.MouseButton;
import me.kix.sodapop.util.Rectangle;

/**
 * The {@link ButtonContainerComponent} for plugins.
 *
 * @author Kix
 * @since 6/27/2019
 */
public class PluginButtonContainerComponent extends ButtonContainerComponent {

    /**
     * The plugin that this button utilizes.
     */
    private final ToggleablePlugin plugin;

    public PluginButtonContainerComponent(String name, GuiManager guiManager, Rectangle renderPosition, LayoutStrategy layout, ToggleablePlugin plugin) {
        super(name, guiManager, renderPosition, layout);
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
