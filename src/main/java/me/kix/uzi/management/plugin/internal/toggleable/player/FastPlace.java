package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.client.IMinecraft;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;

public class FastPlace extends ToggleablePlugin {

    public FastPlace() {
        super("FastPlace", Category.PLAYER);
        setDisplay("Fast Place");
        setColor(0xFFC6C1FF);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        ((IMinecraft) mc).setRightClickDelayTimer(0);
    }

}
