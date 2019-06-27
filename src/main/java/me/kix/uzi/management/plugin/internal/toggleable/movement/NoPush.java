package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventEntityCanBePushed;
import me.kix.uzi.api.event.events.entity.EventEntityPushedByWater;

/**
 * Prevents the player from being pushed.
 *
 * @author Kix
 * @since 6/26/2019
 */
public class NoPush extends ToggleablePlugin {

    public NoPush() {
        super("NoPush", Category.MOVEMENT);
        setDisplay("No Push");
    }

    @Register
    public void onEntityPushedByWater(EventEntityPushedByWater pushedByWater) {
        pushedByWater.setCancelled(true);
    }

    @Register
    public void onEntityCanBePushed(EventEntityCanBePushed canBePushed){
        canBePushed.setCancelled(true);
    }

}
