package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.angle.Angle;
import me.kix.uzi.api.util.math.angle.AngleUtil;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.DimensionManager;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lays in nearby beds immediately upon players coming near.
 *
 * <p>
 * The bed still has to be either placed by a new module (Module idea maybe?) or manually.
 * </p>
 *
 * @author Kix
 * Created in 06 2019.
 */
public class BedAura extends ToggleablePlugin {

    /**
     * The range that the entity can be before the bed is entered.
     */
    private final NumberProperty<Float> range = new NumberProperty<>("Range", 4f, 3f, 6f);

    public BedAura() {
        super("BedAura", Category.COMBAT);
        setDisplay("Bed Aura");

        getProperties().add(range);
    }

    @Register
    public void onPreUpdate(EventUpdate.Pre preUpdate) {
        /* Ensure we are in the nether. */
        if (mc.player.dimension != -1) {
            return;
        }

        /* The current target. */
        EntityPlayer target = null;

        /* The nearby beds. */
        List<TileEntityBed> nearbyBeds = mc.world.loadedTileEntityList.stream()
                .filter(TileEntityBed.class::isInstance)
                .map(TileEntityBed.class::cast)
                .filter(bed -> mc.player.getDistance(bed.getPos().getX(), bed.getPos().getY(), bed.getPos().getZ()) <= range.getValue())
                .collect(Collectors.toList());

        /* The nearby players. */
        List<EntityPlayer> nearbyPlayers = mc.world.playerEntities.stream()
                .filter(player -> player != mc.player)
                .filter(player -> mc.player.getDistanceToEntity(player) <= range.getValue())
                .sorted((o1, o2) -> Float.compare(mc.player.getDistanceToEntity(o1), mc.player.getDistanceToEntity(o2)))
                .collect(Collectors.toList());

        /* Choose the closest player. */
        if (!nearbyPlayers.isEmpty()) {
            target = nearbyPlayers.get(0);
        }

        /* Ensure non-nullability. */
        if (target != null) {
            /* Temporary "effectively-final" target. */
            EntityPlayer finalTarget = target;
            /* Sort beds based on the position of the target. */
            nearbyBeds.sort(Comparator.comparingDouble(o -> finalTarget.getDistance(o.getPos().getX(), o.getPos().getY(), o.getPos().getZ())));

            /* Choose the closest bed and enter it. */
            if (!nearbyBeds.isEmpty()) {
                TileEntityBed bed = nearbyBeds.get(0);

                /* Look at bed. */
                Angle angle = AngleUtil.getAngle(bed);
                preUpdate.getViewAngles().setYaw(angle.getYaw());
                preUpdate.getViewAngles().setPitch(angle.getPitch());

                /* Bruteforce the facing side because we don't have a way of knowing it. */
                for (EnumFacing facing : EnumFacing.values()) {
                    if (facing != EnumFacing.UP) {
                        mc.playerController.processRightClickBlock(mc.player, mc.world, bed.getPos(), facing, new Vec3d(0, 0, 0), EnumHand.MAIN_HAND);
                    }
                }
            }
        }
    }

}
