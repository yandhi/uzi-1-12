package me.kix.uzi.management.ui.tab.folder;

import me.kix.uzi.management.ui.tab.item.Item;

import java.util.List;

/**
 * Any class that is able to hold a collection of {@link me.kix.uzi.management.ui.tab.item.Item}
 *
 * @author yandhi
 * @since 6/24/2021
 */
public interface Folder {

    /**
     * @return The contents of the folder.
     */
    List<Item> getContents();

    /**
     * @return Whether or not the folder is open.
     */
    boolean isOpen();

    /**
     * @return The item that is currently selected.
     */
    Item getSelectedItem();
}
