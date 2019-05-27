package me.kix.uzi.api.game.impl.packet;

import me.kix.uzi.api.game.accessors.packet.ICPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author Kix
 * @since 5/25/2018
 */
@Mixin(CPacketUseEntity.class)
public abstract class MixinCPacketUseEntity implements ICPacketUseEntity {

    @Override
    @Accessor
    public abstract int getEntityId();
}
