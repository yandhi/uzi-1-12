package me.kix.uzi.api.event.events.world;

import me.kix.uzi.api.event.Event;

/**
 * Allows us to hook into the loading of the chunk.
 *
 * @author Kix
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
