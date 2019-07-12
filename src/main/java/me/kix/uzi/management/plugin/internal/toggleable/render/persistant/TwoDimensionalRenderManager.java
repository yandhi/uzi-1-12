package me.kix.uzi.management.plugin.internal.toggleable.render.persistant;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.Plugin;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4f;

import me.kix.uzi.api.util.render.GLUProjection;
import me.kix.uzi.api.event.events.render.EventRender;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * This calculates all GLU Project math needed to reduce CPU usage and not clutter our ESP.
 *
 * @author Kix
 */
public class TwoDimensionalRenderManager extends Plugin {

    public TwoDimensionalRenderManager() {
        super("2DManager", Category.RENDER);
        Uzi.INSTANCE.getEventManager().register(this);
    }

    @Register
    public void onWorldToScreen(EventRender.WorldToScreen event) {
        final ScaledResolution scaledRes = new ScaledResolution(mc);

        if (mc.gameSettings.showDebugInfo) {
            return;
        }

        for (Entity entity : mc.world.loadedEntityList) {
            if (entity != mc.player) {
                double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.getPartialTicks();
                double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.getPartialTicks();
                double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.getPartialTicks();
                AxisAlignedBB bb = entity.getEntityBoundingBox().expand(0.1, 0.1, 0.1);
                Vector3d[] corners = {
                        new Vector3d(posX + bb.minX - bb.maxX + entity.width / 2.0f, posY, posZ + bb.minZ - bb.maxZ + entity.width / 2.0f),
                        new Vector3d(posX + bb.maxX - bb.minX - entity.width / 2.0f, posY, posZ + bb.minZ - bb.maxZ + entity.width / 2.0f),
                        new Vector3d(posX + bb.minX - bb.maxX + entity.width / 2.0f, posY, posZ + bb.maxZ - bb.minZ - entity.width / 2.0f),
                        new Vector3d(posX + bb.maxX - bb.minX - entity.width / 2.0f, posY, posZ + bb.maxZ - bb.minZ - entity.width / 2.0f),
                        new Vector3d(posX + bb.minX - bb.maxX + entity.width / 2.0f, posY + bb.maxY - bb.minY, posZ + bb.minZ - bb.maxZ + entity.width / 2.0f),
                        new Vector3d(posX + bb.maxX - bb.minX - entity.width / 2.0f, posY + bb.maxY - bb.minY, posZ + bb.minZ - bb.maxZ + entity.width / 2.0f),
                        new Vector3d(posX + bb.minX - bb.maxX + entity.width / 2.0f, posY + bb.maxY - bb.minY, posZ + bb.maxZ - bb.minZ - entity.width / 2.0f),
                        new Vector3d(posX + bb.maxX - bb.minX - entity.width / 2.0f, posY + bb.maxY - bb.minY, posZ + bb.maxZ - bb.minZ - entity.width / 2.0f)
                };
                GLUProjection.Projection result = null;
                Vector4f transformed = new Vector4f(scaledRes.getScaledWidth() * 2.0f, scaledRes.getScaledHeight() * 2.0f, -1.0f, -1.0f);
                for (Vector3d vec : corners) {
                    result = GLUProjection.getInstance().project(vec.x - mc.getRenderManager().viewerPosX, vec.y - mc.getRenderManager().viewerPosY, vec.z - mc.getRenderManager().viewerPosZ, GLUProjection.ClampMode.NONE, true);
                    transformed.setX((float) Math.min(transformed.getX(), result.getX()));
                    transformed.setY((float) Math.min(transformed.getY(), result.getY()));
                    transformed.setW((float) Math.max(transformed.getW(), result.getX()));
                    transformed.setZ((float) Math.max(transformed.getZ(), result.getY()));
                }
                GlStateManager.pushMatrix();
                Uzi.INSTANCE.getEventManager().dispatch(new EventRender.TwoDimensional(transformed, entity));
                GlStateManager.popMatrix();
            }
        }
    }

}
