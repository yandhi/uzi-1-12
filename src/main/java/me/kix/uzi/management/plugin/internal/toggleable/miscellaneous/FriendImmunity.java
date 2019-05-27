package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.game.accessors.packet.ICPacketUseEntity;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.input.packet.EventPacket;
import net.minecraft.network.play.client.CPacketUseEntity;

/**
 * Prevents players from hurting a friend.
 *
 * @author Kix
 * @since 5/25/2018
 */
public class FriendImmunity extends ToggleablePlugin {

    public FriendImmunity() {
        super("FriendImmunity", Category.MISCELLANEOUS);
        setDisplay("Friend Immunity");
        setColor(0xFF69CDFF);
    }

    @Register
    public void onPacketSent(EventPacket.Send event) {
        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity useEntityPacket = (CPacketUseEntity) event.getPacket();
            /* Cancel our hitting if the action is an attack and if the entity is a friend. */
            if (useEntityPacket.getAction() == CPacketUseEntity.Action.ATTACK && Uzi.INSTANCE.getFriendManager().isFriend(mc.world.getEntityByID(((ICPacketUseEntity) useEntityPacket).getEntityId()).getName())) {
                event.setCancelled(true);
            }
        }
    }

}
