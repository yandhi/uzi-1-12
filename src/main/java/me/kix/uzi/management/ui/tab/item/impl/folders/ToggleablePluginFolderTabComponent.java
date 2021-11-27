package me.kix.uzi.management.ui.tab.item.impl.folders;

import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.ui.tab.item.impl.FolderTabComponent;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * An implementation of {@link FolderTabComponent} for plugins.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class ToggleablePluginFolderTabComponent extends FolderTabComponent {

    /**
     * The plugin that this item handles.
     */
    private final ToggleablePlugin plugin;

    public ToggleablePluginFolderTabComponent(GuiManager guiManager, Rectangle renderPosition, ToggleablePlugin plugin) {
        super(plugin.getLabel(), guiManager, renderPosition);
        this.plugin = plugin;
    }

    @Override
    public void handleKeys(int keyCode) {
        if (!isOpen() && keyCode == Keyboard.KEY_RETURN) {
            plugin.toggle();
        }

        super.handleKeys(keyCode);
    }
}
