package me.kix.uzi.management.plugin.internal.toggleable.movement;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class InventoryMove extends ToggleablePlugin {

    public InventoryMove() {
        super("InventoryMove", Category.MOVEMENT);
        setDisplay("Inventory Move");
        setColor(0xFFB79CFF);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                mc.player.rotationPitch += 2f;
            if (Keyboard.isKeyDown(Keyboard.KEY_UP))
                mc.player.rotationPitch -= 2f;
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
                mc.player.rotationYaw += 2f;
            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
                mc.player.rotationYaw -= 2f;
            KeyBinding[] keys = {mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight};
            Arrays.stream(keys).forEach(key -> KeyBinding.setKeyBindState(key.getKeyCode(), Keyboard.isKeyDown(key.getKeyCode())));
        }
    }

}
