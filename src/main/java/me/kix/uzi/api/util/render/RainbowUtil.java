package me.kix.uzi.api.util.render;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.misc.EventTick;

import java.awt.*;

/**
 * Ticks / keeps rainbow things in sync :).
 *
 * @author jackson
 * @since 1/10/2022
 */
public enum RainbowUtil {

    INSTANCE;

    /**
     * The current hue.
     */
    private int colorHue = 0;

    /**
     * Registers the listener for this class.
     */
    public void registerRainbowListener() {
        Uzi.INSTANCE.getEventManager().register(this);
    }

    @Register
    public void tick(EventTick tick) {
        colorHue += 1.5f;

        if (colorHue > 270) {
            colorHue -= 270;
        }
    }

    /**
     * @return The current color.
     */
    public Color getColor() {
        return RenderUtil.getColorViaHue(colorHue);
    }
}
