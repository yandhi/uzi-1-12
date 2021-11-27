package me.kix.uzi.management.ui.tab.item.impl.focus;

import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.theme.renderer.ComponentRenderer;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.ui.tab.item.impl.FocusTabComponent;
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
public class SliderTabComponent extends FocusTabComponent {

    /**
     * The property that this item handles.
     */
    private final NumberProperty property;

    public SliderTabComponent(GuiManager guiManager, Rectangle renderPosition, NumberProperty property) {
        super(property.getLabel(), guiManager, renderPosition);
        this.property = property;
    }

    @Override
    public void drawComponent(ComponentRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.drawComponent(renderer, mouseX, mouseY, partialTicks);
    }

    /**
     * @return The length of the slider proportionate to the width.
     */
    public float getSliderLength() {
        return MathHelper.floor((((Number) property.getValue()).floatValue() - property.getMinimum().floatValue()) / (property.getMaximum().floatValue() - property.getMinimum().floatValue()) * getRenderPosition().getWidth());
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
        return getName() + " : " + Math.round(((Number) property.getValue()).doubleValue());
    }

    public NumberProperty getProperty() {
        return property;
    }
}
