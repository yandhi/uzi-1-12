package me.kix.uzi.api.game.accessors.client.multiplayer;

/**
 * The networking controller for the player.
 *
 * @author Kix
 * @since April 2018.
 */
public interface PlayerController {

    /**
     * Changes the delay between block breaks.
     *
     * @param blockHitDelay The new block hit delay.
     */
    void setBlockHitDelay(int blockHitDelay);

    /**
     * Changes the damage of the block currently being broken.
     *
     * @param curBlockDamageMP The new block damage.
     */
    void setCurBlockDamageMP(float curBlockDamageMP);

    /**
     * @return The current damage to the block being broken.
     */
    float getCurBlockDamageMP();
}
