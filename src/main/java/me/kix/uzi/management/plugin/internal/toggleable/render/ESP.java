package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
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

import java.awt.*;

public class ESP extends ToggleablePlugin {

    private final Property<Boolean> box = new Property<>("Box", true);
    private final Property<Boolean> health = new Property<>("Health", true);
    private final Property<Boolean> players = new Property<>("Players", true);
    private final Property<Boolean> animals = new Property<>("Animals", false);
    private final Property<Boolean> mobs = new Property<>("Monsters", false);
    private final Property<Boolean> items = new Property<>("Items", false);

    public ESP() {
        super("ESP", Category.RENDER);
        setHidden(true);
        getProperties().add(box);
        getProperties().add(health);
        getProperties().add(players);
        getProperties().add(animals);
        getProperties().add(mobs);
        getProperties().add(items);
    }

    @Register
    public void onRender2D(EventRender.TwoDimensional event) {
        if (RenderUtil.isInViewFrustrum(event.getEntity())) {
            if (event.getEntity() instanceof EntityPlayer && players.getValue() || event.getEntity() instanceof EntityAnimal && animals.getValue() || (event.getEntity() instanceof EntityMob && mobs.getValue()) || event.getEntity() instanceof EntityItem && items.getValue()) {
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
                return 0xFF22CA00;
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

    private int getHealthColor(EntityLivingBase player) {
        float f = player.getHealth();
        float f1 = player.getMaxHealth();
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 1.0F) | 0xFF000000;
    }
}
