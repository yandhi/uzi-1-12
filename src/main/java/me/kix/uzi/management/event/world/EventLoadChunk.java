package me.kix.uzi.management.event.world;

import me.kix.uzi.api.event.Event;
import net.minecraft.world.chunk.Chunk;

/**
 * Allows us to hook into the loading of the chunk.
 *
 * @author jackson
 * @since 8/28/18
 */
public class EventLoadChunk extends Event {

	private final double x, z;

	public EventLoadChunk(double x, double z) {
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
