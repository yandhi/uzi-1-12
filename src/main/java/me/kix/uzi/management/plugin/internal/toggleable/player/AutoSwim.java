package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.client.key.GameKeybinding;
import me.kix.uzi.api.game.accessors.entity.Player;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;

public class AutoSwim extends ToggleablePlugin {

    public AutoSwim() {
        super("AutoSwim", Category.PLAYER);
        setDisplay("Auto Swim");
        setColor(0xFF47A4FF);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        ((GameKeybinding) mc.gameSettings.keyBindJump).setPressed(((Player) mc.player).isInLiquid());
    }

}
