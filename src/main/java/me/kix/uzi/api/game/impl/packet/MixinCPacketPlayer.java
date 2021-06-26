package me.kix.uzi.api.game.impl.packet;

import me.kix.uzi.api.game.accessors.packet.PacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CPacketPlayer.class)
public abstract class MixinCPacketPlayer implements PacketPlayer {

    @Override
    @Accessor("x")
    public abstract double getX();

    @Override
    @Accessor("y")
    public abstract double getY();

    @Override
    @Accessor("z")
    public abstract double getZ();

    @Override
    @Accessor("x")
    public abstract void setX(double x);

    @Override
    @Accessor("y")
    public abstract void setY(double y);

    @Override
    @Accessor("z")
    public abstract void setZ(double z);

    @Override
    @Accessor
    public abstract void setOnGround(boolean onGround);
}
