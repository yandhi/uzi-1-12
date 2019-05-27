package me.kix.uzi.management.plugin.internal;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.macro.Macro;
import me.kix.uzi.api.plugin.*;
import me.kix.uzi.management.event.input.key.EventKeyPressed;
import me.kix.uzi.management.ui.click.GuiClick;
import org.lwjgl.input.Keyboard;

public class Keybinds extends Plugin {

    private GuiClick click;

    public Keybinds() {
        super("Keybinds", Category.MISCELLANEOUS);
        Uzi.INSTANCE.getEventManager().register(this);
    }

    @Register
    public void onKeyPressed(EventKeyPressed event) {
        Uzi.INSTANCE.getMacroManager().getContents()
                .stream()
                .filter(macro -> event.getKey() == Keyboard.getKeyIndex(macro.getFacet()))
                .forEach(Macro::performAction);
        if (event.getKey() == Keyboard.KEY_RSHIFT) {
            if(click == null){
                click = new GuiClick();
                click.init();
            }
            mc.displayGuiScreen(click);
        }
    }

}
