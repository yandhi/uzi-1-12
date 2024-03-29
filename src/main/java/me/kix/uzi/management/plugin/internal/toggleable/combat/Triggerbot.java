package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Mouse;

import java.util.Random;

public class Triggerbot extends ToggleablePlugin {

    private final NumberProperty<Integer> aps = new NumberProperty<>("APS", 5, 1, 15, 1);
    private final NumberProperty<Float> range = new NumberProperty<>("Range", 4f, 3f, 6f, .1f);
    private final Property<Boolean> mouse = new Property<>("Mouse", true);
    private final Timer timer;
    private final Random random;

    public Triggerbot() {
        super("Triggerbot", Category.COMBAT);
        setColor(0xE66745);
        timer = new Timer();
        random = new Random();
    }

    @Override
    public void initPlugin() {
        super.initPlugin();
        getProperties().add(aps);
        getProperties().add(range);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof EntityPlayer && mc.player.getDistance(mc.objectMouseOver.entityHit) <= range.getValue()) {
            if (mouse.getValue()) {
                if (Mouse.isButtonDown(0)) {
                    if (timer.completed(1000 / (random.nextInt((aps.getValue() + 2) - (aps.getValue() - 2)) + aps.getValue()))) {
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                        mc.playerController.attackEntity(mc.player, mc.objectMouseOver.entityHit);
                        timer.reset();
                    }
                }
            } else {
                if (timer.completed(1000 / (random.nextInt((aps.getValue() + 2) - (aps.getValue() - 2)) + aps.getValue()))) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.playerController.attackEntity(mc.player, mc.objectMouseOver.entityHit);
                    timer.reset();
                }
            }
        }
    }

}
