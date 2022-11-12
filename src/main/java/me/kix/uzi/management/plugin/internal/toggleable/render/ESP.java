package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.renderer.GameRenderManager;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.EnumProperty;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.event.events.render.EventRender;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Originally written a really long time ago? 2018 I'm guessing.
 *
 * I added a 3D mode and revised in 2021.
 *
 * @author Jackson
 * @since 2018
 */
public class ESP extends ToggleablePlugin {

    /**
     * What to render on the esp.
     */
    private final Property<Boolean> box = new Property<>("Box", true);
    private final Property<Boolean> health = new Property<>("Health", true);

    /**
     * Whether to render them.
     */
    private final Property<Boolean> players = new Property<>("Players", true);
    private final Property<Boolean> animals = new Property<>("Animals", false);
    private final Property<Boolean> mobs = new Property<>("Monsters", false);
    private final Property<Boolean> items = new Property<>("Items", false);

    /**
     * The canvas for us to render on.
     */
    private final EnumProperty<Canvas> canvas = new EnumProperty<>("Canvas", Canvas.TWOD);

    public ESP() {
        super("ESP", Category.RENDER);
        setHidden(true);
        getProperties().add(canvas);
        getProperties().add(box);
        getProperties().add(health);
        getProperties().add(players);
        getProperties().add(animals);
        getProperties().add(mobs);
        getProperties().add(items);
    }

    @Register
    public void onRender2D(EventRender.TwoDimensional event) {
        if (canvas.getValue() == Canvas.TWOD && isRenderable(event.getEntity())) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.scale(.5f, .5f, .5f);
            float x = event.getBox().x * 2;
            float x2 = event.getBox().w * 2;
            float y = event.getBox().y * 2;
            float y2 = event.getBox().z * 2;
            if (box.getValue()) {
                RenderUtil.drawHollowBox(x, y, x2, y2, 3f, Color.BLACK.getRGB());
                RenderUtil.drawHollowBox(x + 1f, y + 1f, x2 - 1f, y2 + 1f, 1f, getColorFromEntity(event.getEntity()));
            }
            if (health.getValue() && event.getEntity() instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) event.getEntity();
                float healthHeight = (y2 - y) * (entityLivingBase.getHealth() / entityLivingBase.getMaxHealth());
                RenderUtil.drawRect(x - 4f, y, x - 1f, y2 + 3f, -0x1000000);
                RenderUtil.drawRect(x - 3f, y2 - healthHeight + 1f, x - 2f, y2 + 2f, getHealthColor(entityLivingBase));
            }
            GlStateManager.popMatrix();
        }
    }

    @Register
    public void renderHand(EventRender.Hand hand) {
        if (canvas.getValue() != Canvas.THREED) return;
        for (Entity entity : mc.world.loadedEntityList) {
            if (isRenderable(entity)) {
                GL11.glPushMatrix();
                RenderUtil.enable3D();
                GL11.glLineWidth(1f);
                double posX = entity.lastTickPosX + ((entity.posX - entity.lastTickPosX) * hand.getPartialTicks()) - ((GameRenderManager) mc.getRenderManager()).getRenderPosX();
                double posY = entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * hand.getPartialTicks()) - ((GameRenderManager) mc.getRenderManager()).getRenderPosY();
                double posZ = entity.lastTickPosZ + ((entity.posZ - entity.lastTickPosZ) * hand.getPartialTicks()) - ((GameRenderManager) mc.getRenderManager()).getRenderPosZ();
                AxisAlignedBB bb = new AxisAlignedBB(-.5, 0, -.5, .5, 2, .5).offset(posX, posY, posZ);
                RenderUtil.color(getColorFromEntity(entity));
                RenderUtil.drawOutlinedBox(bb);
                RenderUtil.disable3D();
                RenderUtil.color(Color.WHITE.getRGB());
                GL11.glPopMatrix();
            }
        }
    }

    /**
     * Whether the entity is renderable or not.
     *
     * @param entity The entity being checked.
     * @return The entity's renderability.
     */
    private boolean isRenderable(Entity entity) {
        return entity != mc.player && RenderUtil.isInViewFrustrum(entity) &&
                entity instanceof EntityPlayer && players.getValue() ||
                entity instanceof EntityAnimal && animals.getValue() ||
                (entity instanceof EntityMob && mobs.getValue()) ||
                entity instanceof EntityItem && items.getValue();
    }

    /**
     * Gets the color based on the entity type.
     *
     * @param entity The entity being color checked.
     * @return The color of the entity's esp box.
     */
    private int getColorFromEntity(Entity entity) {
        if (entity.isInvisible()) {
            return 0xFFCCC524;
        }
        if (entity instanceof EntityPlayer) {
            if (Uzi.INSTANCE.getFriendManager().isFriend(entity.getName())) {
                return 0xFF7FCDFF;
            } else {
                return 0xFFDCC6FF;
            }
        }
        if (entity instanceof EntityAnimal) {
            return 0xFFCA6EB0;
        }
        if (entity instanceof EntityMob) {
            return 0xFFCC3C3A;
        }
        return 0xFF8642CC;
    }

    /**
     * Calculates a color for the health bar.
     *
     * @param entity The entity we are getting the health color of.
     * @return The entity's health color.
     */
    private int getHealthColor(EntityLivingBase entity) {
        float f = entity.getHealth();
        float f1 = entity.getMaxHealth();
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 1.0F) | 0xFF000000;
    }


    /**
     * Which canvas to render upon.
     */
    private enum Canvas {
        TWOD,
        THREED
    }
}
