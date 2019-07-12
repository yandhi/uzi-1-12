package me.kix.sodapop.components.spinner;

import me.kix.sodapop.AbstractComponent;
import me.kix.sodapop.Component;
import me.kix.sodapop.util.MouseButton;
import me.kix.sodapop.util.MouseUtil;
import me.kix.sodapop.util.Rectangle;
import me.kix.sodapop.manage.GuiManager;

/**
 * An instance of {@link Component} for cycling through an array of values.
 *
 * @author Kix
 * @since 6/28/2019
 */
public abstract class SpinnerComponent extends AbstractComponent {

    public SpinnerComponent(String name, GuiManager guiManager, Rectangle renderPosition) {
        super(name, guiManager, renderPosition);
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
