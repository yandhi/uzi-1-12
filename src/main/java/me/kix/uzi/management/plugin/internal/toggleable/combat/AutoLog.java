package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.client.gui.GuiMainMenu;

/**
 * Automatically disconnects when the player is close to dying.
 * Meant for servers without a CombatLogger.
 *
 * @author Kix
 * @since 5/11/2018
 */
public class AutoLog extends ToggleablePlugin {

    private final NumberProperty<Float> health = new NumberProperty<>("Health", 6f, 2f, 20f, .5f);

    public AutoLog() {
        super("AutoLog", Category.COMBAT);
        setDisplay("Auto Log");
        setColor(0xE6D782);
    }

    @Override
    public void initPlugin() {
        super.initPlugin();
        getProperties().add(health);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (mc.player.getHealth() <= health.getValue()) {
            mc.world.sendQuittingDisconnectingPacket();
            mc.currentScreen = new GuiMainMenu();
            toggle();
        }
    }

}
