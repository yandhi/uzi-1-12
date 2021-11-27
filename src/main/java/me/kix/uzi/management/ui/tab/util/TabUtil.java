package me.kix.uzi.management.ui.tab.util;

import me.kix.uzi.api.util.render.font.NahrFont;
import me.kix.uzi.management.ui.tab.focus.Focusable;
import me.kix.uzi.management.ui.tab.folder.Folder;
import me.kix.uzi.management.ui.tab.item.Item;
import me.kix.uzi.management.ui.tab.item.TabComponent;
import me.kix.uzi.management.ui.tab.item.impl.ButtonTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.FolderTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.focus.SliderTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.focus.SpinnerTabComponent;
import me.kix.uzi.management.ui.tab.item.impl.folders.ToggleablePluginFolderTabComponent;
import net.minecraft.client.Minecraft;

/**
 * A util for the tab-gui.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public enum TabUtil {
    INSTANCE;

    /**
     * The font renderer for the UI.
     */
    private final NahrFont consolas = new NahrFont("Consolas", 16);

    /**
     * Handles the issue of "oh what if a folder has an open folder inside of it."
     *
     * @param folder The folder being checked.
     * @return Whether or not that folder contains an open folder.
     */
    public boolean hasFocusedItem(Folder folder) {
        for (TabComponent component : folder.getContents()) {
            return isFocused(component);
        }
        return false;
    }

    /**
     * Tells whether or not the component is a focused component.
     *
     * @param component The component being checked.
     * @return Whether or not that component is focused.
     */
    public boolean isFocused(TabComponent component) {
        if (component instanceof FolderTabComponent) {
            FolderTabComponent folderItem = (FolderTabComponent) component;
            return folderItem.isOpen();
        } else if (component instanceof Focusable) {
            Focusable focusable = (Focusable) component;
            return focusable.isFocused();
        }
        return false;
    }

    /**
     * Determines the max width necessary for the components in the folder.
     *
     * @param folder The folder being searched through.
     * @return The max length of the component name plus some padding.
     */
    public int determineMaxWidth(Folder folder) {
        float highestWidth = 0;
        for (TabComponent component : folder.getContents()) {
            float width = consolas.getStringWidth(component.getName());

            if (component instanceof ToggleablePluginFolderTabComponent) {
                width = width + 10;
            }

            if (component instanceof ButtonTabComponent) {
                width = width + 10;
            }

            if (component instanceof SliderTabComponent) {
                SliderTabComponent sliderItem = (SliderTabComponent) component;

                width = consolas.getStringWidth(sliderItem.getRaw()) + 10;
            }

            if (component instanceof SpinnerTabComponent) {
                SpinnerTabComponent spinnerItem = (SpinnerTabComponent) component;

                width = consolas.getStringWidth(spinnerItem.getRaw());
            }

            if (width > highestWidth) {
                highestWidth = width;
            }
        }
        return (int) highestWidth + 4;
    }
}
