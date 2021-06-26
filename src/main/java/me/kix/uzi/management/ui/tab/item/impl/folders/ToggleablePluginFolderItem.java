package me.kix.uzi.management.ui.tab.item.impl.folders;

import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.ui.tab.item.impl.FolderItem;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * An implementation of {@link me.kix.uzi.management.ui.tab.item.impl.FolderItem} for plugins.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class ToggleablePluginFolderItem extends FolderItem {

    /**
     * The plugin that this item handles.
     */
    private final ToggleablePlugin plugin;

    public ToggleablePluginFolderItem(ToggleablePlugin plugin) {
        super(plugin.getLabel());
        this.plugin = plugin;
    }

    @Override
    public void draw(int x, int y, int width, int height, int foreground, int background) {
        if (plugin.isEnabled()) {
            RenderUtil.drawRect(x + width - 5, y, x + width, y + height, new Color(foreground).darker().getRGB());
        }
        super.draw(x, y, width, height, foreground, background);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(">", x + width - Minecraft.getMinecraft().fontRenderer.getStringWidth(">") - 5, y + ((height - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) / 2f), 0xFFDEDEDE);
    }

    @Override
    public void handleKeys(int keyCode) {
        if (!isOpen() && keyCode == Keyboard.KEY_RETURN) {
            plugin.toggle();
        }

        super.handleKeys(keyCode);
    }
}
