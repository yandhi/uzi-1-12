package me.kix.uzi.api.event.events.entity;

import me.kix.uzi.api.event.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

/**
 * Runs on the death of a player.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class EventPlayerDeath extends Event {

    /**
     * The killed entity.
     */
    private final EntityPlayer killedEntity;

    /**
     * The source of damage.
     */
    private final DamageSource damageSource;

    public EventPlayerDeath(EntityPlayer killedEntity, DamageSource damageSource) {
        this.killedEntity = killedEntity;
        this.damageSource = damageSource;
    }

    public EntityPlayer getKilledEntity() {
        return killedEntity;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }
}
