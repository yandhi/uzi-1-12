package me.kix.uzi.management.plugin.internal.toggleable.qol;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.event.events.world.EventLoadChunk;
import net.minecraft.util.math.AxisAlignedBB;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Gives us various information about a chunk.
 *
 * <p>
 * Some of this information would be if the chunk has already been loaded and if it has not been.
 * </p>
 *
 * @author Kix
 * @since 8/28/18
 */
public class ChunkUpdates extends ToggleablePlugin {

	/**
	 * A set of temporary cache meant to be used for rendering the chunks that we are going to.
	 */
	private final Set<ChunkData> cachedLoadedChunks = new HashSet<>();

	public ChunkUpdates() {
		super("ChunkUpdates", Category.QOL);
		setDisplay("Chunk Updates");
		setColor(new Color(0x965080).getRGB());
	}

	@Register
	public void onRenderWorld(EventRender.Hand event) {
		for (ChunkData chunk : cachedLoadedChunks) {
			renderChunk(chunk);
		}
	}

	@Register
	public void onChunkLoad(EventLoadChunk event) {
		ChunkData chunkData = new ChunkData(event.getX() * 16, event.getZ() * 16);
		cachedLoadedChunks.add(chunkData);
	}

	private void renderChunk(ChunkData chunk) {
		double x = chunk.x - mc.getRenderManager().viewerPosX;
		double y = -mc.getRenderManager().viewerPosY;
		double z = chunk.z - mc.getRenderManager().viewerPosZ;
		AxisAlignedBB chunkBB = new AxisAlignedBB(0d, 0d, 0d, 16d, 0, 16d).offset(x, y, z);
		RenderUtil.bb(chunkBB, 2, new Color(255, 255, 0, 70));
	}

	@Override
	public void onDisable() {
		super.onDisable();
		cachedLoadedChunks.clear();
	}

	/**
	 * Contains all of the necessary info about a chunk.
	 */
	private class ChunkData {

		private final double x, z;

		ChunkData(double x, double z) {
			this.x = x;
			this.z = z;
		}

		public double getX() {
			return x;
		}

		public double getZ() {
			return z;
		}
	}

}
