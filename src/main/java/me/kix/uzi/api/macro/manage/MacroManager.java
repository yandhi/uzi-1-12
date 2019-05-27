package me.kix.uzi.api.macro.manage;

import me.kix.uzi.api.macro.Macro;
import me.kix.uzi.api.manager.ListManager;

import java.util.Optional;

public class MacroManager extends ListManager<Macro> {

    public Optional<Macro> getMacro(String identifier) {
        return getContents()
                .stream()
                .filter(macro -> macro.getLabel().equalsIgnoreCase(identifier))
                .findFirst();
    }

}
