package me.kix.uzi.management.ui.tab.util;

import me.kix.uzi.management.ui.tab.focus.Focusable;
import me.kix.uzi.management.ui.tab.folder.Folder;
import me.kix.uzi.management.ui.tab.item.Item;
import me.kix.uzi.management.ui.tab.item.impl.ButtonItem;
import me.kix.uzi.management.ui.tab.item.impl.FolderItem;
import me.kix.uzi.management.ui.tab.item.impl.focus.SliderItem;
import me.kix.uzi.management.ui.tab.item.impl.focus.SpinnerItem;
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
     * Handles the issue of "oh what if a folder has an open folder inside of it."
     *
     * @param folder The folder being checked.
     * @return Whether or not that folder contains an open folder.
     */
    public boolean hasFocusedItem(Folder folder) {
        for (Item item : folder.getContents()) {
            return isFocused(item);
        }
        return false;
    }

    /**
     * Tells whether or not the item is a focused item.
     *
     * @param item The item being checked.
     * @return Whether or not that item is focused.
     */
    public boolean isFocused(Item item) {
        if (item instanceof FolderItem) {
            FolderItem folderItem = (FolderItem) item;
            return folderItem.isOpen();
        } else if (item instanceof Focusable) {
            Focusable focusable = (Focusable) item;
            return focusable.isFocused();
        }
        return false;
    }

    /**
     * Determines the max width necessary for the items in the folder.
     *
     * @param folder The folder being searched through.
     * @return The max length of the item name plus some padding.
     */
    public int determineMaxWidth(Folder folder) {
        int highestWidth = 0;
        for (Item item : folder.getContents()) {
            int width = Minecraft.getMinecraft().fontRenderer.getStringWidth(item.getLabel());

            if (item instanceof ButtonItem) {
                width = width + 10;
            }

            if (item instanceof SliderItem) {
                SliderItem sliderItem = (SliderItem) item;

                width = Minecraft.getMinecraft().fontRenderer.getStringWidth(sliderItem.getRaw()) + 10;
            }

            if (item instanceof SpinnerItem) {
                SpinnerItem spinnerItem = (SpinnerItem) item;

                width = Minecraft.getMinecraft().fontRenderer.getStringWidth(spinnerItem.getRaw());
            }

            if (width > highestWidth) {
                highestWidth = width;
            }
        }
        return highestWidth + 6;
    }
}
