package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import me.kix.uzi.api.event.events.render.EventRender;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Tells whether or not the server is responding.
 *
 * @author Kix
 * Created in 06 2019.
 */
public class ServerResponding extends ToggleablePlugin {

    /**
     * The timer for seeing how long it has been since the server responded.
     */
    private final Timer timer = new Timer();

    public ServerResponding() {
        super("ServerResponding", Category.MISCELLANEOUS);
        setDisplay("Server Responding");
    }

    @Register
    public void onRenderWorldToScreen(EventRender.WorldToScreen worldToScreen) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);

        if (timer.completed(1000)) {
            String stoppedRespondingForewarning = String.format("Server has stopped responding! (\2477%ss\247f)", (System.currentTimeMillis() - timer.getNow()) / 1000);
            mc.fontRenderer.drawStringWithShadow(stoppedRespondingForewarning,
                    scaledResolution.getScaledWidth() / 2 - (mc.fontRenderer.getStringWidth(stoppedRespondingForewarning) / 2), 2,
                    0xFFFFFFFF);
        }
    }

    @Register
    public void onPacketRead(EventPacket.Read read) {
        timer.reset();
    }
}
