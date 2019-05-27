package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.render.EventRender;

public class Fullbright extends ToggleablePlugin {

    private float gamma;

    public Fullbright() {
        super("Fullbright", Category.RENDER);
        setColor(-3154432);
        gamma = mc.gameSettings.gammaSetting;
        setDisplay("Full Bright");
    }

    @Register
    public void onWorldToScreen(EventRender.WorldToScreen event) {
        if (isEnabled()) {
            if (mc.gameSettings.gammaSetting < 16f) {
                mc.gameSettings.gammaSetting += .01f;
            }
        } else {
            if (mc.gameSettings.gammaSetting > 0f) {
                mc.gameSettings.gammaSetting -= .01f;
            }
            if (mc.gameSettings.gammaSetting <= gamma) {
                mc.gameSettings.gammaSetting = gamma;
                Uzi.INSTANCE.getEventManager().unregister(this);
            }
        }
    }

    @Override
    public void onEnable() {
        /* Removed the super here because this event needs to be running a tad bit longer than normal. */
        Uzi.INSTANCE.getEventManager().register(this);
    }

    @Override
    public void onDisable() {

    }
}
