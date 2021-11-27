package me.kix.uzi.management.ui.tab.item.impl;

import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.theme.renderer.ComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.management.ui.tab.folder.Folder;
import me.kix.uzi.management.ui.tab.item.TabComponent;
import me.kix.uzi.management.ui.tab.util.TabUtil;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link TabComponent} that is also an {@link me.kix.uzi.management.ui.tab.folder.Folder}.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class FolderTabComponent extends TabComponent implements Folder {

    /**
     * The contents of the item.
     *
     * <p>
     * The child items.
     * </p>
     */
    private final List<TabComponent> contents = new ArrayList<>();

    /**
     * Whether or not the folder is open.
     */
    private boolean open;

    /**
     * The item currently selected.
     */
    private TabComponent selectedItem;

    /**
     * The current index.
     */
    private int index;

    public FolderTabComponent(String name, GuiManager guiManager, Rectangle renderPosition) {
        super(name, guiManager, renderPosition);
    }

    @Override
    public void drawComponent(ComponentRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.drawComponent(renderer, mouseX, mouseY, partialTicks);

        if(!open) {
            getContents().forEach(item -> item.setHovered(false));
        }

        if (open) {
            selectedItem = contents.get(index);
            int childY = getRenderPosition().getY();

            for (TabComponent component : contents) {
                component.setHovered(component == selectedItem);
                component.getRenderPosition().setX(getRenderPosition().getX() + getRenderPosition().getWidth() + 2);
                component.getRenderPosition().setY(childY);
                component.getRenderPosition().setWidth(TabUtil.INSTANCE.determineMaxWidth(this));
                component.getRenderPosition().setHeight(getRenderPosition().getHeight());

                component.drawComponent(getGuiManager().getTheme().getFactory().getComponentRenderer(component), mouseX, mouseY, partialTicks);

                childY += getRenderPosition().getHeight();
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
                if (selectedItem != null) {
                    selectedItem.handleKeys(keyCode);
                }
            }
        }
    }

    @Override
    public List<TabComponent> getContents() {
        return contents;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public TabComponent getSelectedComponent() {
        return selectedItem;
    }
}
