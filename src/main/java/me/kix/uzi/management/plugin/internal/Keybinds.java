package me.kix.uzi.management.plugin.internal;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.keybind.Keybind;
import me.kix.uzi.api.plugin.*;
import me.kix.uzi.api.event.events.input.key.EventKeyPressed;
import me.kix.uzi.api.plugin.service.Service;
import me.kix.uzi.management.click.GuiClick;
import org.lwjgl.input.Keyboard;

public class Keybinds extends Service {

    public Keybinds() {
        super("Keybinds", Category.MISCELLANEOUS);
    }

    @Register
    public void onKeyPressed(EventKeyPressed event) {
        Uzi.INSTANCE.getKeybindManager().getContents()
                .stream()
                .filter(keybind -> event.getKey() == Keyboard.getKeyIndex(keybind.getKey()))
                .forEach(Keybind::onPress);
        if (event.getKey() == Keyboard.KEY_RSHIFT) {
            mc.displayGuiScreen(GuiClick.getScreen());
        }
    }

}
