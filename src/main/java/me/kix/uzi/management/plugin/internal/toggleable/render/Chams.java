package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.render.EventPostRenderEntity;
import me.kix.uzi.management.event.render.EventPreRenderEntity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

/**
 * Actual chams, not just wallhack.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class Chams extends ToggleablePlugin {

    public Chams() {
        super("Chams", Category.RENDER);
    }

    @Register
    public void onPreRenderEntity(EventPreRenderEntity event) {
        if (event.getEntity() instanceof EntityPlayer) {
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -2000000F);
            GL11.glColor4f(1f, 1f, .5f, 1f);
        }
    }

    @Register
    public void onPostRenderEntity(EventPostRenderEntity event) {
        if (event.getEntity() instanceof EntityPlayer) {
            GL11.glPolygonOffset(1.0F, 2000000F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glColor4f(1f, 1f, 1f, 1f);
        }
    }

}
