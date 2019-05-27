package me.kix.uzi.api.game.impl.packet;

import me.kix.uzi.api.game.accessors.packet.ICPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CPacketPlayer.class)
public abstract class MixinCPacketPlayer implements ICPacketPlayer {

    @Override
    @Accessor
    public abstract void setX(double x);

    @Override
    @Accessor
    public abstract void setY(double y);

    @Override
    @Accessor
    public abstract void setZ(double z);

    @Override
    @Accessor
    public abstract void setOnGround(boolean onGround);
}
