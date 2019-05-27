package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.management.event.render.EventRender;
import me.kix.uzi.management.event.render.EventRenderNameplate;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class Nametags extends ToggleablePlugin {

    private final Property<Boolean> players = new Property<>("Players", true);
    private final Property<Boolean> animals = new Property<>("Animals", false);
    private final Property<Boolean> mobs = new Property<>("Monsters", false);

    public Nametags() {
        super("Nametags", Category.RENDER);
        setHidden(true);
        getProperties().add(players);
        getProperties().add(animals);
        getProperties().add(mobs);
    }

    @Register
    public void onRender2D(EventRender.TwoDimensional event) {
        if (RenderUtil.isInViewFrustrum(event.getEntity())) {
            if (event.getEntity() instanceof EntityPlayer && players.getValue() || event.getEntity() instanceof EntityAnimal && animals.getValue() || (event.getEntity() instanceof EntityMob && mobs.getValue())) {
                double health = event.getEntity().getHealth() / 2;
                double maxHealth = event.getEntity().getMaxHealth() / 2;
                double percentage = 100 * (health / maxHealth);
                String healthColor;
                if (percentage > 75)
                    healthColor = "2";
                else if (percentage > 50)
                    healthColor = "e";
                else if (percentage > 25)
                    healthColor = "6";
                else
                    healthColor = "4";
                String name = event.getEntity().getName();
                if (Uzi.INSTANCE.getFriendManager().isFriend(event.getEntity().getName()))
                    name = Uzi.INSTANCE.getFriendManager().getReplacedText(name);
                RenderUtil.drawRect(event.getBox().x + ((event.getBox().w - event.getBox().x) / 2) - (mc.fontRenderer.getStringWidth("\2477" + name + " \247" + healthColor + Math.round(health)) / 2f) - 2f, event.getBox().y - 14, event.getBox().x + ((event.getBox().w - event.getBox().x) / 2) + (mc.fontRenderer.getStringWidth("\2477" + name + " \247" + healthColor + Math.round(health)) / 2f) + 1.5f, event.getBox().y - 2f, 0x80000000);
                mc.fontRenderer.drawStringWithShadow("\2477" + name + " \247" + healthColor + Math.round(health),
                        event.getBox().x + ((event.getBox().w - event.getBox().x) / 2) - (mc.fontRenderer.getStringWidth("\2477" + name + " \247" + healthColor + Math.round(health)) / 2f),
                        event.getBox().y - 12,
                        0xFFFFFFFF);
            }
        }
    }

    @Register
    public void onRenderNameplate(EventRenderNameplate event) {
        event.setCancelled(true);
    }

}
