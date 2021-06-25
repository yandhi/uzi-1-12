package me.kix.uzi.management.ui.tab.item.impl.buttons;

import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.ui.tab.item.impl.ButtonItem;

/**
 * An implementation of {@link me.kix.uzi.management.ui.tab.item.impl.ButtonItem} for {@link me.kix.uzi.api.plugin.toggleable.ToggleablePlugin}.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class ToggleablePluginButtonItem extends ButtonItem {

    /**
     * The plugin that this button handles.
     */
    private final ToggleablePlugin plugin;

    public ToggleablePluginButtonItem(ToggleablePlugin plugin) {
        super(plugin.getLabel());
        this.plugin = plugin;
    }

    @Override
    public void draw(int x, int y, int width, int height, int foreground, int background) {
        setState(plugin.isEnabled());
        super.draw(x, y, width, height, foreground, background);
    }

    @Override
    protected void executeAction() {
        plugin.toggle();
    }
}
