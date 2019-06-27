package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.event.events.render.EventPostRenderEntity;
import me.kix.uzi.api.event.events.render.EventPreRenderEntity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author Kix
 * @since 9/1/18
 */
public class Wallhack extends ToggleablePlugin {

	public Wallhack() {
		super("Wallhack", Category.RENDER);
		setColor(new Color(0xB47949).getRGB());
		setHidden(true);
	}

	@Register
	public void onPreRenderEntity(EventPreRenderEntity event) {
		if (event.getEntity() instanceof EntityPlayer) {
			GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
			GL11.glPolygonOffset(1.0F, -2000000F);
		}
	}

	@Register
	public void onPostRenderEntity(EventPostRenderEntity event){
		if (event.getEntity() instanceof EntityPlayer) {
			GL11.glPolygonOffset(1.0F, 2000000F);
			GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
		}
	}

}
