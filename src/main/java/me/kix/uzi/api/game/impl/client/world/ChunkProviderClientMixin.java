package me.kix.uzi.api.game.impl.client.world;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import me.kix.uzi.Uzi;
import me.kix.uzi.management.event.world.EventLoadChunk;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author jackson
 * @since 8/28/18
 */
@Mixin(ChunkProviderClient.class)
public class ChunkProviderClientMixin {

	@Shadow @Final private World world;

	@Shadow @Final private Long2ObjectMap<Chunk> chunkMapping;

	/**
	 * @author Kix
	 * @reason Allows us to hook into the game's chunk loading system.
	 */
	public Chunk loadChunk(int chunkX, int chunkZ) {
		Chunk chunk = new Chunk(this.world, chunkX, chunkZ);
		this.chunkMapping.put(ChunkPos.asLong(chunkX, chunkZ), chunk);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.ChunkEvent.Load(chunk));
		chunk.markLoaded(true);
		return chunk;
	}

}
