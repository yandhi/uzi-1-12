package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.api.event.events.entity.EventUpdate;

public class AutoPayload extends ToggleablePlugin {

    private final Property<String> payload = new Property<>("Payload", "Wanna Join Aphrodite? Apply Today! https://discord.gg/2wKYsgQ (2b2t Group)");
    private final NumberProperty<Integer> delay = new NumberProperty<>("Delay", 2000, 100, 10000);
    private final Timer timer = new Timer();

    public AutoPayload() {
        super("AutoPayload", Category.MISCELLANEOUS);
        setDisplay("Auto Payload");
        setColor(0xFFFB75FF);
        getProperties().add(payload);
        getProperties().add(delay);
    }

    @Register
    public void onUpdate(EventUpdate.Pre event) {
        if (timer.completed(delay.getValue())) {
            mc.player.sendChatMessage(payload.getValue());
            timer.reset();
        }
    }

}
