package me.kix.uzi.api.game.impl.client.world;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.EventManager;
import me.kix.uzi.management.event.world.EventLoadChunk;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.IChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * @author jackson
 * @since 8/28/18
 */
@Mixin(ChunkProviderServer.class)
public abstract class ChunkProviderServerMixin {

	@Shadow
	@Nullable
	public abstract Chunk getLoadedChunk(int x, int z);

	@Shadow @Final public WorldServer world;

	@Shadow @Final public IChunkLoader chunkLoader;

	@Shadow @Final private Set<Long> loadingChunks;

	@Shadow
	@Nullable
	protected abstract Chunk loadChunkFromFile(int x, int z);

	@Shadow @Final public Long2ObjectMap<Chunk> id2ChunkMap;

	@Shadow @Final public IChunkGenerator chunkGenerator;

	/**
	 * Thanks LPK for giving me a good way to perform this.
	 *
	 * @author Kix
	 * @reason We need to be able to read chunk updates.
	 */
	@Nullable
	public Chunk loadChunk(int x, int z, @Nullable Runnable runnable) {
		ChunkProviderServer _this = (ChunkProviderServer) (Object) this;
		Chunk chunk = this.getLoadedChunk(x, z);
		if (chunk == null) {
			long pos = ChunkPos.asLong(x, z);
			chunk = net.minecraftforge.common.ForgeChunkManager.fetchDormantChunk(pos, this.world);
			if (chunk != null || !(this.chunkLoader instanceof net.minecraft.world.chunk.storage.AnvilChunkLoader)) {
				if (!loadingChunks.add(pos))
					net.minecraftforge.fml.common.FMLLog.bigWarning("There is an attempt to load a chunk ({},{}) in dimension {} that is already being loaded. This will cause weird chunk breakages.", x, z, this.world.provider.getDimension());
				if (chunk == null) chunk = this.loadChunkFromFile(x, z);
				if (chunk != null) {
					this.id2ChunkMap.put(ChunkPos.asLong(x, z), chunk);
					chunk.onLoad();
					chunk.populate(_this, this.chunkGenerator);
				}
				loadingChunks.remove(pos);
			} else {
				net.minecraft.world.chunk.storage.AnvilChunkLoader loader = (net.minecraft.world.chunk.storage.AnvilChunkLoader) this.chunkLoader;
				if (runnable == null || !net.minecraftforge.common.ForgeChunkManager.asyncChunkLoading)
					chunk = net.minecraftforge.common.chunkio.ChunkIOExecutor.syncChunkLoad(this.world, loader, _this, x, z);
				else if (loader.isChunkGeneratedAt(x, z)) {
					// We can only use the async queue for already generated chunks
					net.minecraftforge.common.chunkio.ChunkIOExecutor.queueChunkLoad(this.world, loader, _this, x, z, runnable);
					return null;
				}
			}
		}
		// If we didn't load the chunk async and have a callback run it now
		if (runnable != null) runnable.run();
		return chunk;
	}


}
