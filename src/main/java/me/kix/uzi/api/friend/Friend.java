package me.kix.uzi.api.friend;

import me.kix.uzi.api.util.interfaces.Labeled;

public class Friend implements Labeled {

    private final String label;
    private final String alias;

    public Friend(String label, String alias) {
        this.label = label;
        this.alias = alias;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public String getAlias() {
        return alias;
    }
}
