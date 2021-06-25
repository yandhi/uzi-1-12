package me.kix.uzi.management.ui.tab.item;

/**
 * The abstract form of {@link Item}.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public abstract class AbstractItem implements Item {

    /**
     * The name of the item.
     */
    private final String label;

    /**
     * Whether or not the item is hovered.
     */
    private boolean hovered;

    public AbstractItem(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean isHovered() {
        return hovered;
    }

    @Override
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }
}
