package me.kix.uzi.management.ui.tab.item.impl.focus;

import me.kix.uzi.api.property.properties.EnumProperty;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.ui.tab.item.impl.FocusItem;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

/**
 * An implementation of {@link me.kix.uzi.management.ui.tab.item.Item} that is a spinner.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class SpinnerItem extends FocusItem {

    /**
     * The property that this item handles.
     */
    private final EnumProperty property;

    public SpinnerItem(EnumProperty property) {
        super(property.getLabel());
        this.property = property;
    }

    @Override
    public void draw(int x, int y, int width, int height, int foreground, int background) {
        RenderUtil.drawRect(x, y, x + width, y + height, isHovered() ? foreground : background);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(getLabel() + " : " + property.getFixedValue(), x + 2, y + ((height - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) / 2f), 0xFFFFFFFF);
    }

    @Override
    public void handleKeys(int keyCode) {
        if (isFocused()) {
            if (keyCode == Keyboard.KEY_UP) {
                property.increment();
            }
            if (keyCode == Keyboard.KEY_DOWN) {
                property.decrement();
            }
        }
    }
}
