package me.kix.uzi.api.friend;

import me.kix.uzi.api.util.interfaces.Labeled;

/**
 * A container class for an in-game ally.
 *
 * @author Kix
 * @since May 2018 (Revised June 2019).
 */
public class Friend implements Labeled {

    /**
     * The in-game-name of the friend.
     */
    private final String label;

    /**
     * The nickname of the friend.l
     */
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
