package me.kix.uzi.management.plugin.internal.toggleable.render.ui.components;

import me.kix.sodapop.AbstractComponent;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.theme.renderer.ComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.Uzi;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A component for the toggleables.
 *
 * @author jackson
 * @since 11/25/2021
 */
public class ToggleablesBlockComponent extends AbstractComponent {

    public ToggleablesBlockComponent(GuiManager guiManager, Rectangle renderPosition) {
        super("Toggleables", guiManager, renderPosition);
    }

    @Override
    public void drawComponent(ComponentRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.drawComponent(renderer, mouseX, mouseY, partialTicks);
    }

    /**
     * @return All of the toggleables to be rendered.
     */
    public List<ToggleablePlugin> getToggleables() {
        return Uzi.INSTANCE.getPluginManager().getContents().stream()
                .filter(ToggleablePlugin.class::isInstance)
                .map(ToggleablePlugin.class::cast)
                .filter(ToggleablePlugin::isEnabled)
                .filter(plugin -> !plugin.isHidden())
                .collect(Collectors.toList());
    }
}
