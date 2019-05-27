package me.kix.uzi.api.command.argument;

public abstract class Argument{

    private final String[] handles;

    public Argument(String[] handles) {
        this.handles = handles;
    }

    public abstract void execute(String args);

    public String[] getHandles() {
        return handles;
    }
}
