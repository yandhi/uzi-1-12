package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.network.TPSTracker;
import me.kix.uzi.api.event.events.block.EventRelativeBlockHardness;

/**
 * Prevents players from desyncing blocks.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class AntiDesync extends ToggleablePlugin {

    public AntiDesync() {
        super("AntiDesync", Category.PLAYER);
        setDisplay("Anti Desync");
    }

    @Register
    public void onRelativeBlockHardness(EventRelativeBlockHardness relativeBlockHardness) {
        relativeBlockHardness.setHardness(TPSTracker.getTracker().getTps() / 20f);
    }
}
