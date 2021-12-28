package me.kix.uzi.management.ui.tab;

import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.theme.Theme;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.Uzi;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.EnumProperty;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.management.click.GuiClick;
import me.kix.uzi.management.click.themes.UziTheme;
import me.kix.uzi.management.ui.tab.item.impl.FolderTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.buttons.PropertyButtonTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.buttons.ToggleablePluginButtonTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.focus.SliderTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.focus.SpinnerTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.folders.ToggleablePluginFolderTabComponent;
import org.apache.commons.lang3.text.WordUtils;

/**
 * The root file for handling of the tabgui.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public enum TabGui implements GuiManager {
    INSTANCE;

    /**
     * The main tab for the ui.
     */
    private final FolderTabComponent mainTab = new FolderTabComponent(">", this, new Rectangle(2, 12, 10, 10));

    /**
     * The theme for the tabgui.
     */
    private Theme theme = new UziTheme();

    /**
     * Sets up the tab-gui.
     */
    public void setup() {
        for (Category category : Category.values()) {
            FolderTabComponent categoryItem = new FolderTabComponent(WordUtils.capitalizeFully(category.name()), this, new Rectangle(0,0,0,0));

            for (Plugin plugin : Uzi.INSTANCE.getPluginManager().getContents()) {
                if (plugin instanceof ToggleablePlugin) {
                    ToggleablePlugin toggleablePlugin = (ToggleablePlugin) plugin;
                    if (toggleablePlugin.getCategory() == category) {
                        if (toggleablePlugin.getProperties().isEmpty()) {
                            categoryItem.getContents().add(new ToggleablePluginButtonTabComponent(this, new Rectangle(0,0,0,0),toggleablePlugin));
                        } else {
                            ToggleablePluginFolderTabComponent toggleablePluginFolderTabComponent = new ToggleablePluginFolderTabComponent(this, new Rectangle(0,0,0,0), toggleablePlugin);

                            for (Property property : toggleablePlugin.getProperties()) {
                                if (property.getValue() instanceof Boolean) {
                                    toggleablePluginFolderTabComponent.getContents().add(new PropertyButtonTabComponent(this, new Rectangle(0,0,0,0), property));
                                }
                                if (property instanceof NumberProperty) {
                                    toggleablePluginFolderTabComponent.getContents().add(new SliderTabComponent(this, new Rectangle(0,0,0,0), (NumberProperty) property));
                                }
                                if (property instanceof EnumProperty) {
                                    toggleablePluginFolderTabComponent.getContents().add(new SpinnerTabComponent(this, new Rectangle(0,0,0,0), (EnumProperty) property));
                                }
                            }
                            categoryItem.getContents().add(toggleablePluginFolderTabComponent);
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
     */
    public void draw() {
        theme = GuiClick.getScreen().getTheme();
        mainTab.drawComponent(theme.getFactory().getComponentRenderer(mainTab), 0, 0, 0);
    }

    /**
     * Handles the keys for the tab-gui.
     *
     * @param keyCode the key currently pressed.
     */
    public void handleKeys(int keyCode) {
        mainTab.handleKeys(keyCode);
    }

    @Override
    public Theme getTheme() {
        return theme;
    }
}