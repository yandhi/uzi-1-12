package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.angle.Angle;
import me.kix.uzi.api.util.math.angle.AngleUtil;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.Optional;

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
        if (isPlayerInOverworld(mc.player)) {
            return;
        }

        findNearestTarget(mc.player, mc.world)
                .map(target -> findNearestBedTo(mc.player, mc.world, target))
                .ifPresent(bed -> {
                    if (bed.isPresent()) {
                        preUpdate.setViewAngles(AngleUtil.getAngle(bed.get()));
                        useBed(bed.get());
                    }
                });
    }

    /**
     * Right clicks the bed and allows it to explode.
     *
     * @param bed The bed being right clicked.
     */
    private void useBed(TileEntityBed bed) {
        for (EnumFacing facing : EnumFacing.values()) {
            if (facing != EnumFacing.UP) {
                mc.playerController.processRightClickBlock(mc.player, mc.world, bed.getPos(), facing, new Vec3d(0, 0, 0), EnumHand.MAIN_HAND);
            }
        }
    }

    /**
     * Gains the nearest target to the player.
     *
     * @param clientPlayer The client player.
     * @param world        The world that the player is in.
     * @return The closest target to the entity.
     */
    private Optional<EntityPlayer> findNearestTarget(EntityPlayer clientPlayer, World world) {
        return world.playerEntities.stream()
                .filter(player -> player != clientPlayer)
                .filter(player -> !(Uzi.INSTANCE.getFriendManager().isFriend(player.getName())))
                .filter(player -> clientPlayer.getDistanceToEntity(player) <= range.getValue())
                .min(Comparator.comparing(clientPlayer::getDistanceToEntity));
    }

    /**
     * Gives us the nearest bed to the target based on nearby beds to the player.
     *
     * @param clientPlayer The client entity.
     * @param world        The world that the player and target are in.
     * @param target       The target of the bed bombing.
     * @return The closest entity to the target based on nearby entities to the player in {@link Optional} form.
     */
    private Optional<TileEntityBed> findNearestBedTo(EntityPlayer clientPlayer, World world, EntityPlayer target) {
        return world.loadedTileEntityList.stream()
                .filter(TileEntityBed.class::isInstance)
                .map(TileEntityBed.class::cast)
                .filter(bed -> clientPlayer.getDistance(bed.getPos().getX(), bed.getPos().getY(), bed.getPos().getZ()) <= range.getValue())
                .min(Comparator.comparingDouble(o -> target.getDistance(o.getPos().getX(), o.getPos().getY(), o.getPos().getZ())));
    }

    /**
     * Checks if the player is in the overworld dimension.
     *
     * @param player The player who's dimension is being checked.
     * @return Whether or not the player is in the overworld.
     */
    private boolean isPlayerInOverworld(EntityPlayer player) {
        return player.dimension == 0;
    }
}
