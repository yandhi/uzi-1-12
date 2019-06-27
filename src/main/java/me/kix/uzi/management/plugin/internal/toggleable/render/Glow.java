package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.render.EventRender;

/**
 * Adds a glow to nearby players.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class Glow extends ToggleablePlugin {

    public Glow() {
        super("Glow", Category.RENDER);
        setHidden(true);
    }

    @Register
    public void onRender(EventRender.Hand hand) {
        mc.world.playerEntities.stream()
                .filter(entityPlayer -> entityPlayer != mc.player)
                .forEach(entityPlayer -> entityPlayer.setGlowing(true));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.world.playerEntities.stream()
                .filter(entityPlayer -> entityPlayer != mc.player)
                .forEach(entityPlayer -> entityPlayer.setGlowing(false));
    }
}
