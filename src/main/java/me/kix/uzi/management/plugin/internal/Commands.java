package me.kix.uzi.management.plugin.internal;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.command.Command;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;
import me.kix.uzi.api.util.logging.Logger;
import me.kix.uzi.management.event.input.chat.EventSendOffChatMessage;

public class Commands extends Plugin {

    private final String catalyst = ".";

    public Commands() {
        super("Commands", Category.MISCELLANEOUS);
        Uzi.INSTANCE.getEventManager().register(this);
    }

    @Register
    public void onChatMessageDispatch(EventSendOffChatMessage event) {
        checkCommands(event.getMessage(), event);
    }

    private void checkCommands(String message, EventSendOffChatMessage event) {
        if (message.startsWith(catalyst)) {
            for (Command command : Uzi.INSTANCE.getCommandManager().getContents()) {
                if (message.split(" ")[0].equalsIgnoreCase("." + command.getLabel())) {
                    try {
                        command.execute(message);
                    } catch (Exception e) {
                        Logger.printMessage("Execution Failed! Possible arguments are: " + command.getHelp() + ".");
                    }
                    event.setCancelled(true);
                } else {
                    for (String alias : command.getAliases()) {
                        if (message.split(" ")[0].equalsIgnoreCase("." + alias)) {
                            try {
                                command.execute(message);
                            } catch (Exception e) {
                                Logger.printMessage("Execution Failed! Possible arguments are: " + command.getHelp() + ".");
                            }
                            event.setCancelled(true);
                        }
                    }
                }
            }

            if (!event.isCancelled()) {
                Logger.printMessage("Command \"" + message + "\" was not found!");
                event.setCancelled(true);
            }
        }
    }

}
