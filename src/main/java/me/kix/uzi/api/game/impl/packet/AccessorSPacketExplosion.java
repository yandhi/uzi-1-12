package me.kix.uzi.api.game.impl.packet;

import net.minecraft.network.play.server.SPacketExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author jackson
 * @since 1/2/2022
 */
@Mixin(SPacketExplosion.class)
public interface AccessorSPacketExplosion {

    /**
     * @return our motion x pos.
     */
    @Accessor
    float getMotionX();

    /**
     * changes the motion x pos.
     *
     * @param x The new x pos.
     */
    @Accessor
    void setMotionX(float x);

    /**
     * @return our motion y pos.
     */
    @Accessor
    float getMotionY();

    /**
     * changes the motion y pos.
     *
     * @param y The new y pos.
     */
    @Accessor
    void setMotionY(float y);

    /**
     * @return our motion z pos.
     */
    @Accessor
    float getMotionZ();

    /**
     * changes the motion z pos.
     *
     * @param z The new z pos.
     */
    @Accessor
    void setMotionZ(float z);
}
