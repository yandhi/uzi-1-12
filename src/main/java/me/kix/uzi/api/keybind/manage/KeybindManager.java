package me.kix.uzi.api.keybind.manage;

import me.kix.uzi.api.keybind.Keybind;
import me.kix.uzi.api.manager.ListManager;

import java.util.Optional;

public class KeybindManager extends ListManager<Keybind> {

    /**
     * Gets a keybind based on the provided name.
     *
     * @param identifier The name of the keybind being searched for.
     * @return The {@link Optional} instance of the keybind.
     */
    public Optional<Keybind> getKeybind(String identifier) {
        return getContents()
                .stream()
                .filter(keybind -> keybind.getLabel().equalsIgnoreCase(identifier))
                .findFirst();
    }
}
