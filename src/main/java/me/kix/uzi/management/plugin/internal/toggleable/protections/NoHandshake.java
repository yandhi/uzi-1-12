package me.kix.uzi.management.plugin.internal.toggleable.protections;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.misc.EventServerHandshake;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;

/**
 * Prevents forge from giving out the player's mod list.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class NoHandshake extends ToggleablePlugin {

    public NoHandshake() {
        super("NoHandshake", Category.PROTECTIONS);
        setDisplay("No Handshake");
    }

    @Register
    public void onServerHandshake(EventServerHandshake serverHandshake) {
        serverHandshake.setCancelled(true);
    }

}
