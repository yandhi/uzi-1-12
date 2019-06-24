package me.kix.uzi.management.event.render;

import me.kix.uzi.api.event.cancellable.EventCancellable;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

/**
 * @author Kix
 * @since 9/1/18
 */
public class EventPreRenderEntity extends EventCancellable {

	private final Render<?> renderer;
	private final Entity entity;
	private final double x, y, z;
	private final float entityYaw;
	private final float partialTicks;

	public EventPreRenderEntity(Render<?> renderer, Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.renderer = renderer;
		this.entity = entity;
		this.x = x;
		this.y = y;
		this.z = z;
		this.entityYaw = entityYaw;
		this.partialTicks = partialTicks;
	}

	public Render<?> getRenderer() {
		return renderer;
	}

	public Entity getEntity() {
		return entity;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getEntityYaw() {
		return entityYaw;
	}

	public float getPartialTicks() {
		return partialTicks;
	}

}
