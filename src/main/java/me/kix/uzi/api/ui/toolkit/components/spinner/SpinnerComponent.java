package me.kix.uzi.api.ui.toolkit.components.spinner;

import me.kix.uzi.api.ui.toolkit.AbstractComponent;
import me.kix.uzi.api.ui.toolkit.theme.Theme;
import me.kix.uzi.api.ui.toolkit.util.MouseButton;
import me.kix.uzi.api.ui.toolkit.util.MouseUtil;
import me.kix.uzi.api.ui.toolkit.util.Rectangle;

/**
 * An instance of {@link me.kix.uzi.api.ui.toolkit.Component} for cycling through an array of values.
 *
 * @author Kix
 * @since 6/28/2019
 */
public abstract class SpinnerComponent extends AbstractComponent {

    public SpinnerComponent(String name, Theme theme, Rectangle renderPosition) {
        super(name, theme, renderPosition);
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, MouseButton mouseButton) {
        super.mousePressed(mouseX, mouseY, mouseButton);
        if (MouseUtil.mouseWithinRectangle(mouseX, mouseY, getRenderPosition())) {
            if (mouseButton == MouseButton.LEFT) {
                increment();
            } else if (mouseButton == MouseButton.RIGHT) {
                decrement();
            }
        }
    }

    /**
     * Increments the current value.
     */
    protected abstract void increment();

    /**
     * Decrements the current value.
     */
    protected abstract void decrement();
}
