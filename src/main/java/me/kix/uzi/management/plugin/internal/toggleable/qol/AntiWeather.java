package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;

public class AntiWeather extends ToggleablePlugin {

    public AntiWeather() {
        super("AntiWeather", Category.QOL);
        setHidden(true);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (mc.world.isRaining()) {
            mc.world.getWorldInfo().setRaining(false);
            mc.world.rainingStrength = 0.0f;
            mc.world.prevRainingStrength = 0.0f;
        }
    }

}
