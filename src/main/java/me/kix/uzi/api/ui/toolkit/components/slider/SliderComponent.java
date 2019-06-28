package me.kix.uzi.api.ui.toolkit.components.slider;

import me.kix.uzi.api.ui.toolkit.AbstractComponent;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.theme.renderer.ComponentRenderer;
import me.kix.uzi.api.ui.toolkit.util.MouseButton;
import me.kix.uzi.api.ui.toolkit.util.MouseUtil;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;
import net.minecraft.util.math.MathHelper;

/**
 * The implementation of {@link me.kix.uzi.api.ui.toolkit.Component} for sliders.
 *
 * @author Kix
 * @since 6/28/2019
 */
public abstract class SliderComponent extends AbstractComponent {

    /**
     * The current position of the slider.
     */
    private float length;

    /**
     * Whether or not the slider is moving.
     */
    private boolean dragging;

    public SliderComponent(String name, Theme theme, Rectangle renderPosition) {
        super(name, theme, renderPosition);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        super.updateComponent(mouseX, mouseY);
        length = MathHelper.floor((value().floatValue() - minimum().floatValue()) / (maximum().floatValue() - minimum().floatValue()) * getRenderPosition().getWidth());
    }

    @Override
    public void drawComponent(ComponentRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.drawComponent(renderer, mouseX, mouseY, partialTicks);
        if (dragging) {
            setValue(((mouseX - getRenderPosition().getX()) * (maximum().floatValue() - minimum().floatValue()) / getRenderPosition().getWidth() + minimum().floatValue()));
        }
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, MouseButton mouseButton) {
        super.mousePressed(mouseX, mouseY, mouseButton);
        if (MouseUtil.mouseWithinRectangle(mouseX, mouseY, getRenderPosition())) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, MouseButton mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        this.dragging = false;
    }

    /**
     * Allows us to change the value.
     *
     * @param value The new value.
     */
    protected abstract void setValue(Number value);

    /**
     * This will probably be just an {@link me.kix.uzi.api.property.properties.NumberProperty}'s value.
     *
     * @return The slider's number value.
     */
    protected abstract Number value();

    /**
     * This will probably be just an {@link me.kix.uzi.api.property.properties.NumberProperty}'s minimum.
     *
     * @return The minimum value that a slider can be.
     */
    protected abstract Number minimum();

    /**
     * This will probably be just an {@link me.kix.uzi.api.property.properties.NumberProperty}'s maximum.
     *
     * @return The maximum value that a slider can be.
     */
    protected abstract Number maximum();

    public float getLength() {
        return length;
    }

    public boolean isDragging() {
        return dragging;
    }
}
