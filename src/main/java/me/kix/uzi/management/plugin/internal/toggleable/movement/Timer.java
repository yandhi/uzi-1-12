package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.game.accessors.client.IMinecraft;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;

/**
 * Speeds the game up.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class Timer extends ToggleablePlugin {

    public Timer() {
        super("Timer", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ((IMinecraft) mc).getTimer().elapsedTicks = 10;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ((IMinecraft) mc).getTimer().elapsedTicks = 1;
    }
}
