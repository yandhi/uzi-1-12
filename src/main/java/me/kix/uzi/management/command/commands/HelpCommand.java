package me.kix.uzi.management.command.commands;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.command.Command;
import me.kix.uzi.api.command.commands.PluginCommand;
import me.kix.uzi.api.util.logging.Logger;

/**
 * @author Kix
 * @since 8/31/18
 */
public class HelpCommand extends Command {

	public HelpCommand() {
		super("Help", new String[] { "he", "h" }, "Allows us to see the commands in the client.");
	}

	@Override
	public void execute(String args) {
		for (Command command : Uzi.INSTANCE.getCommandManager().getContents()) {
			if (!(command instanceof PluginCommand)) {
				Logger.printCommandReturn("\247c" + command.getLabel() + "\2477: \247f" + command.getDescription());
			}
		}
	}
}
