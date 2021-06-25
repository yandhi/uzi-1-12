package me.kix.uzi.management.ui.tab;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.EnumProperty;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.management.ui.tab.item.impl.FolderItem;
import me.kix.uzi.management.ui.tab.item.impl.buttons.PropertyButtonItem;
import me.kix.uzi.management.ui.tab.item.impl.buttons.ToggleablePluginButtonItem;
import me.kix.uzi.management.ui.tab.item.impl.focus.SliderItem;
import me.kix.uzi.management.ui.tab.item.impl.focus.SpinnerItem;
import me.kix.uzi.management.ui.tab.item.impl.folders.ToggleablePluginFolderItem;
import org.apache.commons.lang3.text.WordUtils;

/**
 * The root file for handling of the tabgui.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public enum TabGui {
    INSTANCE;

    /**
     * The main tab for the ui.
     */
    private final FolderItem mainTab = new FolderItem(">");

    /**
     * Sets up the tab-gui.
     */
    public void setup() {
        for (Category category : Category.values()) {
            FolderItem categoryItem = new FolderItem(WordUtils.capitalizeFully(category.name()));

            for (Plugin plugin : Uzi.INSTANCE.getPluginManager().getContents()) {
                if (plugin instanceof ToggleablePlugin) {
                    ToggleablePlugin toggleablePlugin = (ToggleablePlugin) plugin;
                    if (toggleablePlugin.getCategory() == category) {
                        if (toggleablePlugin.getProperties().isEmpty()) {
                            categoryItem.getContents().add(new ToggleablePluginButtonItem(toggleablePlugin));
                        } else {
                            ToggleablePluginFolderItem toggleablePluginFolderItem = new ToggleablePluginFolderItem(toggleablePlugin);

                            for (Property property : toggleablePlugin.getProperties()) {
                                if (property.getValue() instanceof Boolean) {
                                    toggleablePluginFolderItem.getContents().add(new PropertyButtonItem(property));
                                }
                                if (property instanceof NumberProperty) {
                                    toggleablePluginFolderItem.getContents().add(new SliderItem((NumberProperty) property));
                                }
                                if (property instanceof EnumProperty) {
                                    toggleablePluginFolderItem.getContents().add(new SpinnerItem((EnumProperty) property));
                                }
                            }
                            categoryItem.getContents().add(toggleablePluginFolderItem);
                        }
                    }
                }
            }
            mainTab.getContents().add(categoryItem);
        }
        mainTab.setHovered(true);
    }

    /**
     * Draws the tab-gui at the given position.
     *
     * @param x          The x position of the ui.
     * @param y          The y position of the ui.
     * @param width      The width of the initial tab.
     * @param height     The height of each item in the ui.
     * @param foreground The foreground color for the ui.
     * @param background The background color for the ui.
     */
    public void draw(int x, int y, int width, int height, int foreground, int background) {
        mainTab.draw(x, y, width, height, foreground, background);
    }

    /**
     * Handles the keys for the tab-gui.
     *
     * @param keyCode the key currently pressed.
     */
    public void handleKeys(int keyCode) {
        mainTab.handleKeys(keyCode);
    }
}