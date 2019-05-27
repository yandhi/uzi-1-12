package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.client.key.IKeybinding;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;
import org.lwjgl.input.Keyboard;

public class AutoWalk extends ToggleablePlugin {

    public AutoWalk() {
        super("AutoWalk", Category.MOVEMENT);
        setDisplay("Auto Walk");
        setColor(0xFF8FE6C5);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event){
        ((IKeybinding) mc.gameSettings.keyBindForward).setPressed(true);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ((IKeybinding) mc.gameSettings.keyBindForward).setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
    }
}
