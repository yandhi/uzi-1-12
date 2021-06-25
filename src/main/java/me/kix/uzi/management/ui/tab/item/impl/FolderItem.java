package me.kix.uzi.management.ui.tab.item.impl;

import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.ui.tab.folder.Folder;
import me.kix.uzi.management.ui.tab.item.AbstractItem;
import me.kix.uzi.management.ui.tab.item.Item;
import me.kix.uzi.management.ui.tab.util.TabUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link me.kix.uzi.management.ui.tab.item.AbstractItem} that is also an {@link me.kix.uzi.management.ui.tab.folder.Folder}.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class FolderItem extends AbstractItem implements Folder {

    /**
     * The contents of the item.
     *
     * <p>
     * The child items.
     * </p>
     */
    private final List<Item> contents = new ArrayList<>();

    /**
     * Whether or not the folder is open.
     */
    private boolean open;

    /**
     * The item currently selected.
     */
    private Item selectedItem;

    /**
     * The current index.
     */
    private int index;

    public FolderItem(String label) {
        super(label);
    }

    @Override
    public void draw(int x, int y, int width, int height, int foreground, int background) {
        RenderUtil.drawRect(x, y, x + width, y + height, isHovered() ? foreground : background);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(getLabel(), x + 2, y + ((height - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) / 2f), 0xFFFFFFFF);

        if (!open) {
            getContents().forEach(item -> item.setHovered(false));
        }

        if (open) {
            selectedItem = contents.get(index);
            int childY = y;

            for (Item item : contents) {
                item.setHovered(item == selectedItem);
                item.draw(x + width + 2, childY, TabUtil.INSTANCE.determineMaxWidth(this), height, foreground, background);

                childY += height;
            }
        }
    }

    @Override
    public void handleKeys(int keyCode) {
        if (isHovered()) {
            if (keyCode == Keyboard.KEY_RIGHT) {
                if (!open) {
                    open = true;
                }
            } else if (keyCode == Keyboard.KEY_LEFT) {
                if (open && !TabUtil.INSTANCE.hasFocusedItem(this) && !TabUtil.INSTANCE.isFocused(selectedItem)) {
                    open = false;
                }
            }

            if (isOpen()) {
                if (!TabUtil.INSTANCE.isFocused(selectedItem)) {
                    if (keyCode == Keyboard.KEY_DOWN) {
                        if (index < getContents().size() - 1) {
                            index++;
                        } else {
                            index = 0;
                        }
                    }

                    if (keyCode == Keyboard.KEY_UP) {
                        if (index > 0) {
                            index--;
                        } else {
                            index = getContents().size() - 1;
                        }
                    }
                }
                selectedItem.handleKeys(keyCode);
            }
        }
    }

    @Override
    public List<Item> getContents() {
        return contents;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public Item getSelectedItem() {
        return selectedItem;
    }
}
