package me.kix.uzi.api.game.impl.connection;

import me.kix.uzi.Uzi;
import me.kix.uzi.management.event.input.packet.EventPacket;
import me.kix.uzi.management.event.world.EventLoadChunk;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChunkData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {

	@Final
	@Shadow
	private NetworkManager netManager;

	/**
	 * @author Tojatta and Kix
	 * @reason Supposed to fix Antihunger.
	 */
	@Overwrite
	public void sendPacket(Packet<?> packetIn) {
		if (packetIn == null) {
			return;
		}
		EventPacket.Send event = new EventPacket.Send(packetIn);
		Uzi.INSTANCE.getEventManager().dispatch(event);
		if (event.isCancelled()) {
			return;
		}
		this.netManager.sendPacket(packetIn);
	}

	@Inject(method = "handleChunkData", at = @At("HEAD"))
	private void handleChunkData(SPacketChunkData packetIn, CallbackInfo ci) {
		if (!packetIn.isFullChunk()) {
			Uzi.INSTANCE.getEventManager().dispatch(new EventLoadChunk(packetIn.getChunkX(), packetIn.getChunkZ()));
		}
	}

}
