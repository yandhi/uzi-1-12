package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import me.kix.uzi.api.game.accessors.client.Game;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.interfaces.MinecraftAccessor;
import me.kix.uzi.api.util.math.MathUtil;
import me.kix.uzi.api.util.math.timing.Timer;
import org.lwjgl.input.Mouse;

/**
 * Automatically clicks the mouse!
 *
 * <p>
 * Swaps between randomizing on an inverse bell curve and a normal bell curve to click at a random speed!
 * </p>
 *
 * @author jackson
 * @since 1/2/2022
 */
public class Autoclicker extends ToggleablePlugin {

    /**
     * The max that the autoclicker can click at.
     */
    private NumberProperty<Double> maximum = new NumberProperty<>("Maximum", 12.0, 1.0, 20.0, .1);

    /**
     * The min that the autoclicker can click at.
     */
    private NumberProperty<Double> minimum = new NumberProperty<>("Minimum", 8.0, 1.0, 20.0, .1);

    /**
     * The timing util for the mod.
     */
    private final Timer timer = new Timer();

    public Autoclicker() {
        super("Autoclicker", Category.COMBAT);
        setDisplay("Auto Clicker");
    }

    @Override
    public void initPlugin() {
        super.initPlugin();
        getProperties().add(maximum);
        getProperties().add(minimum);
    }

    @Register
    public void preUpdate(EventUpdate.Pre pre) {
        if (Mouse.isButtonDown(0)) {
            boolean ticksDivisble = mc.player.ticksExisted % 2 == 0;
            double aps = Math.max(minimum.getValue() + ((ticksDivisble ? MathUtil.bellCurveRandom() : MathUtil.inverseBellCurveRandom()) * maximum.getValue()), maximum.getValue());
            int delay = (int) (1000 / aps);

            if (timer.completed(delay)) {
                ((Game) mc).clickMouse(0);
                timer.reset();
            }
        }
    }
}
