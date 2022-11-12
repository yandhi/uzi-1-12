package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;

public class AutoRespawn extends ToggleablePlugin {

    public AutoRespawn() {
        super("AutoRespawn", Category.QOL);
        setDisplay("Auto Respawn");
        setColor(0xDDA5E6);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (mc.player.isDead) {
            mc.player.respawnPlayer();
        }
    }

}
