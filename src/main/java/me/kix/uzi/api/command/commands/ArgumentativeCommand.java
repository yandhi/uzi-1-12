package me.kix.uzi.api.command.commands;

import me.kix.uzi.api.command.Command;
import me.kix.uzi.api.command.argument.Argument;
import me.kix.uzi.api.command.argument.factory.ArgumentFactory;
import me.kix.uzi.api.util.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class ArgumentativeCommand extends Command {

    private final List<Argument> arguments = new ArrayList<>();
    private final ArgumentFactory factory = new ArgumentFactory();

    public ArgumentativeCommand(String label, String[] aliases, String description) {
        super(label, aliases, description);
    }

    public void init() {
        factory.setupArgs(this);
    }

    @Override
    public void execute(String args) {
        for (Argument argument : arguments) {
            for (String handle : argument.getHandles()) {
                if (args.split(" ")[1].equalsIgnoreCase(handle)) {
                    argument.execute(args);
                }
            }
        }
    }

    @Override
    public String getHelp() {
        StringBuilder builder = new StringBuilder("<");
        arguments.forEach(arg -> {
            builder.append(arg.getHandles()[0]).append("/");
        });
        return builder.toString().substring(0, builder.length() - 1) + ">";
    }

    public List<Argument> getArguments() {
        return arguments;
    }

}
