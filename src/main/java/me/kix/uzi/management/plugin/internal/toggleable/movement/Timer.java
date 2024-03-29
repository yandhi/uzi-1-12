package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.client.Game;
import me.kix.uzi.api.game.accessors.client.math.GameTimer;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.event.events.entity.EventUpdate;

/**
 * Speeds the game up.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Timer extends ToggleablePlugin {

    /**
     * The speed of the timer.
     */
    private final NumberProperty<Integer> speed = new NumberProperty<>("Speed", 10, 2, 10, 1);

    public Timer() {
        super("Timer", Category.MOVEMENT);
        getProperties().add(speed);
    }

    @Register
    public void onPreUpdate(EventUpdate.Pre pre) {
        Game game = (Game) mc;
        GameTimer timer = (GameTimer) game.getTimer();

        timer.setTickLength(50f / speed.getValue());
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Game game = (Game) mc;
        GameTimer timer = (GameTimer) game.getTimer();

        timer.setTickLength(50);
    }
}
