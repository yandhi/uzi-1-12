package me.kix.uzi.management.command.commands;

import me.kix.uzi.api.command.Command;
import me.kix.uzi.management.plugin.internal.toggleable.qol.AutoTranslate;

/**
 * Allows us to modify the characters for {@link AutoTranslate}.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class SmartChatCommand extends Command {

    public SmartChatCommand() {
        super("SmartChat", new String[]{"sc", "smartc", "translator"}, "Modifies the characters for smart-chat.");
    }

    @Override
    public void execute(String args) {
        String[] split = args.split(" ");

        if (split.length >= 1) {
            AutoTranslate.splitter = split[1];
        }
    }
}
