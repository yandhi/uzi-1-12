package me.kix.uzi.api.game.impl.packet;

import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Accessor for velocity packet motion pos.
 *
 * @author jackson
 * @since 1/2/2022
 */
@Mixin(SPacketEntityVelocity.class)
public interface AccessorSPacketEntityVelocity {

    /**
     * @return our motion x pos.
     */
    @Accessor
    int getMotionX();

    /**
     * changes the motion x pos.
     *
     * @param x The new x pos.
     */
    @Accessor
    void setMotionX(int x);

    /**
     * @return our motion y pos.
     */
    @Accessor
    int getMotionY();

    /**
     * changes the motion y pos.
     *
     * @param y The new y pos.
     */
    @Accessor
    void setMotionY(int y);

    /**
     * @return our motion z pos.
     */
    @Accessor
    int getMotionZ();

    /**
     * changes the motion z pos.
     *
     * @param z The new z pos.
     */
    @Accessor
    void setMotionZ(int z);
}
