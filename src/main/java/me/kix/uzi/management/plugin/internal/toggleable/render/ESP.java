package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.event.events.render.EventRender;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class ESP extends ToggleablePlugin {

    private final Property<Boolean> box = new Property<>("Box", true);
    private final Property<Boolean> health = new Property<>("Health", true);
    private final Property<Boolean> players = new Property<>("Players", true);
    private final Property<Boolean> animals = new Property<>("Animals", false);
    private final Property<Boolean> mobs = new Property<>("Monsters", false);

    public ESP() {
        super("ESP", Category.RENDER);
        setHidden(true);
        getProperties().add(box);
        getProperties().add(health);
        getProperties().add(players);
        getProperties().add(animals);
        getProperties().add(mobs);
    }

    @Register
    public void onRender2D(EventRender.TwoDimensional event) {
        if (RenderUtil.isInViewFrustrum(event.getEntity())) {
            if (event.getEntity() instanceof EntityPlayer && players.getValue() || event.getEntity() instanceof EntityAnimal && animals.getValue() || (event.getEntity() instanceof EntityMob && mobs.getValue())) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.scale(.5f, .5f, .5f);
                float x = event.getBox().x * 2;
                float x2 = event.getBox().w * 2;
                float y = event.getBox().y * 2;
                float y2 = event.getBox().z * 2;
                if (box.getValue()) {
                    RenderUtil.drawHollowBox(x, y, x2, y2, 3f, Color.BLACK.getRGB());
                    RenderUtil.drawHollowBox(x + 1f, y + 1f, x2 - 1f, y2 + 1f, 1f, Uzi.INSTANCE.getFriendManager().isFriend(event.getEntity().getName()) ? 0xFF7FCDFF : (event.getEntity().getName().equalsIgnoreCase(mc.player.getName()) ? 0xFFB43EFF : 0xFFce8cc9));
                }
                if (health.getValue()) {
                    float healthHeight = (y2 - y) * (event.getEntity().getHealth() / event.getEntity().getMaxHealth());
                    RenderUtil.drawRect(x - 4f, y, x - 1f, y2 + 3f, -0x1000000);
                    RenderUtil.drawRect(x - 3f, y2 - healthHeight + 1f, x - 2f, y2 + 2f, getHealthColor(event.getEntity()));
                }
                GlStateManager.popMatrix();
            }
        }
    }

    private int getHealthColor(EntityLivingBase player) {
        float f = player.getHealth();
        float f1 = player.getMaxHealth();
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 1.0F) | 0xFF000000;
    }
}
