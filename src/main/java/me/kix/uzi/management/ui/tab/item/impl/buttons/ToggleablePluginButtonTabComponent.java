package me.kix.uzi.management.ui.tab.item.impl.buttons;

import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.theme.renderer.ComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.ui.tab.item.impl.ButtonTabComponent;

/**
 * An implementation of {@link ButtonTabComponent} for {@link me.kix.uzi.api.plugin.toggleable.ToggleablePlugin}.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class ToggleablePluginButtonTabComponent extends ButtonTabComponent {

    /**
     * The plugin that this button handles.
     */
    private final ToggleablePlugin plugin;

    public ToggleablePluginButtonTabComponent(GuiManager guiManager, Rectangle renderPosition, ToggleablePlugin plugin) {
        super(plugin.getLabel(), guiManager, renderPosition);
        this.plugin = plugin;
    }

    @Override
    public void drawComponent(ComponentRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        setState(plugin.isEnabled());
        super.drawComponent(renderer, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void executeAction() {
        plugin.toggle();
    }

    public ToggleablePlugin getPlugin() {
        return plugin;
    }
}
