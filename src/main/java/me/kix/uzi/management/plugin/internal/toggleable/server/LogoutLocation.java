package me.kix.uzi.management.plugin.internal.toggleable.server;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.event.events.input.packet.EventPacket;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.game.accessors.renderer.GameRenderManager;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.math.vector.Vector3;
import me.kix.uzi.api.util.render.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.AxisAlignedBB;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Logs the logout location of entities.
 *
 * @author Kix
 * @since 7/9/2019
 */
public class LogoutLocation extends ToggleablePlugin {

    /**
     * The logouts that the plugin has registered.
     */
    private final Set<Vector3<Double>> logouts = new HashSet<>();

    public LogoutLocation() {
        super("LogoutLocation", Category.SERVER);
        setDisplay("Logout Location");
    }

    @Register
    public void onReadPacket(EventPacket.Read read) {
        if (read.getPacket() instanceof SPacketPlayerListItem) {
            SPacketPlayerListItem playerListItem = (SPacketPlayerListItem) read.getPacket();

            if (playerListItem.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                for (SPacketPlayerListItem.AddPlayerData addPlayerData : playerListItem.getEntries()) {
                    EntityPlayer entityRecieved = mc.world.getPlayerEntityByUUID(addPlayerData.getProfile().getId());
                    if (entityRecieved != null) {
                        logouts.add(new Vector3<>(entityRecieved.posX, entityRecieved.posY, entityRecieved.posZ));
                    }
                }
            }
        }
    }

    @Register
    public void onRenderHand(EventRender.Hand hand) {
        for (Vector3<Double> logout : logouts) {
            GameRenderManager renderManager = (GameRenderManager) mc.getRenderManager();
            double renderX = logout.getX() - renderManager.getRenderPosX();
            double renderY = logout.getY() - renderManager.getRenderPosY();
            double renderZ = logout.getZ() - renderManager.getRenderPosZ();

            AxisAlignedBB boundingBox = new AxisAlignedBB(0, 0, 0, 1, 1, 1).offset(renderX, renderY, renderZ);
            RenderUtil.bb(boundingBox, 1f, Color.BLUE);
        }
    }
}
