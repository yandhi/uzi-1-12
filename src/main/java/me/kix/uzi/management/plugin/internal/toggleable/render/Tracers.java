package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.event.events.render.EventRender;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

/**
 * hvze designed these.
 */
public class Tracers extends ToggleablePlugin {

    private final NumberProperty<Float> width = new NumberProperty<>("Width", 1f, 0.1f, 10f, .1f);
    private final Property<Boolean> players = new Property<>("Players", true);
    private final Property<Boolean> animals = new Property<>("Animals", false);
    private final Property<Boolean> mobs = new Property<>("Monsters", false);

    public Tracers() {
        super("Tracers", Category.RENDER);
        setHidden(true);
        getProperties().add(width);
        getProperties().add(players);
        getProperties().add(animals);
        getProperties().add(mobs);
    }

    @Register
    public void onRender2D(EventRender.TwoDimensional event) {
        if (event.getEntity() instanceof EntityPlayer && players.getValue() || event.getEntity() instanceof EntityAnimal && animals.getValue() || (event.getEntity() instanceof EntityMob && mobs.getValue())) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            final float distance = mc.player.getDistance(event.getEntity());
            float[] color = new float[]{0.0F, 0.90F, 0.0F, 1f};
            if (distance <= 64) {
                color = new float[]{0.9F, distance / 64.0F, 0.0F, 1f};
            }
            if (Uzi.INSTANCE.getFriendManager().isFriend(event.getEntity().getName())) {
                color = new float[]{127 / 255f, 205 / 255f, 1f, 1f};
            }
            double[] pos = {event.getBox().x, event.getBox().y, event.getBox().w, event.getBox().z};
            RenderUtil.drawTracerLine(pos, new float[]{color[0] - 0.2f, color[1] - 0.2f, color[2], color[3]}, width.getValue() * 3, new ScaledResolution(mc));
            RenderUtil.drawTracerLine(pos, color, width.getValue(), new ScaledResolution(mc));
            GlStateManager.popMatrix();
        }
    }
}
