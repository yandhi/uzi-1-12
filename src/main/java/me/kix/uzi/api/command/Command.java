package me.kix.uzi.api.command;

import me.kix.uzi.api.util.interfaces.Labeled;

public abstract class Command implements Labeled {


    private final String label;
    private final String[] aliases;
    private final String description;
    private String help;

    public Command(String label, String[] aliases, String description) {
        this.label = label;
        this.aliases = aliases;
        this.description = description;
    }

    public abstract void execute(String args);

    @Override
    public String getLabel() {
        return label;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public String getHelp() {
        return help;
    }
}
