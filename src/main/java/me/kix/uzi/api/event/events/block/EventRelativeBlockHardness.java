package me.kix.uzi.api.event.events.block;

import me.kix.uzi.api.event.Event;

/**
 * Allows the player to edit relative block hardness.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class EventRelativeBlockHardness extends Event {

    /**
     * The hardness of the block.
     */
    private float hardness;

    public EventRelativeBlockHardness(float hardness) {
        this.hardness = hardness;
    }

    public float getHardness() {
        return hardness;
    }

    public void setHardness(float hardness) {
        this.hardness = hardness;
    }
}
