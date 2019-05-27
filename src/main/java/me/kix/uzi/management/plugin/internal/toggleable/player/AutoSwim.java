package me.kix.uzi.management.plugin.internal.toggleable.player;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.client.key.IKeybinding;
import me.kix.uzi.api.game.accessors.entity.IPlayerSP;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;

public class AutoSwim extends ToggleablePlugin {

    public AutoSwim() {
        super("AutoSwim", Category.PLAYER);
        setDisplay("Auto Swim");
        setColor(0xFF47A4FF);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        ((IKeybinding) mc.gameSettings.keyBindJump).setPressed(((IPlayerSP) mc.player).isInLiquid());
    }

}
