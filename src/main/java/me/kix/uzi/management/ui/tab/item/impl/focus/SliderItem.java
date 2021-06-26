package me.kix.uzi.management.ui.tab.item.impl.focus;

import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.ui.tab.item.impl.FocusItem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * An implementation of {@link me.kix.uzi.management.ui.tab.item.Item} that is a slider.
 *
 * @author yandhi
 * @since 6/24/2021
 */
public class SliderItem extends FocusItem {

    /**
     * The property that this item handles.
     */
    private final NumberProperty property;

    public SliderItem(NumberProperty property) {
        super(property.getLabel());
        this.property = property;
    }

    @Override
    public void draw(int x, int y, int width, int height, int foreground, int background) {
        float sliderLength = MathHelper.floor((((Number) property.getValue()).floatValue() - property.getMinimum().floatValue()) / (property.getMaximum().floatValue() - property.getMinimum().floatValue()) * width);

        RenderUtil.drawRect(x, y, x + width, y + height, background);
        RenderUtil.drawRect(x, y, x + sliderLength, y + height, isHovered() ? foreground : new Color(foreground).darker().darker().getRGB());
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(property.getLabel(), x + 2, y + ((height - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) / 2f), 0xFFFFFFFF);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(property.getValue().toString(), x + width - Minecraft.getMinecraft().fontRenderer.getStringWidth(property.getValue().toString()) - 2, y + ((height - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) / 2f), 0xFFDEDEDE);
    }

    @Override
    public void handleKeys(int keyCode) {
        super.handleKeys(keyCode);

        if (isFocused()) {
            if (keyCode == Keyboard.KEY_UP) {
                property.setValue(((Number) property.getValue()).floatValue() + property.getIncrement().floatValue());
            }
            if (keyCode == Keyboard.KEY_DOWN) {
                property.setValue(((Number) property.getValue()).floatValue() - property.getIncrement().floatValue());
            }
        }
    }

    /**
     * @return The raw label and value together for width purposes.
     */
    public String getRaw() {
        return getLabel() + " : " + Math.round(((Number) property.getValue()).doubleValue());
    }

}
